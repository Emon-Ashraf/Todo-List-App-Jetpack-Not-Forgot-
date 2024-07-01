package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.navigation.NavigationApp
import com.example.todolist.ui.theme.TodoListTheme
import com.example.todolist.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                val taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
                NavigationApp(taskViewModel = taskViewModel)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    TodoListTheme {
        // Preview of the NavigationApp with a dummy ViewModel
        val taskViewModel = TaskViewModel()
        NavigationApp(taskViewModel = taskViewModel)
    }
}
