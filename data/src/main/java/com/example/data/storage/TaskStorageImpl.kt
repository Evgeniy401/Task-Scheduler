package com.example.data.storage

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.data.mapping.TaskDataMapper
import com.example.data.network.NetworkManager
import com.example.data.network.TaskApi
import com.example.data.storage.dao.TaskDao
import com.example.data.storage.entity.CreateTaskDto
import com.example.data.storage.entity.UpdateTaskDto
import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskStorageImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val mapper: TaskDataMapper,
    private val taskApi: TaskApi,
    private val networkManager: NetworkManager,
) : TaskStorage {

    override suspend fun save(task: Task) {
        val taskEntity = mapper.toData(task)
        taskDao.insertTask(taskEntity)
    }

    override suspend fun saveToServer(task: Task): Task {
        return try {

            val createDto: CreateTaskDto = mapper.toCreateDto(task)

            val response = taskApi.createTask(createDto)

            val savedTask = mapper.fromRemote(response)

            taskDao.insertTask(mapper.toData(savedTask))
            savedTask
        } catch (e: Exception) {
            val localTask = task.copy(needsSync = true)
            taskDao.insertTask(mapper.toData(localTask))
            localTask
        }
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { entities ->
            mapper.toDomainList(entities)
        }
    }

    override suspend fun getAllTasksFromServer(): List<Task> {
        val remoteTasks = taskApi.getAllTasks()
        return mapper.fromRemoteList(remoteTasks)
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTaskById(taskId)
    }

    override suspend fun deleteTaskFromServer(taskId: Int) {
        try {
            taskApi.deleteTask(taskId)
            taskDao.deleteTaskById(taskId)
        } catch (e: Exception) {

            val task = taskDao.getTaskById(taskId)
            task?.let {
                val markedForDeletion = it.copy(isDeleted = true, needsSync = true)
                taskDao.insertTask(markedForDeletion)
            }
        }
    }

    override suspend fun completeTask(taskId: Int) {
        val task = taskDao.getTaskById(taskId)
        task?.let {

            val completedTask = it.copy(isCompleted = true, needsSync = true)
            taskDao.insertTask(completedTask)

            try {

                val domainTask = mapper.toDomain(completedTask)
                val updateDto: UpdateTaskDto = mapper.toUpdateDto(domainTask)
                taskApi.updateTask(taskId, updateDto)

                val syncedTask = completedTask.copy(needsSync = false)
                taskDao.insertTask(syncedTask)
            } catch (e: Exception) {}
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun syncTasks() {
        if (!networkManager.isConnected()) return

        try {
            val serverTasks = getAllTasksFromServer()
            serverTasks.forEach { serverTask ->
                taskDao.insertTask(mapper.toData(serverTask))
            }
        } catch (e: Exception) {
            return
        }

        val unsyncedTasks = taskDao.getUnsyncedTasks()
        unsyncedTasks.forEach { taskEntity ->
            try {
                val domainTask = mapper.toDomain(taskEntity)

                if (taskEntity.isDeleted) {

                    if (taskEntity.id > 0) {
                        taskApi.deleteTask(taskEntity.id)
                    }
                    taskDao.deleteTask(taskEntity)
                } else {

                    val response = if (taskEntity.id > 0) {

                        val updateDto: UpdateTaskDto = mapper.toUpdateDto(domainTask)
                        taskApi.updateTask(taskEntity.id, updateDto)
                    } else {

                        val createDto: CreateTaskDto = mapper.toCreateDto(domainTask)
                        taskApi.createTask(createDto)
                    }

                    val updatedEntity = mapper.toData(mapper.fromRemote(response))
                        .copy(needsSync = false, isDeleted = false)
                    taskDao.insertTask(updatedEntity)
                }
            } catch (e: Exception) {}
        }
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)?.let { taskEntity ->
            mapper.toDomain(taskEntity)
        }
    }
}