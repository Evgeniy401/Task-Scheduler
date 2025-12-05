package com.example.data.repository

import com.example.data.mapping.TaskDataMapper
import com.example.data.storage.dao.TaskDao
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val taskDataMapper: TaskDataMapper
) : TaskRepository {

    override fun getActiveTasks(): Flow<List<Task>> =
        taskDao.getActiveTasks().map { entities ->
            taskDataMapper.toDomainList(entities)
        }

    override suspend fun saveTask(task: Task): Task {
        val entity = taskDataMapper.toData(task)
        taskDao.insertTask(entity)
        return task
    }

    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { entities ->
            taskDataMapper.toDomainList(entities)
        }

    override suspend fun deleteTask(taskId: Int) {
        val task = getTaskById(taskId)
        if (task != null) {
            val deletedTask = task.copy(
                isDeleted = true,
                lastModified = System.currentTimeMillis()
            )
            saveTask(deletedTask)
        }
    }

    override suspend fun completeTask(taskId: Int) {
        val task = getTaskById(taskId)
        if (task != null && !task.isCompleted) {
            val completedTask = task.copy(
                isCompleted = true,
                lastModified = System.currentTimeMillis()
            )
            saveTask(completedTask)
        }
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        val entity = taskDao.getTaskById(taskId)
        return entity?.let { taskDataMapper.toDomain(it) }
    }
}