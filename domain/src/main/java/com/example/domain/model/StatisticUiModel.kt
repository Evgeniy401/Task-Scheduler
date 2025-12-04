package com.example.domain.model

data class StatisticUiModel(
    val totalTasksCreated: Int = 0,
    val currentTasks: Int = 0,
    val completedTasks: Int = 0,
    val cancelledTasks: Int = 0,
    val completionPercentage: Float = 0f
)
