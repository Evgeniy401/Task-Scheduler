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
            if (task.id > 0) {
                val updateDto: UpdateTaskDto = mapper.toUpdateDto(task)
                val response = taskApi.updateTask(task.id, updateDto)
                val updatedTask = mapper.fromRemote(response)

                val updatedEntity = mapper.toData(updatedTask).copy(needsSync = false)
                taskDao.insertTask(updatedEntity)
                updatedTask

            } else {
                val createDto: CreateTaskDto = mapper.toCreateDto(task)
                val response = taskApi.createTask(createDto)
                val savedTask = mapper.fromRemote(response)

                val savedEntity = mapper.toData(savedTask).copy(needsSync = false)
                taskDao.insertTask(savedEntity)
                savedTask
            }
        } catch (e: Exception) {

            throw e
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
            if (taskId > 0) {
                taskApi.deleteTask(taskId)
            }

            taskDao.deleteTaskById(taskId)
        } catch (e: Exception) {

            val task = taskDao.getTaskById(taskId)
            task?.let {
                val markedForDeletion = it.copy(isDeleted = true, needsSync = true)
                taskDao.insertTask(markedForDeletion)
            }
            throw e
        }
    }

    override suspend fun completeTask(taskId: Int) {

        val task = taskDao.getTaskById(taskId)
        task?.let {
            val completedTask = it.copy(isCompleted = true, needsSync = true)
            taskDao.insertTask(completedTask)
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun syncTasks() {
        if (!networkManager.isConnected()) return

        try {
            val serverTasks = getAllTasksFromServer()
            val serverTasksMap = serverTasks.associateBy { it.id }
            val localTasks = taskDao.getAllTasksSync()

            for (localTask in localTasks) {
                val serverTask = serverTasksMap[localTask.id]

                if (serverTask != null) {
                    val localDomain = mapper.toDomain(localTask)
                    if (serverTask.lastModified > localDomain.lastModified && !localTask.needsSync) {
                        taskDao.insertTask(mapper.toData(serverTask))
                    }

                } else if (!localTask.isDeleted) {}
            }
        } catch (e: Exception) {}

        val unsyncedTasks = taskDao.getUnsyncedTasks()
        unsyncedTasks.forEach { taskEntity ->
            try {
                val domainTask = mapper.toDomain(taskEntity)

                if (taskEntity.isDeleted) {

                    if (taskEntity.id > 0) {
                        taskApi.deleteTask(taskEntity.id)
                    }

                    taskDao.deleteTask(taskEntity)

                } else if (taskEntity.id > 0) {
                    val updateDto: UpdateTaskDto = mapper.toUpdateDto(domainTask)
                    val response = taskApi.updateTask(taskEntity.id, updateDto)
                    val updatedEntity = mapper.toData(mapper.fromRemote(response))
                        .copy(needsSync = false, isDeleted = false)
                    taskDao.insertTask(updatedEntity)

                } else {
                    val createDto: CreateTaskDto = mapper.toCreateDto(domainTask)
                    val response = taskApi.createTask(createDto)
                    val remoteTask = mapper.fromRemote(response)
                    val updatedEntity = mapper.toData(remoteTask)
                        .copy(needsSync = false, isDeleted = false)

                    taskDao.deleteTask(taskEntity)
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