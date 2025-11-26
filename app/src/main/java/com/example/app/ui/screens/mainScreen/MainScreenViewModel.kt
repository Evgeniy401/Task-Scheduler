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

    sealed class ConfirmationType {
        data object Delete : ConfirmationType()
        data object Complete : ConfirmationType()
    }

    data class PendingAction(
        val taskId: Int,
        val type: ConfirmationType
    )

    private val _pendingAction = MutableStateFlow<PendingAction?>(null)
    private val _showConfirmationDialog = MutableStateFlow(false)

    val showConfirmationDialog: StateFlow<Boolean> = _showConfirmationDialog.asStateFlow()
    val currentPendingAction: StateFlow<PendingAction?> = _pendingAction.asStateFlow()

    fun showDeleteConfirmation(taskId: Int) {
        showConfirmation(ConfirmationType.Delete, taskId)
    }

    fun showCompleteConfirmation(taskId: Int) {
        showConfirmation(ConfirmationType.Complete, taskId)
    }

    fun confirmAction() {
        val action = _pendingAction.value
        if (action != null) {
            viewModelScope.launch {
                when (action.type) {
                    is ConfirmationType.Delete -> taskRepository.deleteTask(action.taskId)
                    is ConfirmationType.Complete -> taskRepository.completeTask(action.taskId)
                }
            }
        }
        hideConfirmation()
    }

    fun cancelAction() {
        hideConfirmation()
    }

    private fun showConfirmation(type: ConfirmationType, taskId: Int) {
        _pendingAction.value = PendingAction(taskId, type)
        _showConfirmationDialog.value = true
    }

    private fun hideConfirmation() {
        _showConfirmationDialog.value = false
        _pendingAction.value = null
    }

    val tasks = taskRepository.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}