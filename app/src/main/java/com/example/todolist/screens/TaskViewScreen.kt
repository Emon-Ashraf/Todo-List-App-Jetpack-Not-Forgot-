package com.example.todolist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todolist.data.Priority
import com.example.todolist.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskViewScreen(task: Task, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(onClick = { navController.navigate("task_edit/${task.id}") }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Task")
                    }
                    Text(
                        text = if (task.isCompleted) "Completed" else "Incomplete",
                        color = if (task.isCompleted) Color.Green else Color.Red,
                        fontSize = 16.sp
                    )
                }
            }

            Text(
                text = task.description,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Deadline",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Before ${task.deadline}",
                    fontSize = 16.sp
                )
            }
            if (task.priority != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val priorityColor = getColorBasedOnPriority(task.priority)
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(priorityColor, shape = RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = task.priority.displayName,
                        color = priorityColor,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .background(priorityColor.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp))
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

fun getColorBasedOnPriority(priority: Priority): Color {
    return when (priority) {
        Priority.IMPORTANT -> Color.Red
        Priority.NORMAL -> Color.Green
        Priority.NOT_IMPORTANT -> Color.Yellow
        Priority.SOME_DAY -> Color.Blue
    }
}

@Preview(showBackground = true)
@Composable
fun TaskViewScreenPreview() {
    val navController = rememberNavController()
    val task = Task(
        id = "1",
        title = "Buy a book",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna wirl",
        deadline = "21.09.2020",
        priority = Priority.IMPORTANT,
        isCompleted = true
    )
    TaskViewScreen(task = task, navController = navController)
}
