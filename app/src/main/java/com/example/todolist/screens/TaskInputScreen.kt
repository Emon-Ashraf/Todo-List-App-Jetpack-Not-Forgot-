package com.example.todolist.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.R
import com.example.todolist.data.Priority
import com.example.todolist.data.Task
import com.example.todolist.viewmodel.TaskViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputScreen(navController: NavController, taskViewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf<Priority?>(null) }
    var descriptionLength by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            deadline = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_task_inputscreen)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(
                            R.string.back
                        )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween // Ensure elements are spaced appropriately
        ) {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.title_lebel)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedTextColor = Color(0xff888888),
                        focusedContainerColor = Color.Transparent,
                        focusedTextColor = Color(0xff222222),
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descriptionLength = it.length
                        showError = descriptionLength > 120
                    },
                    label = { Text(stringResource(R.string.description_lebel)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4,
                    isError = showError,
                    supportingText = {
                        if (showError) {
                            Text(text = "Exceeded limit by ${descriptionLength - 120} characters")
                        } else {
                            Text(text = "$descriptionLength/120")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = deadline,
                        onValueChange = { deadline = it },
                        label = { Text("Deadline") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedTextColor = Color(0xff222222),
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color(0xff222222),
                        ),
                        readOnly = true
                    )
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = stringResource(
                            R.string.select_date_lebel
                        )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = priority?.displayName ?: "Priority",
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .height(60.dp)
                                .clickable { expanded = true },
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFEFEFEF),
                                unfocusedTextColor = Color(0xFF000000),
                                focusedContainerColor = Color(0xFFE5E5DD),
                                focusedTextColor = Color(0xFF333333),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            Priority.entries.forEach { label ->
                                DropdownMenuItem(
                                    text = { Text(text = label.displayName) },
                                    onClick = {
                                        priority = label
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (title.isBlank() || description.isBlank() || deadline.isBlank()) {
                        // Show toast indicating missing fields
                        Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                    } else {
                        val newTask = Task(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            description = description,
                            deadline = deadline,
                            priority = priority ?: Priority.NORMAL
                        )
                        taskViewModel.addTask(newTask)
                        navController.popBackStack()
                        // Show success toast
                        Toast.makeText(context, "Task added successfully!", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.save_button_text),
                    modifier = Modifier,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

        }
    }
}
