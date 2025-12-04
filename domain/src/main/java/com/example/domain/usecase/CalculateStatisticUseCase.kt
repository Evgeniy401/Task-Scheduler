package com.example.domain.usecase

import com.example.domain.model.StatisticDomainModel
import com.example.domain.repository.StatisticRepository
import com.example.domain.repository.TaskRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class CalculateStatisticUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository,
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<Pair<StatisticDomainModel?, Int>> {
        return combine(
            statisticRepository.getStatistic(),
            taskRepository.getAllTasks()
        ) { domainModel, currentTasks ->
            domainModel to currentTasks.size
        }
    }
}