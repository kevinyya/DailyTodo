package com.example.dailytodo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTask(task: TaskData)

    @Update
    fun updateTask(task: TaskData)

    @Delete
    fun deleteTask(task: TaskData)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getAllTasks() : LiveData<List<TaskData>>

    @Query("SELECT * FROM tasks WHERE title = :title")
    fun findTask(title: String): TaskData

    // suspend fun sortByDate()
}