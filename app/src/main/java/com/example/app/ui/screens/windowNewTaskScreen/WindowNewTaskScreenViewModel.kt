package com.example.app.ui.screens.windowNewTaskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PriorityDomain
import com.example.domain.repository.TaskRepository
import com.example.domain.usecase.DeterminePriorityUseCase
import com.example.domain.usecase.EditTaskUseCase
import com.example.domain.usecase.SaveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WindowNewTaskScreenViewModel @Inject constructor(
    private val saveTaskUseCase: SaveTaskUseCase,
    private val determinePriorityUseCase: DeterminePriorityUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _selectedPriority = MutableStateFlow(PriorityDomain.NONE)
    val selectedPriority = _selectedPriority.asStateFlow()
    fun updateSelectedPriority(priority: PriorityDomain) {
        _selectedPriority.value = priority
    }

    private val _textStateLabel = MutableStateFlow("")
    val textStateLabel = _textStateLabel.asStateFlow()
    fun updateTextLabel(newTextLabel: String) {
        _textStateLabel.value = newTextLabel
    }

    private val _textStateDescription = MutableStateFlow("")
    val textStateDescription = _textStateDescription.asStateFlow()
    fun updateTextDescription(newTextDescription: String) {
        _textStateDescription.value = newTextDescription
    }

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun loadTaskForEdit(taskId: Int) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)
            if (task != null) {
                _textStateLabel.value = task.title
                _textStateDescription.value = task.body
                _selectedPriority.value = task.priorityDomain
            }
        }
    }

    fun saveTask() {
        if (_textStateLabel.value.isNotBlank()) {
            viewModelScope.launch {
                val priorityToSave = determinePriorityUseCase(_selectedPriority.value)

                saveTaskUseCase(
                    title = _textStateLabel.value,
                    body = _textStateDescription.value,
                    priorityDomain = priorityToSave
                )

                clearState()
                _navigationEvent.emit(NavigationEvent.NavigateBack)
            }
        } else {
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.ShowValidationError)
            }
        }
    }

    fun updateTask(taskId: Int) {
        if (_textStateLabel.value.isNotBlank()) {
            viewModelScope.launch {
                val priorityToSave = determinePriorityUseCase(_selectedPriority.value)

                editTaskUseCase(
                    taskId = taskId,
                    title = _textStateLabel.value,
                    body = _textStateDescription.value,
                    priorityDomain = priorityToSave
                )

                clearState()
                _navigationEvent.emit(NavigationEvent.NavigateBack)
            }
        } else {
            viewModelScope.launch {
                _navigationEvent.emit(NavigationEvent.ShowValidationError)
            }
        }
    }

    private fun clearState() {
        _textStateLabel.value = ""
        _textStateDescription.value = ""
        _selectedPriority.value = PriorityDomain.NONE
    }

    sealed class NavigationEvent {
        object NavigateBack : NavigationEvent()
        object ShowValidationError : NavigationEvent()
    }




}