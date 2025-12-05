package com.example.domain.usecase

import com.example.domain.model.StatisticDomainModel
import com.example.domain.repository.StatisticRepository
import com.example.domain.repository.TaskRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class CalculateStatisticUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke(): Flow<Pair<StatisticDomainModel?, Int>> {
        return combine(
            statisticRepository.getStatistic(),
            taskRepository.getActiveTasks().map { it.size }
        ) { statistic, activeTasksCount ->
            Pair(statistic, activeTasksCount)
        }
    }
}