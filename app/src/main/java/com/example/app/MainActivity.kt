package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.app.ui.theme.TaskSchedulerTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.app.ui.screens.mainScreen.MainScreen
import com.example.app.ui.screens.statisticScreen.StatisticScreen
import com.example.app.ui.screens.windowNewTaskScreen.WindowNewTaskScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.domain.usecase.SyncTasksUseCase
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(
    private val syncTasksUseCase: SyncTasksUseCase
) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskSchedulerTheme {
                TaskScheduler()
            }
        }

        lifecycleScope.launch {
            syncTasksUseCase()
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            syncTasksUseCase()
        }
    }
}

@Composable
fun TaskScheduler() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier
    ) {

        composable("main") {
            MainScreen(
                onNavigateToStatistic = {
                    navController.navigate("statistic")
                },

                onNavigateToWindowNewTask = {
                    navController.navigate("newTask")
                },
            )
        }

        composable("statistic") {
            StatisticScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("newTask") {
            WindowNewTaskScreen(
               onBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}