package com.example.to_do.ui

import androidx.lifecycle.ViewModel
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
    private val _uiState = MutableStateFlow<MutableList<Task>>(mutableListOf())

    // read-only state flow
    val uiState: StateFlow<MutableList<Task>> = _uiState.asStateFlow()


    fun createTask(
        priority: Priority,
        startTime: Time,
        endTime: Time,
        month: Month,
        dateNumber: Int,
        isAlertOn: Boolean,
        name: String,
        description: String,
        color: Colors
    ) {
        _uiState.update {
            it.add(
                Task(
                    false,
                    priority,
                    startTime,
                    endTime,
                    month,
                    dateNumber,
                    isAlertOn,
                    name,
                    description,
                    color
                )
            )
            it
        }
    }

    fun getTask(index: Int): Task {
        return _uiState.value[index]
    }

    fun editTask(
        index: Int,
        priority: Priority? = null,
        startTime: Time? = null,
        endTime: Time? = null,
        month: Month? = null,
        dateNumber: Int? = null,
        isAlertOn: Boolean? = null,
        name: String? = null,
        description: String? = null,
        color: Colors? = null
    ) {
        if (index < 0 || index >= _uiState.value.size) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for size ${_uiState.value.size}")
        }
            _uiState.update {
                it[index] = it[index].copy(
                    priority = priority ?: it[index].priority,
                    startTime = startTime ?: it[index].startTime,
                    endTime = endTime ?: it[index].endTime,
                    month = month ?: it[index].month,
                    dateNumber = dateNumber ?: it[index].dateNumber,
                    isAlertOn = isAlertOn ?: it[index].isAlertOn,
                    name = name ?: it[index].name,
                    description = description ?: it[index].description,
                    color = color ?: it[index].color
                )
                it
            }
        }

    fun deleteTask(index: Int){
        if (index < 0 || index >= _uiState.value.size) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for size ${_uiState.value.size}")
        }

        _uiState.update {
            it.removeAt(index)
            it
        }
    }
}




