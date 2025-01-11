package com.example.to_do

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.to_do.data.TaskDao
import com.example.to_do.data.ToDoDatabase
import com.example.to_do.data.createDefaultTask
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {
    private lateinit var taskDao: TaskDao
    private lateinit var toDoDatabase: ToDoDatabase

    private var taskTest1 = createDefaultTask("taskTest1", "taskTest1 description")

    // in-memory database
    // DAO queries in the main thread with .allowMainThreadQueries() for testing
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        toDoDatabase = Room.inMemoryDatabaseBuilder(context, ToDoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = toDoDatabase.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        toDoDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertTaskIntoDB() = runBlocking {
        taskTest1 = taskTest1.copy(id=1)

        taskDao.insertTask(taskTest1)
        val allTasks = taskDao.getAllTasks().first()
        assertEquals(allTasks[0], taskTest1)
    }
}