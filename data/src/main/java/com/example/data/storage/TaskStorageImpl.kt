package com.example.data.storage

import com.example.data.mapping.TaskDataMapper
import com.example.data.storage.dao.TaskDao
import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskStorageImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val mapper: TaskDataMapper
) : TaskStorage {

    override suspend fun save(task: Task) {
        val taskEntity = mapper.toData(task)
        taskDao.insertTask(taskEntity)
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { entities ->
            mapper.toDomainList(entities)
        }
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTaskById(taskId)
    }

    override suspend fun completeTask(taskId: Int) {
        val task = taskDao.getTaskById(taskId)
        task?.let {
            val completedTask = it.copy(isCompleted = true)
            taskDao.insertTask(completedTask)
        }
    }
}