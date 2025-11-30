package com.example.data.mapping

import com.example.data.storage.PriorityData
import com.example.data.storage.entity.TaskEntity
import com.example.data.storage.entity.TaskRemoteEntity
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
            isCompleted = domain.isCompleted,
            needsSync = domain.needsSync,
            isDeleted = domain.isDeleted,
            lastModified = domain.lastModified,
        )
    }

    fun toDomain(data: TaskEntity): Task {
        return Task(
            id = data.id,
            title = data.title,
            body = data.body,
            priorityDomain = mapPriorityToDomain(data.priority),
            isCompleted = data.isCompleted,
            needsSync = data.needsSync,
            isDeleted = data.isDeleted,
            lastModified = data.lastModified,
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

    fun toRemoteEntity(domain: Task): TaskRemoteEntity {
        return TaskRemoteEntity(
            id = if (domain.id == 0) null else domain.id,
            title = domain.title,
            body = domain.body,
            priority = mapPriorityToRemote(domain.priorityDomain),
            isCompleted = domain.isCompleted
        )
    }

    fun toDomain(remote: TaskRemoteEntity): Task {
        return Task(
            id = remote.id ?: 0,
            title = remote.title,
            body = remote.body,
            priorityDomain = mapPriorityToDomainFromRemote(remote.priority),
            isCompleted = remote.isCompleted
        )
    }

    fun toDomainListFromRemote(remoteList: List<TaskRemoteEntity>): List<Task> {
        return remoteList.map { toDomain(it) }
    }

    private fun mapPriorityToRemote(priorityDomain: PriorityDomain): String {
        return when (priorityDomain) {
            PriorityDomain.STANDARD -> "standard"
            PriorityDomain.HIGH -> "high"
            PriorityDomain.MAXIMUM -> "maximum"
            PriorityDomain.NONE -> "none"
        }
    }

    private fun mapPriorityToDomainFromRemote(priorityString: String): PriorityDomain {
        return when (priorityString.lowercase()) {
            "standard" -> PriorityDomain.STANDARD
            "high" -> PriorityDomain.HIGH
            "maximum" -> PriorityDomain.MAXIMUM
            else -> PriorityDomain.NONE
        }
    }
}