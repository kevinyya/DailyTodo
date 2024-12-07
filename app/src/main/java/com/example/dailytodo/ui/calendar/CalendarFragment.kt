package com.example.dailytodo.ui.calendar

import android.R
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dailytodo.data.Priority
import com.example.dailytodo.data.TaskDao
import com.example.dailytodo.data.TaskDatabase
import com.example.dailytodo.databinding.FragmentCalendarBinding
import com.example.dailytodo.ui.task.TaskAdapter
import com.example.dailytodo.ui.task.TaskViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.coroutines.currentCoroutineContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.log


class CalendarFragment : Fragment() {
    // Calendar Adapter
    private val calendarAdapter: CalendarAdapter by lazy { CalendarAdapter() }
    // Calendar ViewModel
    private val calendarViewModel: CalendarViewModel by viewModels()
    // MaterialCalendarView
    private lateinit var materialCalendarView: MaterialCalendarView
    // TaskDao
    private lateinit var taskDao: TaskDao

    private var _binding: FragmentCalendarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Date Format
    private val formatDate = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Database Dao
        taskDao = TaskDatabase.getInstance(requireContext()).taskDao()

        // Highlight Current Date
        materialCalendarView = binding.materialCalendarView
        val currentDate: Calendar = Calendar.getInstance()
        materialCalendarView.setSelectedDate(currentDate.getTime())

        // Configure RecyclerView
        val calendarRV = binding.calendarRV
        calendarRV.adapter = calendarAdapter
        calendarRV.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        // Date Clicked Event
        materialCalendarView.setOnDateChangedListener(object : OnDateSelectedListener {
            override fun onDateSelected(
                widget: MaterialCalendarView,
                calendarDay: CalendarDay,
                selected: Boolean
            ) {
                val dateInLong = calendarDayToDate(calendarDay).time
                val taskList = taskDao.getTasksByDate(dateInLong / 10000 * 10000)
                calendarAdapter.setData(taskList)
            }
        })

        return root
    }

    override fun onResume() {
        super.onResume()

        // Display Calendar with Color Dot
        showTaskInCalendar()

        // Display Today Tasks
        val dateInLong = calendarDayToDate(materialCalendarView.selectedDate).time
        val taskList = taskDao.getTasksByDate(dateInLong / 10000 * 10000)
        calendarAdapter.setData(taskList)
    }

    override fun onPause() {
        super.onPause()
    }

    private fun showTaskInCalendar() {
        val taskList = taskDao.getAllTasks()
        val lowPriorityDecorator = CalendarDecorator(Priority.LOW)
        val mediumPriorityDecorator = CalendarDecorator(Priority.MEDIUM)
        val highPriorityDecorator = CalendarDecorator(Priority.HIGH)
        for (task in taskList) {
            // Convert to CalendarDay
            val calendarDay = dateToCalendarDay(task.date)
            // Log.d("Debug", task.title + ": "+ task.date.toString())
            when (task.priority) {
                Priority.LOW -> lowPriorityDecorator.addDate(calendarDay)
                Priority.MEDIUM -> mediumPriorityDecorator.addDate(calendarDay)
                Priority.HIGH -> highPriorityDecorator.addDate(calendarDay)
                else -> {}
            }
        }
        // Update Decorator
        materialCalendarView.addDecorator(lowPriorityDecorator)
        materialCalendarView.addDecorator(mediumPriorityDecorator)
        materialCalendarView.addDecorator(highPriorityDecorator)
    }

    private fun dateToCalendarDay(dateInLong: Long) : CalendarDay {
        // Convert to CalendarDay
        val dateInString = formatDate.format(dateInLong)
        val date : Date = formatDate.parse(dateInString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return CalendarDay.from(calendar)
    }

    private fun calendarDayToDate(calendarDay: CalendarDay) : Date {
        val calendar = Calendar.getInstance()
        calendar.set(calendarDay.year, calendarDay.month, calendarDay.day, 0, 0, 0)
        return calendar.time
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}