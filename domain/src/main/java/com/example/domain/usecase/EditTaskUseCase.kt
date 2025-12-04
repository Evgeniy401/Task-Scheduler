package com.example.domain.usecase

import com.example.domain.model.PriorityDomain
import com.example.domain.repository.TaskRepository
import javax.inject.Inject

    class EditTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
        suspend operator fun invoke(
            taskId: Int,
            title: String? = null,
            body: String? = null,
            priorityDomain: PriorityDomain? = null
        ) {
            val existingTask = taskRepository.getTaskById(taskId)
            if (existingTask != null) {
                val updatedTask = existingTask.copy(
                    title = title ?: existingTask.title,
                    body = body ?: existingTask.body,
                    priorityDomain = priorityDomain ?: existingTask.priorityDomain,
                    needsSync = true,
                    lastModified = System.currentTimeMillis()
                )
                taskRepository.saveTask(updatedTask)
            }
        }
    }
