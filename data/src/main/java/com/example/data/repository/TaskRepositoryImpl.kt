package com.example.data.repository

import com.example.data.storage.TaskStorage
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskStorage: TaskStorage): TaskRepository {

    override suspend fun saveTask(task: Task) {
        return taskStorage.save(task)
    }

    override suspend fun deleteTask(taskId: Int) {}
}