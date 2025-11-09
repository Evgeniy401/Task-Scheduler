package com.example.taskscheduler.data.repository

import com.example.taskscheduler.data.storage.TaskStorage
import com.example.taskscheduler.domain.model.Task
import com.example.taskscheduler.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskStorage: TaskStorage): TaskRepository {

    override suspend fun saveTask(task: Task) {
        return taskStorage.save(task)
    }

    override suspend fun deleteTask(taskId: Int) {}
}