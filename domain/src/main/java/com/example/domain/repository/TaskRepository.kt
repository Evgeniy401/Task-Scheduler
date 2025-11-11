package com.example.domain.repository

import com.example.domain.model.Task

interface TaskRepository {
    suspend fun saveTask(task: Task)
    suspend fun deleteTask(taskId: Int)
}