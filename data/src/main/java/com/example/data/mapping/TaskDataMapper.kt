package com.example.data.mapping

import com.example.data.storage.PriorityData
import com.example.data.storage.entity.TaskEntity
import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskDataMapper @Inject constructor() {

    fun toData(task: Task): TaskEntity {
        return TaskEntity(
            id = task.id,
            title = task.title,
            body = task.body,
            priority = when (task.priorityDomain) {
                PriorityDomain.HIGH -> PriorityData.HIGH
                PriorityDomain.MAXIMUM -> PriorityData.MAXIMUM
                PriorityDomain.STANDARD -> PriorityData.STANDARD
                PriorityDomain.NONE -> PriorityData.NONE
            },
            isCompleted = task.isCompleted,
            isDeleted = task.isDeleted,
            lastModified = task.lastModified
        )
    }

    fun toDomain(entity: TaskEntity): Task {
        return Task(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            priorityDomain = when (entity.priority) {
                PriorityData.HIGH -> PriorityDomain.HIGH
                PriorityData.MAXIMUM -> PriorityDomain.MAXIMUM
                PriorityData.STANDARD -> PriorityDomain.STANDARD
                PriorityData.NONE -> PriorityDomain.NONE
            },
            isCompleted = entity.isCompleted,
            isDeleted = entity.isDeleted,
            lastModified = entity.lastModified
        )
    }

    fun toDomainList(entities: List<TaskEntity>): List<Task> {
        return entities.map { toDomain(it) }
    }
}