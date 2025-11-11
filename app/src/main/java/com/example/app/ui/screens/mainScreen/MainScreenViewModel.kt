package com.example.app.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PriorityDomain
import com.example.domain.usecase.SaveTaskUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveTaskUseCase: SaveTaskUseCase
): ViewModel() {

    fun saveNewTask(title: String, body: String, priorityDomain: PriorityDomain) {
        viewModelScope.launch {
            saveTaskUseCase(title, body, priorityDomain)
        }
    }
    }