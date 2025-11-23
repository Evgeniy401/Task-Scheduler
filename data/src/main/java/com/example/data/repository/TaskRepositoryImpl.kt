package com.example.data.repository

import com.example.data.storage.TaskStorage
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class TaskRepositoryImpl@Inject constructor(
    private val taskStorage: TaskStorage
): TaskRepository {

    private val updateFlow = MutableSharedFlow<Unit>(replay = 1)

    init {
        updateFlow.tryEmit(Unit)
    }

    override suspend fun saveTask(task: Task) {
        taskStorage.save(task)
        updateFlow.emit(Unit)
    }

    override fun getTasks(): Flow<List<Task>> = flow {
        updateFlow.collect {
            emit(taskStorage.getAllTasks())
        }
    }

    override suspend fun deleteTask(taskId: Int) {}
}