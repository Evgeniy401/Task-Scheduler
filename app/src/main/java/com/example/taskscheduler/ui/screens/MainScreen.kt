package com.example.taskscheduler.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskscheduler.ui.components.GeneralButton
import com.example.taskscheduler.ui.theme.TaskSchedulerTheme

@Composable
fun MainScreen(
    onNavigateToStatistic: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 35.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        GeneralButton(
            onClick = {

            }
        ) {
            Text(
                text = "Новая задача",
                fontSize = 20.sp,

                )
        }

        GeneralButton(
            modifier = Modifier
                .padding(top = 10.dp),
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    TaskSchedulerTheme() {
        MainScreen(
            onNavigateToStatistic = {}
        )
    }
}