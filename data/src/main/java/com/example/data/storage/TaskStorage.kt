package com.example.data.storage

import com.example.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskStorage {
    suspend fun save(task: Task)
    suspend fun saveToServer(task: Task): Task
    fun getAllTasks(): Flow<List<Task>>
    suspend fun getAllTasksFromServer(): List<Task>
    suspend fun deleteTask(taskId: Int)
    suspend fun deleteTaskFromServer(taskId: Int)
    suspend fun completeTask(taskId: Int)
    suspend fun syncTasks()
    suspend fun getTaskById(taskId: Int): Task?
}