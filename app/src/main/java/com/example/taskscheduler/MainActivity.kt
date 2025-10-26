package com.example.taskscheduler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskscheduler.ui.theme.TaskSchedulerTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.taskscheduler.ui.screens.MainScreen
import com.example.taskscheduler.ui.screens.StatisticScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskSchedulerTheme {
                TaskScheduler()
            }
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
            modifier = Modifier.padding(innerPadding)
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
                }
            )
        }

        composable("statistic") {
            StatisticScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}






