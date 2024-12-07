package com.example.dailytodo.ui.calendar

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dailytodo.data.TaskData
import com.example.dailytodo.data.TaskDatabase
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    // Get TaskDao
    // private val taskDao = TaskDatabase.getInstance(application).taskDao()
    // Get All Tasks
    // val getAllTasks: List<TaskData> = taskDao.getAllTasks()
}