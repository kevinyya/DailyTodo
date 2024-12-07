package com.example.dailytodo.ui.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytodo.R
import com.example.dailytodo.UpdateActivity
import com.example.dailytodo.data.TaskData
import com.example.dailytodo.data.Priority
import java.text.SimpleDateFormat

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    var taskList = emptyList<TaskData>()

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById<TextView>(R.id.titleTV)
        val contentTV: TextView = itemView.findViewById<TextView>(R.id.contentTV)
        val priorityCard: CardView = itemView.findViewById<CardView>(R.id.priorityCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.row_item_calendar, parent, false)
        return CalendarViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        // Display View
        holder.titleTV.text = taskList[position].title
        holder.contentTV.text = taskList[position].content
        when (taskList[position].priority) {
            Priority.LOW -> holder.priorityCard.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            )
            Priority.MEDIUM -> holder.priorityCard.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.yellow)
            )
            Priority.HIGH -> holder.priorityCard.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.red)
            )
        }
    }

    fun setData(taskList: List<TaskData>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }
}