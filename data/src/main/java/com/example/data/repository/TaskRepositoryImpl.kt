package com.example.data.repository

import com.example.data.storage.TaskStorage
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskStorage: TaskStorage
) : TaskRepository {

    override suspend fun saveTask(task: Task) =
        taskStorage.save(task)

    override fun getAllTasks(): Flow<List<Task>> =
        taskStorage.getAllTasks()

    override suspend fun deleteTask(taskId: Int) =
        taskStorage.deleteTask(taskId)

    override suspend fun completeTask(taskId: Int) =
        taskStorage.completeTask(taskId)
}