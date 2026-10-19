package com.example.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.taskapp.model.Task
import com.example.taskapp.model.TaskRepository

/**
 * Sits between the UI and the Repository.
 * The UI reads `tasks` and calls the functions below — nothing else.
 */
class TaskViewModel(
    private val repository: TaskRepository,
) : ViewModel() {

    val tasks = repository.tasks

    fun addTask(title: String) {
        if (title.isNotBlank()) repository.add(title.trim())
    }

    fun deleteTask(task: Task) = repository.delete(task)

    fun toggleDone(task: Task) = repository.toggleDone(task)
}
