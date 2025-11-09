package com.example.taskscheduler.data.storage

import com.example.taskscheduler.domain.model.PriorityDomain

data class TaskDataModel(
    val id: Int,
    val title: String,
    val body: String,
    val priorityData: PriorityData,
)

enum class PriorityData {
    STANDARD, HIGH, MAXIMUM, NONE;

    fun toDomain(): PriorityDomain {
        return when (this) {
            STANDARD -> PriorityDomain.STANDARD
            HIGH -> PriorityDomain.HIGH
            MAXIMUM -> PriorityDomain.MAXIMUM
            NONE -> PriorityDomain.NONE
        }
    }

    companion object {
        fun fromDomain(priorityDomain: PriorityDomain): PriorityData {
            return when (priorityDomain) {
                PriorityDomain.STANDARD -> STANDARD
                PriorityDomain.HIGH -> HIGH
                PriorityDomain.MAXIMUM -> MAXIMUM
                PriorityDomain.NONE -> NONE
            }
        }
    }
}
