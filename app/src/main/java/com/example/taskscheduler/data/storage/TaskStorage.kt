package com.example.taskscheduler.data.storage

import com.example.taskscheduler.domain.model.Task

interface TaskStorage {

    fun save(task: Task)
    fun deleteTask(taskId: Int)

}