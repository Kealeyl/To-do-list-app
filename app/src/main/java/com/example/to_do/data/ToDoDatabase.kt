package com.example.to_do.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.to_do.model.Converters
import com.example.to_do.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {

    // Room to inject the generated TaskDao implementation
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile // never cached
        private var Instance: ToDoDatabase? = null

        //Room.databaseBuilder() to get an instance of the database
        fun getDatabase(context: Context): ToDoDatabase {
            return Instance ?: synchronized(this) { // prevent race condition so database only gets initialized once
                Room.databaseBuilder(context, ToDoDatabase::class.java, "task_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}