package com.example.domain.model

data class Task(
    val id: Int = 0,
    val title: String,
    val body: String,
    val priorityDomain: PriorityDomain,
    val isCompleted: Boolean = false,
    val isDeleted: Boolean = false,
    val lastModified: Long = System.currentTimeMillis()
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
}

