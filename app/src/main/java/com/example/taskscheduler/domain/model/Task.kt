package com.example.taskscheduler.domain.model

import androidx.compose.runtime.mutableStateListOf

data class Task(
    val id: Int,
    val title: String,
    val body: String,
    val priority: Priority,
)

val listTask: MutableList<Task> = mutableListOf()

enum class Priority{
    STANDARD, HIGH, MAXIMUM
}