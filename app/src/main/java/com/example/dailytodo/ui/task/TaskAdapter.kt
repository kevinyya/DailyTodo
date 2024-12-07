package com.example.dailytodo.ui.task

import android.content.Intent
import android.os.Bundle
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
import com.example.dailytodo.data.Priority
import com.example.dailytodo.data.TaskData
import java.text.SimpleDateFormat

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var taskList = emptyList<TaskData>()

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById<TextView>(R.id.titleTV)
        val contentTV: TextView = itemView.findViewById<TextView>(R.id.contentTV)
        val priorityCard: CardView = itemView.findViewById<CardView>(R.id.priorityCard)
        val dateTV: TextView = itemView.findViewById<TextView>(R.id.dateTV)
        val taskRowLL: LinearLayout = itemView.findViewById<LinearLayout>(R.id.taskRowLL)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.row_item_task, parent, false)
        return TaskViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Date Format
        val formatDate = SimpleDateFormat(holder.itemView.context.getString(R.string.date_format))
        // Display View
        holder.titleTV.text = taskList[position].title
        holder.contentTV.text = taskList[position].content
        holder.dateTV.text = formatDate.format(taskList[position].date)
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

        // Item Click Listener
        holder.taskRowLL.setOnClickListener {
            // Log.d("Debug", "Row Item Clicked")
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java)
            val bundle = Bundle()
            bundle.putString("Title", taskList[position].title)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }

    }

    fun setData(taskList: List<TaskData>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }
}