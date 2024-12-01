package com.example.to_do.data

import com.example.to_do.model.Task
import kotlinx.coroutines.flow.Flow

class OfflineDatabaseRepository (private val taskDao: TaskDao) : TasksRepository {

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    override fun getAllTasksAscStream(): Flow<List<Task>> {
        return taskDao.getAllTasksAsc()
    }

    override fun getTaskStream(id: Int): Flow<Task> {
        return taskDao.getTask(id)
    }

    override fun getAllTasksStream(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override fun getNumberOfTasks(): Flow<Int> {
        return taskDao.getNumberOfTasks()
    }

    override fun getTasksBySearch(search: String): Flow<List<Task>> {
        return taskDao.getTasksBySearch(search)
    }
}