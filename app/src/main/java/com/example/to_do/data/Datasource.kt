package com.example.to_do.data

import com.example.to_do.model.Colors
import com.example.to_do.model.Month
import com.example.to_do.model.Priority
import com.example.to_do.model.Task
import com.example.to_do.model.Time
import java.util.Date

val listOfTasks = listOf(
    Task(isComplete = false,
        priority = Priority.High,
        startTime = Time(12, 30, true),
        endTime = Time(2, 45, false),
        month = Month.July,
        dateNumber = 25,
        isAlertOn = true,
        name = "First Task",
        description = "First task made",
        color = Colors.Pink),
    Task(isComplete = false,
        priority = Priority.Medium,
        startTime = Time(10, 20, false),
        endTime = Time(8, 55, true),
        month = Month.February,
        dateNumber = 8,
        isAlertOn = false,
        name = "Second Task",
        description = "Second task made",
        color = Colors.Orange),
    Task(isComplete = true,
        priority = Priority.Low,
        startTime = Time(11, 20, false),
        endTime = Time(9, 55, true),
        month = Month.June,
        dateNumber = 1,
        isAlertOn = false,
        name = "Third Task",
        description = "Third task made",
        color = Colors.Blue)
)

class Datasource {

}