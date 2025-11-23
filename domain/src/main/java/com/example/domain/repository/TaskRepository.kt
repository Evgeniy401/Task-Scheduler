package com.example.domain.repository

import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun saveTask(task: Task)
    fun getTasks(): Flow<List<Task>>
    suspend fun deleteTask(taskId: Int)
}