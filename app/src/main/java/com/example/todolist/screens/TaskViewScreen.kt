package com.example.todolist.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todolist.R
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
                    .padding(bottom = 32.dp),
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
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = stringResource(R.string.edit_task))
                    }
                    Text(
                        text = if (task.isCompleted) stringResource(R.string.completed_status) else stringResource(R.string.incomplete_status),
                        color = if (task.isCompleted) Color(0xFF69B76C) else Color.Red,
                        fontSize = 16.sp
                    )
                }
            }

            Text(
                text = task.description,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.clock_icon),
                    contentDescription = "Deadline",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Before ${task.deadline}",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(78.dp))

                Tag()

            }
        }
    }
}

@Composable
fun Tag(/*priority: Priority*/) {
    //val color = getColorBasedOnPriority(priority)
    val color = Color.Red

    Box(
        modifier = Modifier
            .background(color, shape = CutCornerShape(topStart = 14.dp, bottomStart = 14.dp,))
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .padding(start = 8.dp)
    ) {
        Text(
            //text = priority.displayName,
            text = "Quickly",
            color = Color.White,
            fontSize = 16.sp
        )
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
