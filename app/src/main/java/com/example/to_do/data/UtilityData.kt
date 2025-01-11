package com.example.to_do.data

import com.example.to_do.model.Colors
import com.example.to_do.model.HourMinute
import com.example.to_do.model.Month
import com.example.to_do.model.Priority
import com.example.to_do.model.Task
import com.example.to_do.model.Time
import com.example.to_do.model.WeekData

val weekDayArray: Array<String> = arrayOf(
    "Sunday", "Monday", "Tuesday", "Wednesday",
    "Thursday", "Friday", "Saturday"
)

val priorities = Priority.entries.toTypedArray()
val colors = Colors.entries.toTypedArray()
val months = Month.entries.toTypedArray()

val emptyTask = createDefaultTask("", "")
val defaultTask = createDefaultTask("Default", "Default")

fun createDefaultTask(taskName: String, taskDesc: String): Task {
    return Task(
        isComplete = false,
        priority = Priority.Low,
        color = Colors.Blue,
        startTime = HourMinute(),
        endTime = HourMinute(),
        isAlertOn = false,
        name = taskName,
        description = taskDesc,
        taskDueDate = Time()
    )
}

// Need to account for starting new month
// Returns array of pairs of week day and day
fun arrayOfWeekDays(startDate: Int): Array<Pair<String, Int>> {
    return Array(7) {
        weekDayArray[it].substring(0, 3) to (startDate + it)
    }
}

val weeks = listOf(
    WeekData(27, Month.July, arrayOfWeekDays(27)),
    WeekData(3, Month.October, arrayOfWeekDays(3)),
    WeekData(10, Month.June, arrayOfWeekDays(10))
)

fun createNumAmountOfTasks(num: Int): List<Task> {
    return List(num) {
        Task(
            id = it,
            isComplete = false,
            priority = priorities[it % priorities.size],
            color = colors[it % colors.size],
            startTime = HourMinute(),
            endTime = HourMinute(),
            isAlertOn = true,
            name = "Task #$it",
            description = "Description of task #$it",
            taskDueDate = Time(year = 2000, month = (it % months.size) + 1, day = (it % 28) + 1)
        )
    }
}

