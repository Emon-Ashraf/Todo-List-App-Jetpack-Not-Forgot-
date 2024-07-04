package com.example.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.Task
import com.example.todolist.data.TaskRepository

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        loadTasks()
    }

    private fun loadTasks() {
        _tasks.value = repository.getTasks()
    }

    fun addTask(task: Task) {
        repository.addTask(task)
        loadTasks()
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
        loadTasks()
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task)
        loadTasks()
    }

    fun getTaskById(taskId: String): Task? {
        return _tasks.value?.find { it.id == taskId }
    }
}
