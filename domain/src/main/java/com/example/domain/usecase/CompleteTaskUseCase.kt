package com.example.domain.usecase
import com.example.domain.repository.TaskRepository
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int) {
        repository.completeTask(taskId)
    }
}