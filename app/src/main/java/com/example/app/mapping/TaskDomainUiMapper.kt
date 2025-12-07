package com.example.app.mapping

import androidx.compose.ui.graphics.Color
import com.example.app.ui.screens.mainScreen.MainScreenState
import com.example.app.ui.theme.PriorityHigh
import com.example.app.ui.theme.PriorityMax
import com.example.app.ui.theme.PriorityStandard
import com.example.app.ui.utils.PriorityUtils
import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task
import javax.inject.Inject

class TaskDomainUiMapper @Inject constructor() {

    fun getPriorityGroup(priority: PriorityDomain): String {
        return PriorityUtils.getPriorityGroup(priority)
    }

    fun getPriorityColor(priority: PriorityDomain): Color {
        return when (priority) {
            PriorityDomain.NONE -> Color.Gray
            PriorityDomain.STANDARD -> PriorityStandard
            PriorityDomain.HIGH -> PriorityHigh
            PriorityDomain.MAXIMUM -> PriorityMax
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

    fun toUiList(domains: List<Task>): List<MainScreenState.TaskItem> {
        return domains.map { toUi(it) }
    }
}