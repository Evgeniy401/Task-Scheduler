package com.example.data.mapping

import com.example.data.storage.PriorityData
import com.example.data.storage.entity.TaskEntity
import com.example.domain.model.Task
import com.example.domain.model.PriorityDomain
import javax.inject.Inject

class TaskDataMapper @Inject constructor() {

    fun toData(domain: Task): TaskEntity {
        return TaskEntity(
            id = if (domain.id == 0) 0 else domain.id,
            title = domain.title,
            body = domain.body,
            priority = mapPriorityToData(domain.priorityDomain),
            isCompleted = domain.isCompleted
        )
    }

    fun toDomain(data: TaskEntity): Task {
        return Task(
            id = data.id,
            title = data.title,
            body = data.body,
            priorityDomain = mapPriorityToDomain(data.priority),
            isCompleted = data.isCompleted
        )
    }

    private fun mapPriorityToData(priorityDomain: PriorityDomain): PriorityData {
        return when (priorityDomain) {
            PriorityDomain.STANDARD -> PriorityData.STANDARD
            PriorityDomain.HIGH -> PriorityData.HIGH
            PriorityDomain.MAXIMUM -> PriorityData.MAXIMUM
            PriorityDomain.NONE -> PriorityData.NONE
        }
    }

    private fun mapPriorityToDomain(priorityData: PriorityData): PriorityDomain {
        return when (priorityData) {
            PriorityData.STANDARD -> PriorityDomain.STANDARD
            PriorityData.HIGH -> PriorityDomain.HIGH
            PriorityData.MAXIMUM -> PriorityDomain.MAXIMUM
            PriorityData.NONE -> PriorityDomain.NONE
        }
    }

    fun toDataList(domains: List<Task>): List<TaskEntity> {
        return domains.map { toData(it) }
    }

    fun toDomainList(dataList: List<TaskEntity>): List<Task> {
        return dataList.map { toDomain(it) }
    }
}