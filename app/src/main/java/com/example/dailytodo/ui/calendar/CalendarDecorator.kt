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

    // init {
    //     // 添加要标记的日期，这里以2024年12月10日和20日为例
    //     val cal1 = java.util.Calendar.getInstance()
    //     cal1.set(2024, java.util.Calendar.DECEMBER, 10)
    //     dateList.add(CalendarDay.from(cal1))
    //     val cal2 = java.util.Calendar.getInstance()
    //     cal2.set(2024, java.util.Calendar.DECEMBER, 20)
    //     dateList.add(CalendarDay.from(cal2))
    // }

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

