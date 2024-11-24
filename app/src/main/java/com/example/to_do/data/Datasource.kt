package com.example.to_do.data

import com.example.to_do.model.Colors
import com.example.to_do.model.Month
import com.example.to_do.model.Priority
import com.example.to_do.model.Task
import com.example.to_do.model.Time
import com.example.to_do.model.WeekData
import com.example.to_do.model.arrayOfWeekDays

val listOfTasksDummy = listOf(
    createDefaultTask("Groceries", "Buy more chicken"),
    createDefaultTask("Laundry", "Clean and fold laundry"),
    createDefaultTask("Build database for app", "Build using Room"),
    createDefaultTask("Finish discrete math project", "8 questions left"),
    createDefaultTask("Make lunch", "Chicken and sweet potato"),
    createDefaultTask("Submit C++ assignment", "Review code, and write readme and submit")
)


val defaultTask = createDefaultTask("", "")

fun createDefaultTask(taskName: String, taskDesc: String): Task {
    return Task(
        isComplete = false,
        priority = Priority.Low,
        color = Colors.Blue,
        startTime = Time(0, 0, true),
        endTime = Time(0, 0, true),
        dateNumber = 1,
        isAlertOn = false,
        name = taskName,
        description = taskDesc,
        month = Month.January
    )
}

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
            dateNumber = (it % 28) + 1,
            isAlertOn = true,
            name = "Task #$it",
            description = "Description of task #$it$",
            month = months[it % months.size]
        )
    }
}

