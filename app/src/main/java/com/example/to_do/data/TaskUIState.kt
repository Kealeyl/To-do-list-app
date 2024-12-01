package com.example.to_do.data

import com.example.to_do.model.Task
import kotlinx.coroutines.flow.StateFlow

data class TaskUIState(
    val listOfTasks: StateFlow<List<Task>>,
    val listOfTasksSearch: StateFlow<List<Task>>? = null,
    val numberOfTasks: StateFlow<Int>,
    val userSearch: String = "",
    val editTempTask: Task = defaultTask,
    val createTempTask: Task = emptyTask
)