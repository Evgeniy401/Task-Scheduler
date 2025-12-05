package com.example.domain.usecase
import com.example.domain.repository.StatisticRepository
import com.example.domain.repository.TaskRepository
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val statisticRepository: StatisticRepository
) {
    suspend operator fun invoke(taskId: Int) {
        val task = repository.getTaskById(taskId)

        if (task != null && !task.isCompleted) {
            statisticRepository.incrementTotalCompleted()
            val completedTask = task.copy(
                isCompleted = true,
                lastModified = System.currentTimeMillis()
            )
            repository.saveTask(completedTask)
        }
    }
}