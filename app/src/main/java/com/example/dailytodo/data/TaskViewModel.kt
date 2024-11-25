package com.example.dailytodo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    // Get TaskDao
    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    // Get All Tasks
    val getAllData: LiveData<List<TaskData>> = taskDao.getAllTasks()

    fun insertTask(taskData: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insertTask(taskData)
        }
    }

    fun updateTask(taskData: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.updateTask(taskData)
        }
    }

    fun deleteTask(taskData: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(taskData)
        }
    }

    fun findTask(title: String) : TaskData{
        return taskDao.findTask(title)
    }

}