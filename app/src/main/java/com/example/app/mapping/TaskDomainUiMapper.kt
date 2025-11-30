package com.example.app.mapping

import com.example.app.ui.screens.mainScreen.MainScreenState
import com.example.domain.model.Task
import com.example.domain.model.PriorityDomain
import androidx.compose.ui.graphics.Color
import javax.inject.Inject

class TaskDomainUiMapper @Inject constructor() {

    fun toUiState(tasks: List<Task>): List<MainScreenState.TaskItem> {
        return tasks
            .sortedByDescending { it.getPriorityValue() }
            .map { task ->
                MainScreenState.TaskItem(
                    id = task.id,
                    title = task.title,
                    body = task.body,
                    priority = task.priorityDomain,
                    priorityGroup = getPriorityGroupName(task.priorityDomain),
                    priorityColor = getPriorityColor(task.priorityDomain),
                    isCompleted = task.isCompleted,
                    needsSync = task.needsSync
                )
            }
    }

    fun getPriorityColor(priority: PriorityDomain): Color {
        return when (priority) {
            PriorityDomain.MAXIMUM -> Color(0xFFD32F2F)
            PriorityDomain.HIGH -> Color(0xFFFFA000)
            PriorityDomain.STANDARD -> Color(0xFF388E3C)
            PriorityDomain.NONE -> Color(0xFF757575)
        }
    }

    private fun getPriorityGroupName(priority: PriorityDomain): String {
        return when (priority) {
            PriorityDomain.MAXIMUM -> "Максимальный приоритет"
            PriorityDomain.HIGH -> "Высокий приоритет"
            PriorityDomain.STANDARD -> "Стандартный приоритет"
            PriorityDomain.NONE -> "Без приоритета"
        }
    }

    fun getTaskCardColor(priority: PriorityDomain): Color {
        return when (priority) {
            PriorityDomain.NONE -> Color(0xFFF5F5F5)
            PriorityDomain.STANDARD -> Color(0xFFE8F5E8)
            PriorityDomain.HIGH -> Color(0xFFFFF9C4)
            PriorityDomain.MAXIMUM -> Color(0xFFFFEBEE)
        }
    }
}