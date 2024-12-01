package com.example.to_do.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "tasks") // SQLite table name
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // placeholder
    val isComplete: Boolean,
    val priority: Priority,
    val startTime: HourMinute, //   08:00
    val endTime: HourMinute, //     17:00
    val taskDueDate: Time, // 2024-11-25
    val isAlertOn: Boolean,
    val name: String,
    val description: String,
    val color: Colors
) {
    fun dateToString(): String {
        return taskDueDate.getUiDate()
    }
}

enum class Priority {
    Low,
    Medium,
    High
}

enum class Colors {
    Orange,
    Blue,
    Pink
}
// converters to transform them into types that Room supports
class Converters {
    @TypeConverter
    fun fromTime(time: Time): String = time.toString()

    @TypeConverter
    fun toTime(time: String): Time = Time.stringToTime(time)

    @TypeConverter
    fun fromHourMinute(hourMinute: HourMinute): String = hourMinute.toString()

    @TypeConverter
    fun toHourMinute(hourMinute: String): HourMinute = HourMinute.stringToHourMinute(hourMinute)

    @TypeConverter
    fun fromPriority(priority: Priority): Int = priority.ordinal

    @TypeConverter
    fun toPriority(ordinal: Int): Priority = Priority.entries[ordinal]

    @TypeConverter
    fun fromColors(color: Colors): Int = color.ordinal

    @TypeConverter
    fun toColors(ordinal: Int): Colors = Colors.entries[ordinal]
}