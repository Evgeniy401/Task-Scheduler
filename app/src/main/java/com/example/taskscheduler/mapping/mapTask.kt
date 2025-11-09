package com.example.taskscheduler.mapping

import com.example.taskscheduler.data.storage.PriorityData
import com.example.taskscheduler.data.storage.TaskDataModel
import com.example.taskscheduler.domain.model.Task

fun Task.toDataModel(): TaskDataModel {
    return TaskDataModel(
        id = this.id,
        title = this.title,
        body = this.body,
        priorityData = PriorityData.fromDomain(this.priorityDomain)
    )
}

fun TaskDataModel.toDomainModel(): Task {
    return Task(
        id = this.id,
        title = this.title,
        body = this.body,
        priorityDomain = this.priorityData.toDomain()
    )
}