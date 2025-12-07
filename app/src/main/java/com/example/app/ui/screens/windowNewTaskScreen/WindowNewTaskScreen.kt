package com.example.app.ui.screens.windowNewTaskScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.app.ui.components.GeneralButton
import com.example.app.ui.theme.TaskSchedulerTheme
import com.example.app.R
import com.example.app.ui.utils.getPriorityText
import com.example.domain.model.PriorityDomain

@Composable
fun WindowNewTaskScreen(
    taskId: Int? = null,
    onBack: () -> Unit,
    viewModel: WindowNewTaskScreenViewModel = hiltViewModel()
) {
    if (taskId != null) {
        LaunchedEffect(taskId) {
            viewModel.loadTaskForEdit(taskId)
        }
    }

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is WindowNewTaskScreenViewModel.NavigationEvent.NavigateBack -> {
                    onBack()
                }

                is WindowNewTaskScreenViewModel.NavigationEvent.ShowValidationError -> {
                    snackbarHostState.showSnackbar("Заполните название задачи")
                }

                is WindowNewTaskScreenViewModel.NavigationEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    var isExpanded by remember { mutableStateOf(false) }
    val priorities = PriorityDomain.entries.toTypedArray().filter { it != PriorityDomain.NONE }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(10.dp),
                    containerColor = Color(0xFFD32F2F),
                    contentColor = Color.White
                ) {
                    Text(
                        text = data.visuals.message,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(130.dp),
                windowInsets = WindowInsets(0.dp),
                containerColor = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GeneralButton(
                        onClick = {
                            if (taskId != null) {
                                viewModel.updateTask(taskId)
                            } else {
                                viewModel.saveTask()
                            }
                        }
                    ) {
                        Text(
                            text = if (taskId != null) "Сохранить изменения" else "Сохранить задачу",
                            fontSize = 20.sp,
                        )
                    }
                    GeneralButton(
                        onClick = { onBack() },
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
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        value = uiState.title,
                        onValueChange = { newTitle ->
                            viewModel.updateTitle(newTitle)
                        },
                        shape = RoundedCornerShape(10.dp),
                        label = { Text(text = "Название задачи") },
                        singleLine = true,
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        value = uiState.description,
                        onValueChange = { newDescription ->
                            viewModel.updateDescription(newDescription)
                        },
                        shape = RoundedCornerShape(10.dp),
                        label = { Text(text = "Содержание задачи") },
                        maxLines = 12,
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .wrapContentSize(Alignment.TopCenter),
                    ) {
                        Button(
                            onClick = { isExpanded = !isExpanded },
                            modifier = Modifier.wrapContentWidth(),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text(
                                text = getPriorityText(
                                    uiState.selectedPriority,
                                    includePrefix = true
                                )
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ArrowDropUp
                                else Icons.Default.ArrowDropDown,
                                contentDescription = stringResource(R.string.open_menu)
                            )
                        }
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false },
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            priorities.forEach { priority ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.updateSelectedPriority(priority)
                                        isExpanded = false
                                    },
                                    text = {
                                        Text(
                                            text = getPriorityText(priority, includePrefix = false),
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WindowNewTaskScreenPreview() {
    TaskSchedulerTheme {
        WindowNewTaskScreen(
            onBack = {},
        )
    }
}