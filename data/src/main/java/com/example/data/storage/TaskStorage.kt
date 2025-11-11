package com.example.data.storage

import com.example.domain.model.Task

interface TaskStorage {

    fun save(task: Task)
    fun deleteTask(taskId: Int)

}