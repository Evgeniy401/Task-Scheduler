package com.example.domain.model

data class Task(
    val id: Int,
    val title: String,
    val body: String,
    val priorityDomain: PriorityDomain
)

enum class PriorityDomain{
    STANDARD, HIGH, MAXIMUM, NONE
}