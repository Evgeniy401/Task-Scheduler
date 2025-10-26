package com.example.taskscheduler.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StatisticScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Statistic Screen"
        )
    }
}
