package com.example.data.storage

import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskStorage {
    suspend fun save(task: Task)
    fun getAllTasks(): Flow<List<Task>>
    suspend fun deleteTask(taskId: Int)
    suspend fun completeTask(taskId: Int)
    suspend fun getTaskById(taskId: Int): Task?
    fun getActiveTasks(): Flow<List<Task>>
}