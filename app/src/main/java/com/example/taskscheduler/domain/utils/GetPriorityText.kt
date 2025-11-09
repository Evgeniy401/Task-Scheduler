package com.example.taskscheduler.domain.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.taskscheduler.R
import com.example.taskscheduler.domain.model.Priority

@Composable
fun getPriorityText(priority: Priority): String {
    val textPriority = when (priority) {
        Priority.NONE -> stringResource(R.string.priority_none)
        Priority.STANDARD -> stringResource(R.string.priority_standard)
        Priority.HIGH -> stringResource(R.string.priority_high)
        Priority.MAXIMUM -> stringResource(R.string.priority_maximum)
    }
    return textPriority
}