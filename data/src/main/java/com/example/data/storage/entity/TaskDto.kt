package com.example.data.storage.entity

data class CreateTaskDto(
    val title: String,
    val body: String,
    val priority: String = "NONE",
    val isCompleted: Boolean = false
)

data class GetTaskDto(
    val id: Int,
    val title: String,
    val body: String,
    val priority: String,
    val isCompleted: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class UpdateTaskDto(
    val title: String,
    val body: String,
    val priority: String = "NONE",
    val isCompleted: Boolean = false
)