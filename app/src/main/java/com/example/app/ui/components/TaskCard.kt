package com.example.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.app.mapping.TaskDomainUiMapper
import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task

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
    onDeleteTask: (Int) -> Unit,
    taskMapper: TaskDomainUiMapper,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = taskMapper.getTaskCardColor(task.priorityDomain)
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        onClick = { onCompleteTask(task.id) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Выполнить задачу",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Green
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )

                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        onClick = { onDeleteTask(task.id) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Удалить задачу",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Red
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
        onDeleteTask = {},
        taskMapper = TaskDomainUiMapper(),
    )
}