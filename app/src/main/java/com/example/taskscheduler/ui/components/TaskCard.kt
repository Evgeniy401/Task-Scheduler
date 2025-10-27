package com.example.taskscheduler.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskscheduler.domain.model.Task

@Composable
fun TaskCard(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        val task1 = Task(
            id = 1,
            title = "Помыть машину",
            body = "описание задачи",
            status = true
        )


        Text(
            text = task.title
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskCardPreview() {
    TaskCard(task = task1)
}