package com.example.dailytodo.ui.calendar

import android.R.color
import com.example.dailytodo.data.Priority
import com.example.dailytodo.data.TaskData
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class CalendarDecorator(private val priority: Priority) : DayViewDecorator
{
    private val dateList = mutableListOf<CalendarDay>()

    fun addDate(date: CalendarDay) {
        dateList.add(date)
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dateList.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        when (priority) {
            Priority.HIGH -> view.addSpan(DotSpan(10f, android.graphics.Color.RED))
            Priority.MEDIUM -> view.addSpan(DotSpan(10f, android.graphics.Color.YELLOW))
            Priority.LOW -> view.addSpan(DotSpan(10f, android.graphics.Color.GREEN))
        }
    }
}

