package com.example.domain.model

data class Task(
    val id: Int = 0,
    val title: String,
    val body: String,
    val priorityDomain: PriorityDomain,
    val isCompleted: Boolean = false,
    val isDeleted: Boolean = false,
    val lastModified: Long = System.currentTimeMillis()
)

enum class PriorityDomain(val value: Int) {
    NONE(1),
    STANDARD(2),
    HIGH(3),
    MAXIMUM(4);

    companion object {
        fun fromValue(value: Int): PriorityDomain {
            return entries.firstOrNull { it.value == value } ?: NONE
        }
    }
}