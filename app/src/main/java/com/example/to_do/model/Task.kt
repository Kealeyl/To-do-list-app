package com.example.to_do.model

import java.util.Date

data class Task (
    val isComplete: Boolean,
    val priority: Priority,
    val startTime: Time,
    val endTime: Time,
    val month: Month,
    val dateNumber: Int,
    val isAlertOn: Boolean,
    val name: String,
    val description: String,
    val color: Colors
) {
    fun dateToString(): String {
        return "$dateNumber $month"
    }
}

enum class Priority{
    High,
    Medium,
    Low
}

enum class Colors{
    Orange,
    Blue,
    Pink
}

enum class Month{
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December
}

class Time(val hour: Int, val minute: Int, val isAM: Boolean) {
}