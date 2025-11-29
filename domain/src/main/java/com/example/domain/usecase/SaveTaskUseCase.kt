package com.example.domain.usecase

import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(
        title: String,
        body: String,
        priorityDomain: PriorityDomain
    ) {
        val task = Task(
            id = 0,
            title = title,
            body = body,
            priorityDomain = priorityDomain,
            isCompleted = false,
        )
        taskRepository.saveTask(task)
    }
}