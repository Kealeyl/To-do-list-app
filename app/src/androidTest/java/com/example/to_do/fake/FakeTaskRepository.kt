package com.example.to_do.fake

import com.example.to_do.data.TasksRepository
import com.example.to_do.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskRepository : TasksRepository {
    private var listOfTasks: List<Task> = listOf()

    override suspend fun insertTask(task: Task) {
        listOfTasks = listOfTasks + task
    }

    override suspend fun updateTask(task: Task) {

        listOfTasks = listOfTasks.map {
            if (it.id == task.id) {
                task
            } else {
                it
            }
        }
    }

    override suspend fun deleteTask(task: Task) {
        listOfTasks = listOfTasks.filter { it.id != task.id }
    }

    override fun getTaskStream(id: Int): Flow<Task> {
        val flow: Flow<Task> = flow { emit(listOfTasks.find { it.id == id }!!) }
        return flow
    }

    override fun getAllTasksAscStream(): Flow<List<Task>> {
        val flow: Flow<List<Task>> = flow { emit(listOfTasks.sortedBy { it.name }) }
        return flow
    }

    override fun getAllTasksStream(): Flow<List<Task>> {
        val flow: Flow<List<Task>> = flow { emit(listOfTasks) }
        return flow
    }

    override fun getNumberOfTasks(): Flow<Int> {
        val flow: Flow<Int> = flow { emit(listOfTasks.size) }
        return flow
    }

    override fun getTasksBySearch(search: String): Flow<List<Task>> {
        val flow: Flow<List<Task>> =
            flow { emit(listOfTasks.filter { it.name.contains(search, ignoreCase = true) }) }
        return flow
    }
}