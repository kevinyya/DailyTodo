package com.example.dailytodo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

class DateConverter {
    // 将 Date 类型转换为 Long 类型（时间戳），以便存储到数据库中
    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }

    // 将从数据库中取出的 Long 类型（时间戳）转换回 Date 类型
    @TypeConverter
    fun longToDate(long: Long): Date {
        return Date(long)
    }
}

@Entity(tableName = "tasks")
data class TaskData (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "priority")
    val priority: Priority,
    @ColumnInfo(name = "date")
    val date : Long
)


