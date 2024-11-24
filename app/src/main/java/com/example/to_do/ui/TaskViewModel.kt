package com.example.to_do.ui

import androidx.lifecycle.ViewModel
import com.example.to_do.data.TaskUIState
import com.example.to_do.data.createNumAmountOfTasks
import com.example.to_do.data.listOfTasksDummy
import com.example.to_do.model.Colors
import com.example.to_do.model.Month
import com.example.to_do.model.Priority
import com.example.to_do.model.Task
import com.example.to_do.model.Time
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    // Backing property
    // initial value is an empty mutable list
    private val _uiState = MutableStateFlow(TaskUIState())

    // read-only state flow
    // change to immutable List<Task> ?
    val uiState: StateFlow<TaskUIState> = _uiState.asStateFlow()

    init {
        _uiState.value = TaskUIState(
            listOfTasks = listOfTasksDummy
        )
    }

    fun getAmountOfTasks(): Int {
        return _uiState.value.listOfTasks.size
    }

    // Set the temp task values with the values of the clicked task
    fun clickTask(task: Task) {
        _uiState.update {
            it.copy(tempTask = task, originalTask = task)
        }
    }

    // For the creation of a new task
    fun resetTempTask() {
        val tempTask = Task(
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

        _uiState.update {
            it.copy(tempTask = tempTask)
        }
    }

    fun onSearch(searched: String) {

        val listOfTasksOriginalOrder: List<Task> = _uiState.value.listOfTasks

        if (searched.isBlank()) {
            _uiState.update {
                it.copy(
                    userSearch = searched,
                    listOfTasks = it.listOfTemp,
                    listOfTemp = emptyList()
                )
            }
        } else {
            // get tasks that start with string
            val listOfMatches: List<Task> =
                _uiState.value.listOfTasks.filter { task ->
                    task.name.startsWith(searched, ignoreCase = true)
                }

            _uiState.update {
                it.copy(
                    userSearch = searched,
                    listOfTasks = (
                            listOfMatches.sortedBy { task -> task.name } + it.listOfTasks.filter { task ->
                                task !in listOfMatches
                            }),
                    listOfTemp = if (it.listOfTemp.isEmpty()) {
                        listOfTasksOriginalOrder // Only saved original when first letter is searched
                    } else {
                        it.listOfTemp
                    }
                )
            }
        }
    }

    // Set the temp task name
    fun onNameEdit(nameChange: String) {
        _uiState.update {
            it.copy(tempTask = it.tempTask.copy(name = nameChange))
        }


    }

    // Set the temp task description
    fun onDescriptionEdit(descriptionChange: String) {
        _uiState.update {
            it.copy(tempTask = it.tempTask.copy(description = descriptionChange))
        }
    }

    // add temp task to list
    fun createTask(task: Task) {
        _uiState.update {
            it.copy(listOfTasks = it.listOfTasks + task)
        }
    }

    // overwrite original task to the temp task
    fun editTask() {
        _uiState.update {
            it.copy(listOfTasks = it.listOfTasks.map {task ->
                if (task == it.originalTask) {
                    _uiState.value.tempTask
                } else {
                    task
                }
            })
        }
    }

    // delete taskToDelete
    fun deleteTask(taskToDelete: Task) {
        _uiState.update {
            it.copy(listOfTasks = it.listOfTasks.filter { task -> task != taskToDelete })
        }
    }
}