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

    @Query("SELECT * FROM tasks ORDER BY date ASC")
    fun getAllTasks() : List<TaskData>

    @Query("SELECT * FROM tasks WHERE title = :title")
    fun getTask(title: String): TaskData

    @Query("SELECT * FROM tasks ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByPriority(): LiveData<List<TaskData>>

    @Query("SELECT * FROM tasks ORDER BY date ASC")
    fun sortByDate(): LiveData<List<TaskData>>

    @Query("SELECT * FROM tasks WHERE date = :date ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun getTasksByDate(date: Long) : List<TaskData>
}