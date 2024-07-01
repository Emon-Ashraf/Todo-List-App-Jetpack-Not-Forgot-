package com.example.todolist.data

class TaskRepository {
    private val tasks = mutableListOf<Task>()

    fun getTasks(): List<Task> = tasks

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun updateTask(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
        }
    }

    fun deleteTask(task: Task) {
        tasks.remove(task)
    }
}
