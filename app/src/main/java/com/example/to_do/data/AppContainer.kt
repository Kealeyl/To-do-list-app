package com.example.to_do.data

import android.content.Context

// App container for Dependency injection
interface AppContainer {
    val taskRepository: TasksRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    //Implementation for TaskRepository
    override val taskRepository: TasksRepository by lazy {
        OfflineDatabaseRepository(ToDoDatabase.getDatabase(context).taskDao()) // instantiate DB, pass DAO instance
    }
}