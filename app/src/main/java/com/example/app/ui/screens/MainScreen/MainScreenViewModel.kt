package com.example.app.ui.screens.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.domain.model.PriorityDomain
import com.example.app.domain.usecase.SaveTaskUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveTaskUseCase: SaveTaskUseCase): ViewModel() {

    fun saveNewTask(title: String, body: String, priorityDomain: PriorityDomain) {
        viewModelScope.launch {
            saveTaskUseCase(title, body, priorityDomain)
        }
    }
    }