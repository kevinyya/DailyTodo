package com.example.dailytodo.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytodo.R
import com.example.dailytodo.data.Priority
import com.example.dailytodo.data.TaskData

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var taskList = emptyList<TaskData>()

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV = itemView.findViewById<TextView>(R.id.titleTV)
        val contentTV = itemView.findViewById<TextView>(R.id.contentTV)
        val priorityCard = itemView.findViewById<CardView>(R.id.priorityCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.task_row_view, parent, false)
        return TaskViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.titleTV.text = taskList[position].title
        holder.contentTV.text = taskList[position].content
        val priority = taskList[position].priority
        when (priority) {
            Priority.LOW -> holder.priorityCard.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.red)
            )
            Priority.MEDIUM -> holder.priorityCard.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.yellow)
            )
            Priority.HIGH -> holder.priorityCard.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.green)
            )

        }
    }
}