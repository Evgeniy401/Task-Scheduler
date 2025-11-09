package com.example.taskscheduler.domain.repository

import com.example.taskscheduler.domain.model.Task

interface TaskRepository {
    suspend fun saveTask(task: Task)
    suspend fun deleteTask(taskId: Int)
}