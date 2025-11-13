package com.example.app.ui.screens.WindowNewTaskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigationevent.NavigationEvent
import com.example.domain.model.PriorityDomain
import com.example.domain.usecase.SaveTaskUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WindowNewTaskScreenViewModel(
    private val saveTaskUseCase: SaveTaskUseCase
): ViewModel() {

    private val _selectedPriority = MutableStateFlow(PriorityDomain.NONE)
    val selectedPriority = _selectedPriority.asStateFlow()
    fun updateSelectedPriority(priority: PriorityDomain) {
        _selectedPriority.value = priority
    }

    private val _textStateLabel = MutableStateFlow("")
    val textStateLabel = _textStateLabel
    fun updateTextLabel(newTextLabel: String) {
        _textStateLabel.value = newTextLabel
    }

    private val _textStateDescription = MutableStateFlow("")
    val textStateDescription = _textStateDescription
    fun updateTextDescription(newTextDescription: String) {
        _textStateDescription.value = newTextDescription
    }

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun saveTask() {
        if (_selectedPriority.value != PriorityDomain.NONE && _textStateLabel.value.isNotBlank()) {
            viewModelScope.launch {
                saveTaskUseCase(
                    title = _textStateLabel.value,
                    body = _textStateDescription.value,
                    priorityDomain = _selectedPriority.value
                )
                // ВМЕСТО вызова колбэка: отправляем событие
                _navigationEvent.emit(NavigationEvent.NavigateBack)
            }
        } else {
            // добавить поверку валидации
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateBack)
        }
    }

    sealed class NavigationEvent {
        object NavigateBack : NavigationEvent()

    }




}