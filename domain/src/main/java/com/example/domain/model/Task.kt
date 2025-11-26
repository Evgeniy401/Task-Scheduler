package com.example.domain.model

data class Task(
    val id: Int,
    val title: String,
    val body: String,
    val priorityDomain: PriorityDomain
) {
    fun getPriorityValue(): Int {
        return when (this.priorityDomain) {
            PriorityDomain.MAXIMUM -> 4
            PriorityDomain.HIGH -> 3
            PriorityDomain.STANDARD -> 2
            PriorityDomain.NONE -> 1
        }
    }
}

enum class PriorityDomain{
    STANDARD, HIGH, MAXIMUM, NONE
}

