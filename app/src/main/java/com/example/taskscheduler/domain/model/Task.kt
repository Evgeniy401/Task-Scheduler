package com.example.taskscheduler.domain.model

import androidx.compose.runtime.mutableStateListOf

data class Task(
    val id: Int,
    val title: String,
    val body: String,
    val status: Boolean,
)

val listTask: MutableList<Task> = mutableListOf()