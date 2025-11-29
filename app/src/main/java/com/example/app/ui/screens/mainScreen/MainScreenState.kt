package com.example.app.ui.screens.mainScreen

import androidx.compose.ui.graphics.Color
import com.example.domain.model.PriorityDomain

data class MainScreenState(
    val tasks: List<TaskItem> = emptyList(),
    val showConfirmationDialog: Boolean = false,
    val pendingAction: PendingAction? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    data class TaskItem(
        val id: Int,
        val title: String,
        val body: String,
        val priority: PriorityDomain,
        val priorityGroup: String,
        val priorityColor: Color,
        val isCompleted: Boolean
    )

    data class PendingAction(
        val taskId: Int,
        val type: ConfirmationType
    )

    sealed class ConfirmationType {
        object Delete : ConfirmationType()
        object Complete : ConfirmationType()
    }
}