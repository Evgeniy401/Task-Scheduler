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
    private val networkManager: NetworkManager,
) : TaskRepository {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun saveTask(task: Task): Task {

        taskStorage.save(task)

        if (networkManager.isConnected()) {
            try {
                return taskStorage.saveToServer(task)
            } catch (e: Exception) {
                val taskWithSync = task.copy(needsSync = true)
                taskStorage.save(taskWithSync)
                return taskWithSync
            }
        }

        val taskWithSync = task.copy(needsSync = true)
        taskStorage.save(taskWithSync)
        return taskWithSync
    }

    override fun getAllTasks(): Flow<List<Task>> =
        taskStorage.getAllTasks()

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun deleteTask(taskId: Int) {

        val task = taskStorage.getTaskById(taskId)

        if (networkManager.isConnected() && task != null && task.id > 0) {

            try {
                taskStorage.deleteTaskFromServer(taskId)
            } catch (e: Exception) {
                val markedForDeletion = task.copy(isDeleted = true, needsSync = true)
                taskStorage.save(markedForDeletion)
            }

        } else {
            taskStorage.deleteTask(taskId)

            task?.let {
                if (it.id > 0) {
                    val markedForDeletion = it.copy(isDeleted = true, needsSync = true)
                    taskStorage.save(markedForDeletion)
                }
            }
        }
    }

    override suspend fun completeTask(taskId: Int) =
        taskStorage.completeTask(taskId)

    override suspend fun syncTasks() {
        taskStorage.syncTasks()
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskStorage.getTaskById(taskId)
    }
}