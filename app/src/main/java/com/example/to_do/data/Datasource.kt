package com.example.to_do.data

import com.example.to_do.model.Colors
import com.example.to_do.model.Month
import com.example.to_do.model.Priority
import com.example.to_do.model.Task
import com.example.to_do.model.Time
import com.example.to_do.model.WeekData
import com.example.to_do.model.arrayOfWeekDays

val listOfTasksDummy = listOf(
    Task(
        isComplete = false,
        priority = Priority.High,
        startTime = Time(12, 30, false),
        endTime = Time(2, 45, false),
        month = Month.July,
        dateNumber = 25,
        isAlertOn = true,
        name = "First Task",
        description = "First task made",
        color = Colors.Pink
    ),
    Task(
        isComplete = false,
        priority = Priority.Medium,
        startTime = Time(10, 20, true),
        endTime = Time(11, 55, true),
        month = Month.February,
        dateNumber = 8,
        isAlertOn = false,
        name = "Second Task",
        description = "Second task made",
        color = Colors.Orange
    ),
    Task(
        isComplete = true,
        priority = Priority.Low,
        startTime = Time(11, 20, true),
        endTime = Time(1, 20, false),
        month = Month.June,
        dateNumber = 1,
        isAlertOn = false,
        name = "Third Task",
        description = "Third task made",
        color = Colors.Blue
    )
)

val defaultTask = Task(
    isComplete = false,
    priority = Priority.Low,
    color = Colors.Blue,
    startTime = Time(0, 0, true),
    endTime = Time(0, 0, true),
    dateNumber = 1,
    isAlertOn = false,
    name = "",
    description = "",
    month = Month.January
)

val weeks = listOf(
    WeekData(27, Month.July, arrayOfWeekDays(27)),
    WeekData(3, Month.October, arrayOfWeekDays(3)),
    WeekData(10, Month.June, arrayOfWeekDays(10))
)

fun createNumAmountOfTasks(num: Int): Array<Task> {

    val priorities = Priority.values()
    val colors = Colors.values()
    val months = Month.values()

    return Array(num) {
        Task(
            isComplete = false,
            priority = priorities[it % priorities.size],
            color = colors[it % colors.size],
            startTime = Time(11, 20, true),
            endTime = Time(1, 20, false),
            dateNumber = (it % 28)+1,
            isAlertOn = true,
            name = "Task #$it",
            description = "Description of task #$it$",
            month = months[it % months.size]
        )
    }
}

