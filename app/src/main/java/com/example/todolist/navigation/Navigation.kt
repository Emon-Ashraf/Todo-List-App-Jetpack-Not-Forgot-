package com.example.todolist.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.screens.*
import com.example.todolist.viewmodel.TaskViewModel

@Composable
fun NavigationApp(taskViewModel: TaskViewModel) {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = "task_list") {
                composable("task_list") { TaskListScreen(navController, taskViewModel) }
                composable("task_input") { TaskInputScreen(navController, taskViewModel) }
                composable("task_view/{taskId}", arguments = listOf(navArgument("taskId") { type = NavType.StringType })) { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getString("taskId")
                    val task = taskViewModel.tasks.value?.find { it.id == taskId }
                    task?.let {
                        TaskViewScreen(task = it, navController = navController)
                    }
                }
                composable("task_edit/{taskId}", arguments = listOf(navArgument("taskId") { type = NavType.StringType })) { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getString("taskId")
                    taskId?.let {
                        TaskEditScreen(navController = navController, taskViewModel = taskViewModel, taskId = it)
                    }
                }
            }
        }
    }
}
