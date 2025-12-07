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

object PriorityUtils {

    private val priorityOrder = mapOf(
        "Максимальный" to 4,
        "Высокий" to 3,
        "Средний" to 2,
        "Низкий" to 1
    )

    fun getPriorityValue(priorityGroup: String): Int {
        return priorityOrder[priorityGroup] ?: 0
    }

    fun comparePriorities(priority1: String, priority2: String): Int {
        val value1 = getPriorityValue(priority1)
        val value2 = getPriorityValue(priority2)
        return value2.compareTo(value1)
    }

    fun getPriorityGroup(priority: PriorityDomain): String {
        return when (priority) {
            PriorityDomain.NONE -> "Низкий"
            PriorityDomain.STANDARD -> "Средний"
            PriorityDomain.HIGH -> "Высокий"
            PriorityDomain.MAXIMUM -> "Максимальный"
        }
    }
}