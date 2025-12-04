package com.example.domain.usecase

import com.example.domain.repository.StatisticRepository
import javax.inject.Inject

class ResetStatisticsUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository,
) {
    suspend operator fun invoke() {
        statisticRepository.resetStatistics()
    }
}