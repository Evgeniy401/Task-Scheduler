package com.example.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.data.network.NetworkManager
import com.example.data.storage.TaskStorage
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskStorage: TaskStorage,
    private val networkManager: NetworkManager
) : TaskRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun saveTask(task: Task): Task {
        return if (networkManager.isConnected()) {
            taskStorage.saveToServer(task)
        } else {
            taskStorage.save(task)
            task
        }
    }

    override fun getAllTasks(): Flow<List<Task>> =
        taskStorage.getAllTasks()

    override suspend fun deleteTask(taskId: Int) =
        taskStorage.deleteTask(taskId)

    override suspend fun completeTask(taskId: Int) =
        taskStorage.completeTask(taskId)

    override suspend fun syncTasks() {
        taskStorage.syncTasks()
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskStorage.getTaskById(taskId)
    }
}