package com.example.to_do.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.to_do.data.createNumAmountOfTasks
import com.example.to_do.data.listOfTasks
import com.example.to_do.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    // Backing property
    // initial value is an empty mutable list
    private val _uiState = MutableStateFlow<MutableList<Task>>(mutableListOf())

    // read-only state flow
    // change to immutable List<Task> ?
    val uiState: StateFlow<MutableList<Task>> = _uiState.asStateFlow()

    init {
        _uiState.value = createNumAmountOfTasks(3).toMutableList()
    }

    var userSearch by mutableStateOf("")
        private set

    private var tempTask: Task = listOfTasks[0]

    private var taskIndex: Int = -1

    var tempName by mutableStateOf("")
        private set

    var tempDescription by mutableStateOf("")
        private set

//    val tempTask: Task = Task(
//        isComplete = false,
//        priority = Priority.Low,
//        color = Colors.Blue,
//        startTime = Time(0, 0, true),
//        endTime = Time(0, 0, true),
//        dateNumber = 1,
//        isAlertOn = false,
//        name = "",
//        description = "",
//        month = Month.January
//    )

    fun getAmountOfTasks(): Int{
        return _uiState.value.size
    }

    fun clickTask(index: Int) {
        taskIndex = index
        tempTask = getTask(index)
        tempName = tempTask.name
        tempDescription = tempTask.description
    }

    fun initializeNewTask(){
        tempName = ""
        tempDescription = ""
    }

    fun onSearch(searched: String) {
        userSearch = searched
    }

    fun onNameEdit(nameChange: String) {
        tempName = nameChange
    }

    fun onDescriptionEdit(descriptionChange: String) {
        tempDescription = descriptionChange
    }


    // reset temp task
    fun createTask() {
        _uiState.update {
            it.add(
                Task(
                    false,
                    tempTask.priority,
                    tempTask.startTime,
                    tempTask.endTime,
                    tempTask.month,
                    tempTask.dateNumber,
                    tempTask.isAlertOn,
                    tempName,
                    tempDescription,
                    tempTask.color
                )
            )
            it
        }
    }

    fun getTask(index: Int): Task {
        if (index < 0 || index >= _uiState.value.size) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for size $_uiState.value.size")
        }
        return _uiState.value[index]
    }

    // reset temp task
    fun editTask(index: Int = taskIndex) {
        if (index < 0 || index >= _uiState.value.size) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for size $_uiState.value.size")
        }
        _uiState.update {
            it[index] = it[index].copy(
                priority = tempTask.priority,
                startTime = tempTask.startTime,
                endTime = tempTask.endTime,
                month = tempTask.month,
                dateNumber = tempTask.dateNumber,
                isAlertOn = tempTask.isAlertOn,
                name = tempName,
                description = tempDescription,
                color = tempTask.color,
            )
            it
        }

    }

    fun deleteTask(index: Int = taskIndex) {
        if (index < 0 || index >= _uiState.value.size) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for size ${_uiState.value.size}")
        }

        _uiState.update {
            it.removeAt(index)
            it
        }
    }
}




