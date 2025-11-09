package com.example.taskscheduler.domain.repository

import com.example.taskscheduler.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun saveTask(task: Task)
    suspend fun deleteTask(taskId: Int)
}