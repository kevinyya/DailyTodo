package com.example.dailytodo.ui.calendar

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.dailytodo.data.Priority
import com.example.dailytodo.databinding.FragmentCalendarBinding
import com.example.dailytodo.ui.task.TaskViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.currentCoroutineContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.log


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null

    private val calendarViewModel: CalendarViewModel by viewModels()

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

        // Highlight Current Date
        val calendarView = binding.calendarView
        val currentDate: Calendar = Calendar.getInstance()
        calendarView.setSelectedDate(currentDate.getTime())

        // Mark Date with Tasks
        // val cal1 = java.util.Calendar.getInstance()
        // val calendarDecorator = CalendarDecorator(Priority.HIGH)
        // cal1.set(2024, java.util.Calendar.DECEMBER, 10)
        // calendarDecorator.addDate(CalendarDay.from(cal1))
        // val cal2 = java.util.Calendar.getInstance()
        // cal2.set(2024, java.util.Calendar.DECEMBER, 20)
        // calendarDecorator.addDate(CalendarDay.from(cal2))
        // calendarView.addDecorator(calendarDecorator)

        // Show Task with Color in the Calendar
        showTaskInCalendar()

        return root
    }

    fun showTaskInCalendar() {
        val taskList = calendarViewModel.getAllTasks
        val lowPriorityDecorator = CalendarDecorator(Priority.LOW)
        val mediumPriorityDecorator = CalendarDecorator(Priority.MEDIUM)
        val highPriorityDecorator = CalendarDecorator(Priority.HIGH)
        for (task in taskList) {
            // Convert to CalendarDay
            val calendarDay = dateToCalendarDay(task.date)
            when (task.priority) {
                Priority.LOW -> {
                    // Log.d("Debug", "Low: " + formatDate.format(task.date))
                    lowPriorityDecorator.addDate(calendarDay)
                }
                Priority.MEDIUM -> {
                    // Log.d("Debug", "Medium: " + formatDate.format(task.date))
                    mediumPriorityDecorator.addDate(calendarDay)
                }
                Priority.HIGH -> {
                    // Log.d("Debug", "High: " + formatDate.format(task.date))
                    highPriorityDecorator.addDate(calendarDay)
                }
                else -> {}
            }
        }
        // Update Decorator
        binding.calendarView.addDecorator(lowPriorityDecorator)
        binding.calendarView.addDecorator(mediumPriorityDecorator)
        binding.calendarView.addDecorator(highPriorityDecorator)
    }

    fun dateToCalendarDay(dateInLong: Long) : CalendarDay {
        // Convert to CalendarDay
        val dateInString = formatDate.format(dateInLong)
        val date : Date = formatDate.parse(dateInString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return CalendarDay.from(calendar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}