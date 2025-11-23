package com.example.app.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = listOf(
                Task(
                    id = 1,
                    title = "Пример задачи 1",
                    body = "Описание задачи 1",
                    priorityDomain = PriorityDomain.STANDARD
                ),
                Task(
                    id = 2,
                    title = "Пример задачи 2",
                    body = "Описание задачи 2",
                    priorityDomain = PriorityDomain.HIGH
                )
            )
        }
    }
}