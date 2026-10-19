package com.example.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.taskapp.ui.TaskScreen

/**
 * Entry point — as thin as possible.
 *
 * MainActivity only sets the Compose content tree.
 * All state, logic, and data live in ViewModel and Repository.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                TaskScreen()
            }
        }
    }
}
