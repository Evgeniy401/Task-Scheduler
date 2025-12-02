package com.example.domain.model

import java.time.LocalDateTime

data class Task(
    val id: Int = 0,
    val title: String,
    val body: String,
    val priorityDomain: PriorityDomain,
    val isCompleted: Boolean = false,
    val needsSync: Boolean = false,
    val isDeleted: Boolean = false,
    val lastModified: Long = System.currentTimeMillis(),
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    fun getPriorityValue(): Int {
        return when (priorityDomain) {
            PriorityDomain.MAXIMUM -> 4
            PriorityDomain.HIGH -> 3
            PriorityDomain.STANDARD -> 2
            PriorityDomain.NONE -> 1
        }
    }
}

enum class PriorityDomain {
    STANDARD, HIGH, MAXIMUM, NONE;

    companion object {
        fun fromString(value: String): PriorityDomain {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                NONE
            }
        }

        fun toString(priority: PriorityDomain): String {
            return when (priority) {
                STANDARD -> "STANDARD"
                HIGH -> "HIGH"
                MAXIMUM -> "MAXIMUM"
                NONE -> "NONE"
            }
        }
    }
}

