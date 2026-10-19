package com.example.taskapp.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * The single source of truth for tasks (in memory for now; Room later).
 * Every change creates a NEW list — that is what makes the UI update.
 */
class TaskRepository {

    private val _tasks = MutableStateFlow(
        listOf(
            Task(1, "Buy groceries"),
            Task(2, "Walk the dog"),
            Task(3, "Read for 30 minutes"),
        )
    )

    val tasks = _tasks.asStateFlow()   // read-only for everyone else

    fun add(title: String) {
        val newId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1
        _tasks.value += Task(newId, title)
    }

    fun delete(task: Task) {
        _tasks.value = _tasks.value.filter { it.id != task.id }
    }

    fun toggleDone(task: Task) {
        _tasks.value = _tasks.value.map {
            if (it.id == task.id) it.copy(isDone = !it.isDone) else it
        }
    }
}
