package com.example.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task
import com.example.app.ui.utils.getPriorityText

val task1 = Task(
    id = 1,
    title = "Помыть машину",
    body = "Это очень длинное описание задачи, которое занимает несколько строк и должно автоматически увеличивать высоту карточки в зависимости от объема текста",
    priorityDomain = PriorityDomain.STANDARD
)

@Composable
fun TaskCard(
    task: Task,
    onCompleteTask: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = when (task.priorityDomain) {
                PriorityDomain.NONE -> Color(0xFFF5F5F5)
                PriorityDomain.STANDARD -> Color(0xFFE8F5E8)
                PriorityDomain.HIGH -> Color(0xFFFFF9C4)
                PriorityDomain.MAXIMUM -> Color(0xFFFFEBEE)
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
                color = Color.Black
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                text = task.body,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Column(
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    text = getPriorityText(task.priorityDomain, includePrefix = true),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    GeneralButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        onClick = { onCompleteTask(task.id) },
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
                            .weight(1f)
                            .padding(end = 4.dp),
                        onClick = { onDeleteTask(task.id) }
                    ) {
                        Text(
                            text = "Удалить "
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
    TaskCard(
        task = task1,
        onCompleteTask = {},
        onDeleteTask = {}
    )
}