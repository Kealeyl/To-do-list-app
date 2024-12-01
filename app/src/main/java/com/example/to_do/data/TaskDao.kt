package com.example.to_do.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.to_do.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // only insert the entity from one place, not expecting conflicts
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task) // entity that's updated has the same primary key as the entity that's passed in

    @Delete
    suspend fun deleteTask(task: Task) // don't have the entity, you might have to fetch it before calling the delete()

    @Query("SELECT * from tasks WHERE id = :id")
    // Flow return type, Room will run it on background thread
    // Room keeps the Flow updated; only need to explicitly get the data once
    fun getTask(id: Int): Flow<Task>

    // tasks in ascending order
    @Query("SELECT * from tasks ORDER BY name ASC")
    fun getAllTasksAsc(): Flow<List<Task>>

    @Query("SELECT * from tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT COUNT(id) FROM tasks")
    fun getNumberOfTasks(): Flow<Int>

    @Query("SELECT * FROM tasks WHERE name LIKE '%' || :search || '%'")
    fun getTasksBySearch(search: String): Flow<List<Task>>
}