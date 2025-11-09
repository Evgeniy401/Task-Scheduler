package com.example.taskscheduler.data.storage

data class TaskDataModel(
    val id: Int,
    val title: String,
    val body: String,
    val priority: Priority,
)

enum class Priority{
    STANDARD, HIGH, MAXIMUM, NONE
}
