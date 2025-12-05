package com.example.app.mapping

import android.annotation.SuppressLint
import com.example.domain.model.StatisticDomainModel
import com.example.domain.model.StatisticUiModel

object StatisticDomainToUiMapper {

    fun toUi(domain: StatisticDomainModel, currentTasksCount: Int): StatisticUiModel {
        val completionPercentage = calculateCompletionPercentage(
            totalCreated = domain.totalCreated,
            totalCompleted = domain.totalCompleted
        )

        return StatisticUiModel(
            totalTasksCreated = domain.totalCreated,
            currentTasks = currentTasksCount,
            completedTasks = domain.totalCompleted,
            cancelledTasks = domain.totalDeleted,
            completionPercentage = completionPercentage
        )
    }

    @SuppressLint("DefaultLocale")
    private fun calculateCompletionPercentage(totalCreated: Int, totalCompleted: Int): Float {
        return if (totalCreated > 0) {
            val percentage = (totalCompleted.toFloat() / totalCreated) * 100
            String.format("%.2f", percentage).replace(",", ".").toFloat()
        } else {
            0f
        }
    }
}