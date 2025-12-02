package com.example.data.mapping

import com.example.data.storage.PriorityData
import com.example.data.storage.entity.TaskEntity
import com.example.data.storage.entity.CreateTaskDto
import com.example.data.storage.entity.GetTaskDto
import com.example.data.storage.entity.UpdateTaskDto
import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
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
            needsSync = task.needsSync,
            isDeleted = task.isDeleted,
            lastModified = task.lastModified,
            createdAt = task.createdAt?.toString(),
            updatedAt = task.updatedAt?.toString()
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
            needsSync = entity.needsSync,
            isDeleted = entity.isDeleted,
            lastModified = entity.lastModified,
            createdAt = parseLocalDateTime(entity.createdAt),
            updatedAt = parseLocalDateTime(entity.updatedAt)
        )
    }

    fun toDomainList(entities: List<TaskEntity>): List<Task> {
        return entities.map { toDomain(it) }
    }

    fun toCreateDto(task: Task): CreateTaskDto {

        val priorityString = when (task.priorityDomain) {
            PriorityDomain.STANDARD -> "STANDARD"
            PriorityDomain.HIGH -> "HIGH"
            PriorityDomain.MAXIMUM -> "MAXIMUM"
            PriorityDomain.NONE -> "NONE"
        }

        return CreateTaskDto(
            title = task.title,
            body = task.body,
            priority = priorityString,
            isCompleted = task.isCompleted
        )
    }

    fun toUpdateDto(task: Task): UpdateTaskDto {

        val priorityString = when (task.priorityDomain) {
            PriorityDomain.STANDARD -> "STANDARD"
            PriorityDomain.HIGH -> "HIGH"
            PriorityDomain.MAXIMUM -> "MAXIMUM"
            PriorityDomain.NONE -> "NONE"
        }

        return UpdateTaskDto(
            title = task.title,
            body = task.body,
            priority = priorityString,
            isCompleted = task.isCompleted
        )
    }

    fun fromRemote(dto: GetTaskDto): Task {

        val priorityDomain = try {
            PriorityDomain.valueOf(dto.priority.uppercase())
        } catch (e: IllegalArgumentException) {
            PriorityDomain.NONE
        }

        return Task(
            id = dto.id,
            title = dto.title,
            body = dto.body,
            priorityDomain = priorityDomain,
            isCompleted = dto.isCompleted,
            needsSync = false,
            isDeleted = false,
            lastModified = System.currentTimeMillis(),
            createdAt = parseLocalDateTime(dto.createdAt),
            updatedAt = parseLocalDateTime(dto.updatedAt)
        )
    }

    fun fromRemoteList(dtos: List<GetTaskDto>): List<Task> {
        return dtos.map { fromRemote(it) }
    }

    private fun parseLocalDateTime(dateString: String?): LocalDateTime? {
        if (dateString == null) return null

        return try {
            LocalDateTime.parse(dateString)
        } catch (e: DateTimeParseException) {

            try {
                val cleaned = dateString.replace("Z$".toRegex(), "")
                LocalDateTime.parse(cleaned)
            } catch (e2: Exception) {
                null
            }
        }
    }
}