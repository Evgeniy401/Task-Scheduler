package com.example.domain.repository

import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun saveTask(task: Task)
    fun getAllTasks(): Flow<List<Task>>
    suspend fun deleteTask(taskId: Int)
    suspend fun completeTask(taskId: Int)
}