package com.example.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.app.ui.theme.DeleteColor
import com.example.app.ui.theme.EditColor
import com.example.app.ui.theme.ExecuteColor
import com.example.app.ui.theme.TextButton
import com.example.domain.model.PriorityDomain
import com.example.domain.model.Task

val task1 = Task(
    id = 1,
    title = "Помыть машину",
    body = "Это очень длинное описание задачи, которое занимает несколько строк и должно автоматически увеличивать высоту карточки в зависимости от объема текста",
    priorityDomain = PriorityDomain.STANDARD,
    isCompleted = false,
    isDeleted = false,
    lastModified = System.currentTimeMillis()
)

@Composable
fun TaskCard(
    task: Task,
    onCompleteTask: (Int) -> Unit,
    onDeleteTask: (Int) -> Unit,
    onEditTask: (Int) -> Unit,
) {
    if (task.isDeleted) {
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = task.body,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { onCompleteTask(task.id) },
                    enabled = !task.isCompleted,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = ExecuteColor,
                        disabledContentColor = TextButton
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Выполнить задачу",
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { onEditTask(task.id) },
                    enabled = !task.isCompleted,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = EditColor,
                        disabledContentColor = TextButton
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Редактировать задачу",
                        modifier = Modifier.size(35.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { onDeleteTask(task.id) },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = DeleteColor,
                        disabledContentColor = TextButton
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Удалить задачу",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}