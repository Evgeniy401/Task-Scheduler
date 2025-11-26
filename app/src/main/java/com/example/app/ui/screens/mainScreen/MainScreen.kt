package com.example.app.ui.screens.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.app.ui.components.GeneralButton
import com.example.app.ui.components.TaskCard
import com.example.app.ui.components.WarningDialog
import com.example.app.ui.theme.TaskSchedulerTheme
import com.example.domain.model.Task

@Composable
fun MainScreen(
    onNavigateToStatistic: () -> Unit,
    onNavigateToWindowNewTask: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.showConfirmationDialog) {
        val dialogConfig = when (uiState.pendingAction?.type) {
            is MainScreenState.ConfirmationType.Delete -> DialogConfig.Delete
            is MainScreenState.ConfirmationType.Complete -> DialogConfig.Complete
            null -> DialogConfig.Delete
        }

        ConfirmationDialog(
            config = dialogConfig,
            onConfirm = { viewModel.confirmAction() },
            onCancel = { viewModel.cancelAction() }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(130.dp),
                windowInsets = WindowInsets(0.dp),
                containerColor = Color.Transparent,
            ) {
                ButtonsColumn(
                    onNewTask = onNavigateToWindowNewTask,
                    onStatistics = onNavigateToStatistic
                )
            }
        }
    ) { paddingValues ->
        TaskList(
            modifier = Modifier.padding(paddingValues),
            tasks = uiState.tasks,
            onDeleteTask = viewModel::showDeleteConfirmation,
            onCompleteTask = viewModel::showCompleteConfirmation,
        )
    }
}

private sealed class DialogConfig(
    val title: String,
    val message: String
) {
    data object Delete : DialogConfig(
        title = "Подтверждение действия",
        message = "Вы уверены, что хотите удалить эту задачу?"
    )

    data object Complete : DialogConfig(
        title = "Подтверждение действия",
        message = "Задача выполнена?"
    )
}

@Composable
private fun ButtonsColumn(
    onNewTask: () -> Unit,
    onStatistics: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GeneralButton(onClick = onNewTask) {
            Text(text = "Новая задача", fontSize = 20.sp)
        }
        GeneralButton(onClick = onStatistics) {
            Text(text = "Статистика", fontSize = 20.sp)
        }
    }
}

@Composable
private fun ConfirmationDialog(
    config: DialogConfig,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    WarningDialog(
        title = config.title,
        message = config.message,
        onConfirm = onConfirm,
        onCancel = onCancel,
        onDismiss = onCancel
    )
}

@Composable
private fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<MainScreenState.TaskItem> = emptyList(),
    onCompleteTask: (Int) -> Unit = {},
    onDeleteTask: (Int) -> Unit = {}
) {
    val groupedTasks = remember(tasks) {
        tasks.groupBy { it.priorityGroup }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        groupedTasks.forEach { (priorityGroup, tasksInGroup) ->
            item {
                PriorityHeader(priorityGroup)
            }
            items(tasksInGroup) { task ->
                TaskCard(
                    task = task.toDomainTask(),
                    onCompleteTask = onCompleteTask,
                    onDeleteTask = onDeleteTask,
                )
            }
        }
    }
}

@Composable
private fun PriorityHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

private fun MainScreenState.TaskItem.toDomainTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        body = this.body,
        priorityDomain = this.priority
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    TaskSchedulerTheme {
        MainScreen(
            onNavigateToStatistic = {},
            onNavigateToWindowNewTask = {}
        )
    }
}