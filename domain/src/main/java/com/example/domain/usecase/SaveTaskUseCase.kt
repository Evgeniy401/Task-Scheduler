package com.example.domain.usecase

import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import com.example.domain.utils.IdGenerator
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(
        title: String,
        body: String,
        priorityDomain: PriorityDomain
    ) {
        val id = IdGenerator.generateId()
        val task = Task(
            id = id,
            title = title,
            body = body,
            priorityDomain = priorityDomain
        )
        taskRepository.saveTask(task)
    }
}