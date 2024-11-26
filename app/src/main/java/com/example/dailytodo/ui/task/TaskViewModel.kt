package com.example.dailytodo.ui.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytodo.data.TaskData
import com.example.dailytodo.data.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    // Get TaskDao
    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    // Get All Tasks
    val getAllTasks: LiveData<List<TaskData>> = taskDao.getAllTasks()

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

    fun findTask(title: String) : TaskData {
        return taskDao.getTask(title)
    }

}