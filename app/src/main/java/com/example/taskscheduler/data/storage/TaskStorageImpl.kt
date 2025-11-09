package com.example.taskscheduler.data.storage

import com.example.taskscheduler.domain.model.Task

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