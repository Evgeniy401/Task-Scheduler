package com.example.taskscheduler.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WindowNewTaskScreenViewModel : ViewModel() {

    private val _textStateLabel = MutableStateFlow("")
    val textStateLabel = _textStateLabel.asStateFlow()

    private val _textStateDescription = MutableStateFlow("")
    val textStateDescription = _textStateDescription.asStateFlow()

    fun updateTextLabel(newTextLabel: String) {
        _textStateLabel.value = newTextLabel
    }

    fun updateTextDescription(newTextDescription: String) {
        _textStateDescription.value = newTextDescription
    }
}