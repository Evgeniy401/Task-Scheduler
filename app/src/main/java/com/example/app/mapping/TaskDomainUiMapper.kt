package com.example.app.mapping

import com.example.app.ui.screens.mainScreen.MainScreenState
import com.example.domain.model.Task
import com.example.domain.model.PriorityDomain
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
                    priorityGroup = getPriorityGroupName(task.priorityDomain)
                )
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

    private fun Task.getPriorityValue(): Int {
        return when (this.priorityDomain) {
            PriorityDomain.MAXIMUM -> 4
            PriorityDomain.HIGH -> 3
            PriorityDomain.STANDARD -> 2
            PriorityDomain.NONE -> 1
        }
    }
}