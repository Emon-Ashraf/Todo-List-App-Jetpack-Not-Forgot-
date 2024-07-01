package com.example.todolist.data

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val deadline: String?,
    val priority: Priority?,
    var isCompleted: Boolean = false
)
