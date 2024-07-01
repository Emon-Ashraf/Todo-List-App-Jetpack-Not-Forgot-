package com.example.todolist.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.R
import com.example.todolist.data.Priority
import com.example.todolist.data.Task
import com.example.todolist.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController, taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.tasks.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.app_tittle_top),
                            modifier = Modifier
                                .padding(16.dp),
                            fontSize = 22.sp
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("task_input") },
                containerColor = Color.Black,
                contentColor = Color.White,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        if (tasks.isEmpty()) {
            EmptyTaskList(modifier = Modifier.padding(innerPadding))
        } else {
            TaskList(navController, tasks, taskViewModel, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun EmptyTaskList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.empty_task_image),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "For now, you have nothing to do.", fontSize = 18.sp)
        Text(text = "Have a nice rest!", fontSize = 16.sp)
    }
}

@Composable
fun TaskList(navController: NavController, tasks: List<Task>, taskViewModel: TaskViewModel, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
        items(tasks) { task ->
            TaskRow(task, navController, taskViewModel)
        }
    }
}

@Composable
fun TaskRow(task: Task, navController: NavController, taskViewModel: TaskViewModel) {
    val color = getColorBasedOnPriority(task.priority)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { navController.navigate("task_view/${task.id}") },
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = task.title,
                    color = Color.White
                )
                Text(
                    text = task.description,
                    color = Color.White
                )
            }
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = {
                    task.isCompleted = it
                    taskViewModel.updateTask(task)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.White,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.Black
                )
            )
        }
    }
}

fun getColorBasedOnPriority(priority: Priority?): Color {
    return when (priority) {
        Priority.IMPORTANT -> Color.Red
        Priority.NORMAL -> Color.Green
        Priority.NOT_IMPORTANT -> Color.Yellow
        Priority.SOME_DAY -> Color.Blue
        else -> Color.Gray
    }
}
