package com.example.domain.usecase

import com.example.domain.repository.StatisticRepository
import com.example.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val statisticRepository: StatisticRepository
) {
    suspend operator fun invoke(taskId: Int) {
        statisticRepository.incrementTotalDeleted()

        val task = repository.getTaskById(taskId)
        if (task != null) {
            val deletedTask = task.copy(
                isDeleted = true,
                lastModified = System.currentTimeMillis()
            )
            repository.saveTask(deletedTask)
        }
    }
}