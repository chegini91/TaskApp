package com.example.taskapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskapp.model.Task
import com.example.taskapp.model.TaskRepository
import com.example.taskapp.viewmodel.TaskViewModel

/**
 * The View: shows state from the ViewModel and sends user events back to it.
 * No business logic here.
 */
@Composable
fun TaskScreen(
    viewModel: TaskViewModel = viewModel { TaskViewModel(TaskRepository()) },
) {
    // State flows DOWN from the ViewModel...
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    // ...the text being typed is pure UI state, so it stays here.
    var input by remember { mutableStateOf("") }

    Column(Modifier.safeDrawingPadding().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("New task") },
                modifier = Modifier.weight(1f),
            )
            // ...and events go UP to the ViewModel.
            Button(onClick = { viewModel.addTask(input); input = "" }) {
                Text("Add")
            }
        }

        LazyColumn {
            items(tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { viewModel.toggleDone(task) },
                    onDelete = { viewModel.deleteTask(task) },
                )
            }
        }
    }
}

/** One task. Knows nothing about the ViewModel — it only gets callbacks. */
@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = task.isDone, onCheckedChange = { onToggle() })
        Text(
            text = task.title,
            textDecoration = if (task.isDone) TextDecoration.LineThrough else null,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
