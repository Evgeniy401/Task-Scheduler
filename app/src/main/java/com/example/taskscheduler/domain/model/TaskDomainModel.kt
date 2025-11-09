package com.example.taskscheduler.domain.model

data class TaskDomainModel(
    val id: Int,
    val title: String,
    val body: String,
    val priority: Priority,
)

enum class Priority{
    STANDARD, HIGH, MAXIMUM, NONE
}