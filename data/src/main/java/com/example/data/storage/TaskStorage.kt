package com.example.data.storage

import com.example.domain.model.Task

interface TaskStorage {

    fun save(task: Task)
    fun getAllTasks(): List<Task>
    fun deleteTask(taskId: Int)
    fun completeTask(taskId: Int)

}