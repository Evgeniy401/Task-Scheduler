package com.example.app.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.mapping.TaskDomainUiMapper
import com.example.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskMapper: TaskDomainUiMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        taskRepository.getAllTasks()
            .onEach { tasks ->
                val taskItems = taskMapper.toUiState(tasks)
                _uiState.update { currentState ->
                    currentState.copy(tasks = taskItems)
                }
            }
            .launchIn(viewModelScope)
    }

    fun showDeleteConfirmation(taskId: Int) {
        showConfirmation(MainScreenState.ConfirmationType.Delete, taskId)
    }

    fun showCompleteConfirmation(taskId: Int) {
        showConfirmation(MainScreenState.ConfirmationType.Complete, taskId)
    }

    fun confirmAction() {
        val action = _uiState.value.pendingAction
        if (action != null) {
            viewModelScope.launch {
                when (action.type) {
                    is MainScreenState.ConfirmationType.Delete -> {
                        taskRepository.deleteTask(action.taskId)
                    }
                    is MainScreenState.ConfirmationType.Complete -> {
                        taskRepository.completeTask(action.taskId)
                    }
                }
            }
        }
        hideConfirmation()
    }

    fun cancelAction() {
        hideConfirmation()
    }

    private fun showConfirmation(type: MainScreenState.ConfirmationType, taskId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                showConfirmationDialog = true,
                pendingAction = MainScreenState.PendingAction(taskId, type)
            )
        }
    }

    private fun hideConfirmation() {
        _uiState.update { currentState ->
            currentState.copy(
                showConfirmationDialog = false,
                pendingAction = null
            )
        }
    }
}