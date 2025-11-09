package com.example.taskscheduler.domain.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.taskscheduler.R
import com.example.taskscheduler.domain.model.PriorityDomain

@Composable
fun getPriorityText(priorityDomain: PriorityDomain): String {
    val textPriorityDomain = when (priorityDomain) {
        PriorityDomain.NONE -> stringResource(R.string.priority_none)
        PriorityDomain.STANDARD -> stringResource(R.string.priority_standard)
        PriorityDomain.HIGH -> stringResource(R.string.priority_high)
        PriorityDomain.MAXIMUM -> stringResource(R.string.priority_maximum)
    }
    return textPriorityDomain
}