package com.example.dailytodo

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.dailytodo.data.Priority
import com.example.dailytodo.data.TaskData
import com.example.dailytodo.data.TaskDatabase
import com.example.dailytodo.ui.task.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class UpdateActivity : AppCompatActivity() {

    // UI Elements
    private lateinit var titleET: EditText
    private lateinit var prioritySP: Spinner
    private lateinit var contentET: EditText
    private lateinit var dateET: EditText

    // Task ViewModel
    private val taskViewModel: TaskViewModel by viewModels()

    // Current Task
    private lateinit var currentTask: TaskData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // Edit Toolbar
        val updateTB = findViewById<androidx.appcompat.widget.Toolbar>(R.id.updateTB)
        setSupportActionBar(updateTB)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set Back Arrow Color
        updateTB.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))

        // Get View Item
        titleET = findViewById(R.id.titleET)
        prioritySP = findViewById(R.id.prioritySP)
        contentET = findViewById(R.id.contentET)
        dateET = findViewById(R.id.dateET)

        // Display Current Task
        displayTask()

        // Show DatePickerDialog
        dateET.setOnClickListener{
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        // Get Date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create Datepicker Dialog
        val datePickerDialog =
            DatePickerDialog(this, { view, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                // Get Date
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val formatDate = SimpleDateFormat(getString(R.string.date_format))
                dateET.setText(formatDate.format(calendar.getTime()))
            }, year, month, day).show()
    }

    private fun displayTask() {
        // Get Title from Intent
        val intent = getIntent()
        val title = intent.getStringExtra("Title").toString()

        // Get Task by Title
        currentTask = taskViewModel.findTask(title)

        // Log.d("Debug", "Title: " + getTask.title)
        // Log.d("Debug", "Priority: " + getTask.priority.value.toString())
        // Log.d("Debug", "Content: " + getTask.content)
        // Log.d("Debug", "Date: " + Date(getTask.date).toString())

        // Display UI Elements
        titleET.setText(currentTask.title)
        prioritySP.setSelection(currentTask.priority.value)
        contentET.setText(currentTask.content)
        val formatDate = SimpleDateFormat(getString(R.string.date_format))
        dateET.setText(formatDate.format(Date(currentTask.date)))
    }

    private fun updateTask() {
        if (titleET.text.toString() != "" && dateET.text.toString() != "") {
            // Update Current Task
            currentTask.title = titleET.text.toString()
            currentTask.priority = Priority.values()[(prioritySP.selectedItemId.toInt())]
            currentTask.content = contentET.text.toString()
            val formatDate = SimpleDateFormat(getString(R.string.date_format))
            currentTask.date = formatDate.parse(dateET.text.toString()).time

            // Update Task
            taskViewModel.updateTask(currentTask)
            finish()
        } else {
            // Snackbar popup
            Snackbar.make(findViewById(android.R.id.content),
                R.string.add_title_hint, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.taskCheck) {
            // Update Task
            updateTask()
        }
        return super.onOptionsItemSelected(item)
    }

}