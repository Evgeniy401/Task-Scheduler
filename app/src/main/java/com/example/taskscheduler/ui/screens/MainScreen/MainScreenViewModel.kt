package com.example.taskscheduler.ui.screens.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskscheduler.domain.model.Priority
import com.example.taskscheduler.domain.usecase.SaveTaskUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveTaskUseCase: SaveTaskUseCase): ViewModel() {

    fun saveNewTask(title: String, body: String, priority: Priority) {
        viewModelScope.launch {
            saveTaskUseCase(title, body, priority)
        }
    }
    }