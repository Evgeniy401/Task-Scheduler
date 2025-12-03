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

        return if (networkManager.isConnected()) {
            try {

                taskStorage.saveToServer(task)
            } catch (e: Exception) {

                val taskWithSync = task.copy(needsSync = true)
                taskStorage.save(taskWithSync)
                taskWithSync
            }
        } else {

            val taskWithSync = task.copy(needsSync = true)
            taskStorage.save(taskWithSync)
            taskWithSync
        }
    }

    override fun getAllTasks(): Flow<List<Task>> =
        taskStorage.getAllTasks()

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun deleteTask(taskId: Int) {

        val task = taskStorage.getTaskById(taskId)

        if (networkManager.isConnected()) {
            try {
                taskStorage.deleteTaskFromServer(taskId)
            } catch (e: Exception) {
                task?.let {
                    val markedForDeletion = it.copy(isDeleted = true, needsSync = true)
                    taskStorage.save(markedForDeletion)
                }
            }
        } else {
            task?.let {
                val markedForDeletion = it.copy(isDeleted = true, needsSync = true)
                taskStorage.save(markedForDeletion)
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