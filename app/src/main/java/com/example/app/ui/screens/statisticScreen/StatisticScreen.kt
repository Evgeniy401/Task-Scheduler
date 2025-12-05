package com.example.app.ui.screens.statisticScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material3.CircularProgressIndicator
import com.example.app.ui.components.GeneralButton
import com.example.app.ui.components.WarningDialog
import com.example.app.ui.theme.TaskSchedulerTheme

@Composable
fun StatisticScreen(
    onBack: () -> Unit,
    viewModel: StatisticViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.showConfirmationDialog) {
        ResetConfirmationDialog(
            onConfirm = { viewModel.confirmReset() },
            onCancel = { viewModel.cancelReset() }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(130.dp),
                windowInsets = WindowInsets(0.dp),
                containerColor = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GeneralButton(
                        onClick = { viewModel.showResetConfirmation() }
                    ) {
                        Text(
                            text = "Сбросить статистику",
                            fontSize = 20.sp,
                        )
                    }
                    GeneralButton(
                        onClick = {
                            onBack()
                        }
                    ) {
                        Text(
                            text = "Назад",
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 130.dp)
                        .statusBarsPadding()
                        .padding(horizontal = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    StatRow("Создано задач", uiState.totalTasksCreated.toString())
                    StatRow("Текущих задач", uiState.currentTasks.toString())
                    StatRow("Выполнено", uiState.completedTasks.toString())
                    StatRow("Отменено", uiState.cancelledTasks.toString())

                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    StatRow("Процент выполнения ", "${uiState.completionPercentage}%")
                }
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun ResetConfirmationDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    WarningDialog(
        title = "Сброс статистики",
        message = "Вы уверены, что хотите сбросить всю статистику? Это действие невозможно отменить.",
        confirmButtonText = "Сбросить",
        cancelButtonText = "Отмена",
        onConfirm = onConfirm,
        onCancel = onCancel,
        onDismiss = onCancel
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatisticScreenPreview() {
    TaskSchedulerTheme {
        StatisticScreen(
            onBack = {},
        )
    }
}