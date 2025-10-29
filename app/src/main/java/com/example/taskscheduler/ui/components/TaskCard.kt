package com.example.taskscheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskscheduler.domain.model.Priority
import com.example.taskscheduler.domain.model.Task
import com.example.taskscheduler.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close


val task1 = Task(
    id = 1,
    title = "Помыть машину",
    body = "Это очень длинное описание задачи, которое занимает несколько строк и должно автоматически увеличивать высоту карточки в зависимости от объема текста",
    priority = Priority.STANDARD
)

@Composable
fun TaskCard(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = when (task.priority) {
                Priority.STANDARD -> Color(0xFFE8F5E8)
                Priority.HIGH -> Color(0xFFFFF9C4)
                Priority.MAXIMUM -> Color(0xFFFFEBEE)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall,
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                text = task.body,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium
            )

            Row() {

                IconButton(
                    onClick = { /* действие при клике */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Описание для доступности"
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    text = when (task.priority) {
                        Priority.STANDARD -> stringResource(R.string.priority_standard)
                        Priority.HIGH -> stringResource(R.string.priority_high)
                        Priority.MAXIMUM -> stringResource(R.string.priority_maximum)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                )

                IconButton(
                    onClick = { /* действие при клике */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Описание для доступности"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskCardPreview() {
    TaskCard(task = task1)
}