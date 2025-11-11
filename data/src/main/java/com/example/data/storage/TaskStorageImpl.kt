package com.example.data.storage

import com.example.domain.model.Task

private val tasks = mutableListOf<Task>() // временное хранилище данных в памяти
private var nextId = 1

class TaskStorageImpl: TaskStorage {

    override fun save(task: Task) {
        tasks.add(task)
    }

    override fun deleteTask(taskId: Int) {
        tasks.removeAt(taskId)
    }

    private fun mapToTaskData(task: Task) {
        return
    }

}