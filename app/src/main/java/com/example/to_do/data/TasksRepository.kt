package com.example.to_do.data

import com.example.to_do.model.Task
import kotlinx.coroutines.flow.Flow


interface TasksRepository {
    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task) // entity that's updated has the same primary key as the entity that's passed in

    suspend fun deleteTask(task: Task) // don't have the entity, you might have to fetch it before calling the delete()

    fun getTaskStream(id: Int): Flow<Task>

    fun getAllTasksAscStream(): Flow<List<Task>>

    fun getAllTasksStream(): Flow<List<Task>>

    fun getNumberOfTasks(): Flow<Int>

    fun getTasksBySearch(search: String): Flow<List<Task>>
}