package com.example.app.mapping

import com.example.data.storage.PriorityData
import com.example.data.storage.TaskDataModel
import com.example.domain.model.Task

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