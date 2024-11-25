package com.example.dailytodo

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class AddActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

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
        }

        return super.onOptionsItemSelected(item)
    }
}