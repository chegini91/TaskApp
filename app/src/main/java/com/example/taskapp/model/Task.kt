package com.example.taskapp.model

/**
 * Represents a single task in the app.
 *
 * Pure Kotlin data class — no Android imports, no UI references.
 * data class gives us equals(), copy(), and toString() for free.
 */
data class Task(
    val id: Int,
    val title: String,
    val isDone: Boolean = false,
)
