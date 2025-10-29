package com.example.taskscheduler.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.taskscheduler.domain.model.Task
import com.example.taskscheduler.ui.components.GeneralButton
import com.example.taskscheduler.ui.components.TaskCard
import com.example.taskscheduler.ui.theme.TaskSchedulerTheme

@Composable
fun MainScreen(
    onNavigateToStatistic: () -> Unit,
    onNavigateToWindowNewTask: () -> Unit,
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(130.dp),
                containerColor = Color.Transparent
                ,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    GeneralButton(
                        onClick = {
                            onNavigateToWindowNewTask()
                        }
                    ) {
                        Text(
                            text = "Новая задача",
                            fontSize = 20.sp,
                        )
                    }
                    GeneralButton(
                        modifier = Modifier
                            .padding(top = 4.dp),
                        onClick = {
                            onNavigateToStatistic()
                        }
                    ) {
                        Text(
                            text = "Статистика",
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        TaskList(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: List<Task> = emptyList()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(tasks) {task ->
            TaskCard(task = task)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    TaskSchedulerTheme() {
        MainScreen(
            onNavigateToStatistic = {},
            onNavigateToWindowNewTask = {}
        )
    }
}