package com.example.app.mapping

import androidx.compose.ui.graphics.Color
import com.example.app.ui.screens.mainScreen.MainScreenState
import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task
import javax.inject.Inject

class TaskDomainUiMapper @Inject constructor() {

    fun getPriorityGroup(priority: PriorityDomain): String {
        return when (priority) {
            PriorityDomain.NONE -> "Низкий приоритет"
            PriorityDomain.STANDARD -> "Стандартный приоритет"
            PriorityDomain.HIGH -> "Высокий приоритет"
            PriorityDomain.MAXIMUM -> "Максимальный приоритет"
        }
    }

    fun getPriorityColor(priority: PriorityDomain): Color {
        return when (priority) {
            PriorityDomain.NONE -> Color.Gray
            PriorityDomain.STANDARD -> Color.Green
            PriorityDomain.HIGH -> Color.Yellow
            PriorityDomain.MAXIMUM -> Color.Red
        }
    }

    fun toUi(domain: Task): MainScreenState.TaskItem {
        return MainScreenState.TaskItem(
            id = domain.id,
            title = domain.title,
            body = domain.body,
            priority = domain.priorityDomain,
            priorityGroup = getPriorityGroup(domain.priorityDomain),
            priorityColor = getPriorityColor(domain.priorityDomain),
            isCompleted = domain.isCompleted
        )
    }

    fun getTaskCardColor(priority: PriorityDomain): Color {
        return when (priority) {
            PriorityDomain.NONE -> Color.LightGray.copy(alpha = 0.2f)
            PriorityDomain.STANDARD -> Color.Green.copy(alpha = 0.2f)
            PriorityDomain.HIGH -> Color.Yellow.copy(alpha = 0.2f)
            PriorityDomain.MAXIMUM -> Color.Red.copy(alpha = 0.2f)
        }
    }

    fun toUiList(domains: List<Task>): List<MainScreenState.TaskItem> {
        return domains.map { toUi(it) }
    }
}