package com.example.dailytodo

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toolbar
import android.app.DatePickerDialog
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.dailytodo.data.Priority
import com.example.dailytodo.data.TaskData
import com.example.dailytodo.ui.task.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class AddActivity : AppCompatActivity() {

    // UI Elements
    private lateinit var titleET: EditText
    private lateinit var prioritySP: Spinner
    private lateinit var contentET: EditText
    private lateinit var dateET: EditText

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
        dateET = findViewById(R.id.dateET)

        // Set Content EditView Scrollable
        contentET.movementMethod = ScrollingMovementMethod.getInstance()

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
    private fun addTask() {
        if (titleET.text.toString() != "" && dateET.text.toString() != "") {
            // Get New Task
            val newTask = createNewTask()
            // Insert Task
            taskViewModel.insertTask(newTask)
            finish()
        } else {
            // Snackbar popup
            Snackbar.make(findViewById(android.R.id.content),
                R.string.add_title_hint, Snackbar.LENGTH_SHORT).show()
        }
    }


    private fun createNewTask(): TaskData {
        // Convert Date
        val formatDate = SimpleDateFormat(getString(R.string.date_format))
        val date = formatDate.parse(dateET.text.toString())

        // Create New Task
        return TaskData(title = titleET.text.toString(),
                        priority = Priority.values()[prioritySP.selectedItemId.toInt()],
                        content = contentET.text.toString(),
                        date = date.time)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.taskCheck) {
            // Add Task
            addTask()
        }

        return super.onOptionsItemSelected(item)
    }
}