package com.example.laba8.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.laba8.model.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): MutableList<Task>

    @Delete
    suspend fun delete(task: Task)
}
