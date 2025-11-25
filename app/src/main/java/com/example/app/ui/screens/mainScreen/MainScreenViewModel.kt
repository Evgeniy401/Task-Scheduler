package com.example.app.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog.asStateFlow()

    private val _taskToDelete = MutableStateFlow<Int?>(null)
    val taskToDelete: StateFlow<Int?> = _taskToDelete.asStateFlow()

    fun showDeleteConfirmation(taskId: Int) {
        _taskToDelete.value = taskId
        _showDeleteDialog.value = true
    }

    fun confirmDelete() {
        val taskId = _taskToDelete.value
        if (taskId != null) {
            viewModelScope.launch {
                taskRepository.deleteTask(taskId)
            }
        }
        hideDeleteDialog()
    }

    fun cancelDelete() {
        hideDeleteDialog()
    }

    private fun hideDeleteDialog() {
        _showDeleteDialog.value = false
        _taskToDelete.value = null
    }

    val tasks = taskRepository.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }

    fun completeTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.completeTask(taskId)
        }
    }
}