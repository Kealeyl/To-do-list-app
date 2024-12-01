package com.example.to_do.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.to_do.TaskAppApplication
import com.example.to_do.data.TaskUIState
import com.example.to_do.data.TasksRepository
import com.example.to_do.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    // Backing property
    // initial value is an empty mutable list
    private val _uiState = MutableStateFlow(
        TaskUIState(
            listOfTasks = tasksRepository.getAllTasksStream().stateIn( // only need to fetch once
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = listOf()
            ),
            numberOfTasks = tasksRepository.getNumberOfTasks().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = 0
            ),
            listOfTasksSearch = null
        )
    )

    // read-only state flow
    val uiState: StateFlow<TaskUIState> = _uiState.asStateFlow()

    suspend fun clickTask(taskId: Int) { // suspend?
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    editTempTask =
                    tasksRepository.getTaskStream(taskId).filterNotNull().first()
                )
            }
        }

        Log.d("clickTask", "Name: ${_uiState.value.editTempTask.name}")
        Log.d("clickTask", "Obj: ${_uiState.value.editTempTask}")
    }

    fun resetTempTask() {
        _uiState.update {
            it.copy(createTempTask = it.createTempTask.copy(name = "", description = ""))
        }
    }

    fun onSearch(searched: String) {
        _uiState.update {
            it.copy(
                userSearch = searched
            )
        }
        viewModelScope.launch{

            _uiState.update {
                it.copy(
                    listOfTasksSearch = tasksRepository.getTasksBySearch(searched).stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                        initialValue = listOf()
                    ),
                )
            }
        }
    }

    // Set the temp task name
    fun onNameCreate(nameChange: String) {
        _uiState.update {
            it.copy(createTempTask = it.createTempTask.copy(name = nameChange))
        }
    }

    // Set the temp task description
    fun onDescriptionCreate(descriptionChange: String) {
        _uiState.update {
            it.copy(createTempTask = it.createTempTask.copy(description = descriptionChange))
        }
    }

    fun onNameEdit(nameChange: String) {
        _uiState.update {
            it.copy(editTempTask = it.editTempTask.copy(name = nameChange))
        }
    }

    fun onDescriptionEdit(descriptionChange: String) {
        _uiState.update {
            it.copy(editTempTask = it.editTempTask.copy(description = descriptionChange))
        }
    }

    // add temp task to list
    // inserts item into the Room database, adds in a non-blocking way.
    fun createTask() {
        viewModelScope.launch {
            tasksRepository.insertTask(_uiState.value.createTempTask)
        }
    }

    fun editTask() {
        viewModelScope.launch {
            tasksRepository.updateTask(_uiState.value.editTempTask)
        }
    }

    //delete taskToDelete
    fun deleteTask(taskToDelete: Task) {
        viewModelScope.launch {
            tasksRepository.deleteTask(taskToDelete)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[APPLICATION_KEY] as TaskAppApplication) // used to find the app's TaskAppApplication object

                val tasksRepository = application.container.taskRepository
                TaskViewModel(tasksRepository = tasksRepository)
            }
        }
    }
}