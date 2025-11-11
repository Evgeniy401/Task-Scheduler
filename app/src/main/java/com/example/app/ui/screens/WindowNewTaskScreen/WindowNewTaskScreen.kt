package com.example.app.ui.screens.WindowNewTaskScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app.domain.model.PriorityDomain
import com.example.app.ui.components.GeneralButton
import com.example.app.ui.theme.TaskSchedulerTheme
import com.example.app.R
import com.example.app.domain.utils.getPriorityText

@Composable
fun WindowNewTaskScreen(
    saveNewTask: (String, String, PriorityDomain) -> Unit,
    onBack: () -> Unit,
    viewModel: WindowNewTaskScreenViewModel = viewModel()
) {
    val textStateLabel by viewModel.textStateLabel.collectAsState()
    val textStateBody by viewModel.textStateDescription.collectAsState()
    var isExpanded by remember { mutableStateOf(false) }
    var selectedPriorityDomain by remember { mutableStateOf(PriorityDomain.NONE) }

    val priorities = PriorityDomain.entries.toTypedArray().filter { it != PriorityDomain.NONE }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = textStateLabel,
            onValueChange = { newTextLabel ->
                viewModel.updateTextLabel(newTextLabel)
            },
            shape = RoundedCornerShape(10.dp),
            label = { Text(text = "Название задачи") },
            singleLine = true,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = textStateBody,
            onValueChange = { newTextDescription ->
                viewModel.updateTextDescription(newTextDescription)
            },
            shape = RoundedCornerShape(10.dp),
            label = { Text(text = "Содержание задачи") },
            maxLines = 12,
        )
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .wrapContentSize(Alignment.TopCenter),
        ) {
            Button(
                onClick = {
                    isExpanded = !isExpanded
                },
                modifier = Modifier
                    .wrapContentWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Приоритет: ${getPriorityText(selectedPriorityDomain)}"
                )
                Icon(
                    imageVector =
                        if (isExpanded) Icons.Default.ArrowDropUp
                        else Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(R.string.open_menu)
                )
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.Center),
            ) {
                priorities.forEach { priority ->
                    DropdownMenuItem(
                        onClick = {
                            selectedPriorityDomain = priority
                            isExpanded = false
                        },
                        text = {
                            Text(
                                text = getPriorityText(priority),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            GeneralButton(
                onClick = {
                    if (selectedPriorityDomain != PriorityDomain.NONE && textStateLabel.isNotBlank()) {
                        saveNewTask(textStateLabel, textStateBody, selectedPriorityDomain)
                        onBack()
                    } // добавить проверку с Snackbar
                }
            ) {
                Text(
                    text = "Сохранить задачу",
                    fontSize = 20.sp,
                )
            }
            GeneralButton(
                modifier = Modifier
                    .padding(top = 5.dp),
                onClick = {
                    onBack()
                }
            ) {
                Text(
                    text = "Назад",
                    fontSize = 20.sp,
                )
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
            saveNewTask = { _, _, _ -> }
        )
    }
}