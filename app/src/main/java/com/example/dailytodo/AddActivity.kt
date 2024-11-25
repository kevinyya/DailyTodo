package com.example.dailytodo

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.dailytodo.data.Priority
import com.example.dailytodo.data.TaskData
import com.example.dailytodo.ui.task.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import java.util.Date


class AddActivity : AppCompatActivity() {

    // UI Elements
    private lateinit var titleET: EditText
    private lateinit var prioritySP: Spinner
    private lateinit var contentET: EditText
    private lateinit var dateDP: DatePicker

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // Edit Toolbar
        val addTB = findViewById<androidx.appcompat.widget.Toolbar>(R.id.addTB)
        setSupportActionBar(addTB)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set Back Arrow Color
        addTB.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))

        // Get View Item
        titleET = findViewById(R.id.titleET)
        prioritySP = findViewById(R.id.prioritySP)
        contentET = findViewById(R.id.contentET)
        dateDP = findViewById(R.id.dateDP)

        // Set Content EditView Scrollable
        contentET.movementMethod = ScrollingMovementMethod.getInstance()


    }


    private fun addTask() {
        if (titleET.text.toString() != "") {
            val newTask = createNewTask()

//            Log.d("Debug", "Title: " + newTask.title)
//            Log.d("Debug", "Priority: " + newTask.priority.toString())
//            Log.d("Debug", "Content: " + newTask.content)
//            Log.d("Debug", "Date: " + newTask.date.toString())
//            Log.d("Debug", "Date: " + Date(newTask.date).toString())

        } else {
            // Snackbar popup
            Snackbar.make(findViewById(android.R.id.content),
                R.string.add_title_hint, Snackbar.LENGTH_SHORT).show()
        }

    }


    private fun createNewTask(): TaskData {
        // Convert Date
        val calendar = Calendar.getInstance()
        calendar.set(dateDP.year, dateDP.month, dateDP.dayOfMonth)

        // Create New Task
        val newTask = TaskData(title = titleET.text.toString(),
                               priority = Priority.values()[prioritySP.selectedItemId.toInt()],
                               content = contentET.text.toString(),
                               date = calendar.getTime().time)

        return newTask
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.taskCheck) {
            Log.d("Debug", "Check Triggered")
            addTask()
        }

        return super.onOptionsItemSelected(item)
    }
}