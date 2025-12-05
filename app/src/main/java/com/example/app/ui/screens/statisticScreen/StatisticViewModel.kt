package com.example.app.ui.screens.statisticScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.mapping.StatisticDomainToUiMapper
import com.example.domain.usecase.CalculateStatisticUseCase
import com.example.domain.usecase.ResetStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val calculateStatisticUseCase: CalculateStatisticUseCase,
    private val resetStatisticsUseCase: ResetStatisticsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticScreenState())
    val uiState: StateFlow<StatisticScreenState> = _uiState.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        calculateStatisticUseCase()
            .onEach { (domainModel, currentTasksCount) ->
                if (domainModel != null) {
                    val uiModel = StatisticDomainToUiMapper.toUi(
                        domain = domainModel,
                        currentTasksCount = currentTasksCount
                    )

                    _uiState.update {
                        it.copy(
                            totalTasksCreated = uiModel.totalTasksCreated,
                            currentTasks = uiModel.currentTasks,
                            completedTasks = uiModel.completedTasks,
                            cancelledTasks = uiModel.cancelledTasks,
                            completionPercentage = uiModel.completionPercentage
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            totalTasksCreated = 0,
                            currentTasks = currentTasksCount,
                            completedTasks = 0,
                            cancelledTasks = 0,
                            completionPercentage = 0f
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun showResetConfirmation() {
        _uiState.update {
            it.copy(
                showConfirmationDialog = true
            )
        }
    }

    fun resetStatistics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                resetStatisticsUseCase()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Ошибка сброса статистики"
                    )
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun confirmReset() {
        _uiState.update { it.copy(showConfirmationDialog = false) }
        resetStatistics()
    }

    fun cancelReset() {
        _uiState.update { it.copy(showConfirmationDialog = false) }
    }
}

data class StatisticScreenState(
    val totalTasksCreated: Int = 0,
    val currentTasks: Int = 0,
    val completedTasks: Int = 0,
    val cancelledTasks: Int = 0,
    val completionPercentage: Float = 0f,
    val isLoading: Boolean = false,
    val showConfirmationDialog: Boolean = false,
    val errorMessage: String? = null
)