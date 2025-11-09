package com.example.taskscheduler.domain.usecase

import com.example.taskscheduler.domain.model.Priority
import com.example.taskscheduler.domain.model.Task
import com.example.taskscheduler.domain.repository.TaskRepository
import com.example.taskscheduler.domain.utils.IdGenerator

class SaveTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(
        title: String,
        body: String,
        priority: Priority
    ) {
        val id = IdGenerator.generateId()
        val task = Task(
            id = id,
            title = title,
            body = body,
            priority = priority
        )
        taskRepository.saveTask(task)
    }
}