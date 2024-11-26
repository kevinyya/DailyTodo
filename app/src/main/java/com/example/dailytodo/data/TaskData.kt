package com.example.dailytodo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

enum class Priority (val value: Int) {
    HIGH(0),
    MEDIUM(1),
    LOW(2)
}

@Entity(tableName = "tasks")
data class TaskData (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "priority")
    var priority: Priority,
    @ColumnInfo(name = "date")
    var date : Long
)


