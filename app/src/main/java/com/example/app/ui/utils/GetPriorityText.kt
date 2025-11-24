package com.example.app.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.app.R
import com.example.domain.model.PriorityDomain

@Composable
fun getPriorityText(priority: PriorityDomain, includePrefix: Boolean = true): String {
    val priorityText = when (priority) {
        PriorityDomain.NONE -> stringResource(R.string.priority_none)
        PriorityDomain.STANDARD -> stringResource(R.string.priority_standard)
        PriorityDomain.HIGH -> stringResource(R.string.priority_high)
        PriorityDomain.MAXIMUM -> stringResource(R.string.priority_maximum)
    }

    return if (includePrefix) {
        stringResource(R.string.priority_prefix, priorityText)
    } else {
        priorityText
    }
}