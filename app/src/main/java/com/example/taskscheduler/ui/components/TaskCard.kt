package com.example.taskscheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskscheduler.domain.model.Priority
import com.example.taskscheduler.domain.model.Task
import com.example.taskscheduler.R
import androidx.compose.ui.Alignment

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

            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                text = task.body,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium
            )

            Column(
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    text = when (task.priority) {
                        Priority.STANDARD -> stringResource(R.string.priority_standard)
                        Priority.HIGH -> stringResource(R.string.priority_high)
                        Priority.MAXIMUM -> stringResource(R.string.priority_maximum)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                )

                Row {
                    GeneralButton(
                        modifier = Modifier
                            .width(150.dp),
                        onClick = {},
                    ) {
                        Text(
                            text = "Выполнить "
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )

                    GeneralButton(
                        modifier = Modifier
                            .width(150.dp),
                        onClick = {}
                    ) {
                        Text(
                            text = "Отменить "
                        )
                    }
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