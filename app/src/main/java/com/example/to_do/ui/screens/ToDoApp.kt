package com.example.to_do.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.to_do.R
import com.example.to_do.ui.TaskViewModel

// routes
enum class ToDoScreen(
    @StringRes val titleResId: Int
) {
    CreateTask(titleResId = R.string.create_task_title),
    Home(titleResId = 0), // special case that will use plural that depends on num of tasks
    EditTask(titleResId = R.string.edit_task_title);
}

@Composable
fun ToDoApp(
    taskViewModel: TaskViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val taskUiState by taskViewModel.uiState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()

    //to make app bar aware of current screen, for the app bar title
    val currentScreen = ToDoScreen.valueOf(
        backStackEntry?.destination?.route ?: ToDoScreen.Home.name // default value of home screen
    )

    Scaffold(
        topBar = {
            ToDoAppBar(
                currentScreenTitle = currentScreen.titleResId,
                // if there's a screen behind the current screen on the back stack, the Up button should show
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                },
                listSize = taskViewModel.getAmountOfTasks() // only used for home screen num of tasks
            )
        }
    ) { innerPadding ->
        // map routes
        NavHost(
            navController = navController, startDestination = ToDoScreen.Home.name,
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            // keep nav logic separate from individual UI
            composable(ToDoScreen.Home.name) {
                HomeScreen(
                    onUserSearchChange = taskViewModel::onSearch,
                    taskUiState = taskUiState,
                    onTaskClick = { index, task ->
                        taskViewModel.clickTask(task)
                        navController.navigate(ToDoScreen.EditTask.name)
                    },
                    onCreateClick = {
                        taskViewModel.resetTempTask()
                        navController.navigate(ToDoScreen.CreateTask.name)
                    }
                )
            }

            composable(ToDoScreen.EditTask.name) {
                TaskDetailsScreen(
                    taskUIState = taskUiState,
                    onDescriptionChange = taskViewModel::onDescriptionEdit,
                    onNameChange = taskViewModel::onNameEdit,
                    onSave = {
                        taskViewModel.editTask()
                        navController.navigateUp()
                        //showToast = true
                    },
                    onDelete = {
                        taskViewModel.deleteTask(it)
                        navController.navigateUp()
                    },
                    isEditScreen = true
                )
            }

            composable(ToDoScreen.CreateTask.name) {
                TaskDetailsScreen(
                    taskUIState = taskUiState,
                    onDescriptionChange = taskViewModel::onDescriptionEdit,
                    onNameChange = taskViewModel::onNameEdit,
                    onCreate = {
                        taskViewModel.createTask(it)
                        navController.navigateUp()
                    },
                    isEditScreen = false
                )
            }
        }
    }

//    if(showToast){
//        CustomToast(message = "Saved changes of task", 3000) {
//            showToast = false;
//        }
//    }
}