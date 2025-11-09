package com.example.taskscheduler.ui.screens.WindowNewTaskScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class WindowNewTaskScreenViewModel : ViewModel() {

    var counter = 0

    private val _textStateLabel = MutableStateFlow("")
    val textStateLabel = _textStateLabel

    private val _textStateDescription = MutableStateFlow("")
    val textStateDescription = _textStateDescription

    fun updateTextLabel(newTextLabel: String) {
        _textStateLabel.value = newTextLabel
    }

    fun updateTextDescription(newTextDescription: String) {
        _textStateDescription.value = newTextDescription
    }
}