package com.example.domain.usecase

import com.example.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int) = taskRepository.getTaskById(taskId)
}