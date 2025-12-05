package com.example.app.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.mapping.TaskDomainUiMapper
import com.example.domain.model.Task
import com.example.domain.usecase.CompleteTaskUseCase
import com.example.domain.usecase.DeleteTaskUseCase
import com.example.domain.usecase.EditTaskUseCase
import com.example.domain.usecase.GetActiveTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.usecase.GetTaskByIdUseCase

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val getActiveTasksUseCase: GetActiveTasksUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    val taskMapper: TaskDomainUiMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    private val _taskToEdit = MutableStateFlow<Task?>(null)
    val taskToEdit: StateFlow<Task?> = _taskToEdit.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        getActiveTasksUseCase()
            .onEach { tasks ->
                val taskItems = taskMapper.toUiList(tasks)
                _uiState.update { currentState ->
                    currentState.copy(tasks = taskItems)
                }
            }
            .launchIn(viewModelScope)
    }

    fun setTaskForEdit(taskId: Int) {
        viewModelScope.launch {
            val task = getTaskByIdUseCase(taskId)
            _taskToEdit.value = task
        }
    }

    fun clearTaskForEdit() {
        _taskToEdit.value = null
    }

    fun editTask(
        taskId: Int,
        title: String,
        body: String,
        priorityDomain: com.example.domain.model.PriorityDomain
    ) {
        viewModelScope.launch {
            editTaskUseCase(
                taskId = taskId,
                title = title,
                body = body,
                priorityDomain = priorityDomain
            )
        }
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
                        deleteTaskUseCase(action.taskId)
                    }
                    is MainScreenState.ConfirmationType.Complete -> {
                        completeTaskUseCase(action.taskId)
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