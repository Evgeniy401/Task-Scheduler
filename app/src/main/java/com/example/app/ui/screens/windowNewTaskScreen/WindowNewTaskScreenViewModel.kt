package com.example.app.ui.screens.windowNewTaskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PriorityDomain
import com.example.domain.repository.TaskRepository
import com.example.domain.usecase.DeterminePriorityUseCase
import com.example.domain.usecase.EditTaskUseCase
import com.example.domain.usecase.GetTaskByIdUseCase
import com.example.domain.usecase.SaveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowNewTaskScreenViewModel @Inject constructor(
    private val saveTaskUseCase: SaveTaskUseCase,
    private val determinePriorityUseCase: DeterminePriorityUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WindowNewTaskScreenState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun loadTaskForEdit(taskId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val task = getTaskByIdUseCase(taskId)
                if (task != null) {
                    _uiState.update {
                        it.copy(
                            title = task.title,
                            description = task.body,
                            selectedPriority = task.priorityDomain,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Задача не найдена"
                        )
                    }
                    _navigationEvent.emit(NavigationEvent.ShowError("Задача не найдена"))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Ошибка загрузки задачи"
                    )
                }
                _navigationEvent.emit(NavigationEvent.ShowError("Ошибка загрузки задачи"))
            }
        }
    }

    fun saveTask() {
        if (uiState.value.title.isNotBlank()) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                try {
                    val priorityToSave = determinePriorityUseCase(uiState.value.selectedPriority)

                    saveTaskUseCase(
                        title = uiState.value.title,
                        body = uiState.value.description,
                        priorityDomain = priorityToSave
                    )

                    clearState()
                    _navigationEvent.emit(NavigationEvent.NavigateBack)
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Ошибка сохранения задачи"
                        )
                    }
                    _navigationEvent.emit(NavigationEvent.ShowError("Ошибка сохранения задачи"))
                }
            }
        } else {
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.ShowValidationError)
            }
        }
    }

    fun updateTask(taskId: Int) {
        if (uiState.value.title.isNotBlank()) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                try {
                    val priorityToSave = determinePriorityUseCase(uiState.value.selectedPriority)

                    editTaskUseCase(
                        taskId = taskId,
                        title = uiState.value.title,
                        body = uiState.value.description,
                        priorityDomain = priorityToSave
                    )

                    clearState()
                    _navigationEvent.emit(NavigationEvent.NavigateBack)
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Ошибка обновления задачи"
                        )
                    }
                    _navigationEvent.emit(NavigationEvent.ShowError("Ошибка обновления задачи"))
                }
            }
        } else {
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.ShowValidationError)
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updateSelectedPriority(priority: PriorityDomain) {
        _uiState.update { it.copy(selectedPriority = priority) }
    }

    private fun clearState() {
        _uiState.update {
            WindowNewTaskScreenState()
        }
    }

    sealed class NavigationEvent {
        object NavigateBack : NavigationEvent()
        object ShowValidationError : NavigationEvent()
        data class ShowError(val message: String) : NavigationEvent()
    }
}

data class WindowNewTaskScreenState(
    val title: String = "",
    val description: String = "",
    val selectedPriority: PriorityDomain = PriorityDomain.NONE,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)