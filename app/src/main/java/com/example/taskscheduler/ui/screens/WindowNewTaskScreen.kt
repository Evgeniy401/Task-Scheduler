package com.example.taskscheduler.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskscheduler.ui.components.GeneralButton
import com.example.taskscheduler.ui.theme.TaskSchedulerTheme

@Composable
fun WindowNewTaskScreen(
    saveNewTask: () -> Unit,
    onBack: () -> Unit,
    viewModel: WindowNewTaskScreenViewModel = viewModel()
) {
    val textStateLabel by viewModel.textStateLabel.collectAsState()
    val textStateDescription by viewModel.textStateDescription.collectAsState()

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
            label = {Text(text = "Содержание задачи")},
            singleLine = true,
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
                    value = textStateDescription,
            onValueChange = { newTextDescription ->
                viewModel.updateTextDescription(newTextDescription)
            },
            shape = RoundedCornerShape(10.dp),
            label = {Text(text = "Название задачи")},
            maxLines = 12,
        )

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

                }
            ) {
                Text(
                    text = "Сохранить задачу",
                    fontSize = 20.sp,
                )
            }

            GeneralButton(
                modifier = Modifier
                    .padding(top = 10.dp),
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
    TaskSchedulerTheme() {
        WindowNewTaskScreen(
            onBack = {},
            saveNewTask = {}
        )
    }
}