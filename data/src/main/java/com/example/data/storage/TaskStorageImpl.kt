package com.example.data.storage

import com.example.domain.model.Task

private val tasks = mutableListOf<Task>() // временное хранилище данных в памяти
private var nextId = 1

class TaskStorageImpl: TaskStorage {

    override fun save(task: Task) {
        val taskToSave = if (task.id == 0) task.copy(id = nextId++) else task
        tasks.removeAll { it.id == taskToSave.id }
        tasks.add(taskToSave)
    }

    override fun getAllTasks(): List<Task> {
        return tasks.toList()
    }

    override fun deleteTask(taskId: Int) {
        tasks.removeAll { it.id == taskId }
    }

    override fun completeTask(taskId: Int) {
        tasks.removeAll { it.id == taskId }
    }
}