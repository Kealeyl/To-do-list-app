package com.example.to_do.data

import com.example.to_do.model.Colors
import com.example.to_do.model.Month
import com.example.to_do.model.Priority
import com.example.to_do.model.Task
import com.example.to_do.model.Time

data class TaskUIState (
    val listOfTasks: List<Task> = emptyList(),
    val userSearch: String = "",
    val tempTask: Task = defaultTask,
    val originalTask: Task = defaultTask // used for editing tasks
)