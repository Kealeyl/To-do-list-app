package com.example.to_do.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.to_do.ui.TaskViewModel
import com.example.to_do.ui.navigaton.CreateTaskDestination
import com.example.to_do.ui.navigaton.EditTaskDestination
import com.example.to_do.ui.navigaton.HomeDestination
import com.example.to_do.ui.navigaton.getTitleResForRoute
import kotlinx.coroutines.launch


@Composable
fun ToDoApp(
    taskViewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val taskUiState by taskViewModel.uiState.collectAsState()

    val numberOfTasks by taskUiState.numberOfTasks.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val coroutineScope = rememberCoroutineScope()

    // default value of home screen
    val titleResId =
        getTitleResForRoute(backStackEntry?.destination?.route ?: HomeDestination.route)

    Scaffold(
        topBar = {
            ToDoAppBar(
                currentScreenTitle = titleResId,
                // if there's a screen behind the current screen on the back stack, the Up button should show
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                },
                listSize = numberOfTasks // only used for home screen num of tasks
            )
        }
    ) { innerPadding ->
        // map routes
        NavHost(
            navController = navController, startDestination = HomeDestination.route,
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            // keep nav logic separate from individual UI
            composable(HomeDestination.route) {
                HomeScreen(
                    onUserSearchChange = taskViewModel::onSearch,
                    taskUiState = taskUiState,
                    onTaskClick = { taskId ->
                        coroutineScope.launch {
                            taskViewModel.clickTask(taskId)
                        }
                        // for a later refactor to use multiple view models
                        navController.navigate("${EditTaskDestination.route}/${taskId}")
                    },
                    onCreateClick = {
                        taskViewModel.resetTempTask()
                        navController.navigate(CreateTaskDestination.route)
                    }
                )
            }

            composable( // for a later refactor to use multiple view models
                route = EditTaskDestination.routeWithArgs,
                arguments = listOf(navArgument(EditTaskDestination.taskIdArg) {
                    type = NavType.IntType
                }) // can be accessed through view model with saved state
            ) {
                TaskEditScreen(
                    taskUIState = taskUiState,
                    onDescriptionChange = taskViewModel::onDescriptionEdit,
                    onNameChange = taskViewModel::onNameEdit,
                    onSave = {
                        taskViewModel.editTask()
                        navController.navigateUp()
                    },
                    onDelete = {
                        taskViewModel.deleteTask(it)
                        navController.navigateUp()
                    }
                )
            }

            composable(CreateTaskDestination.route) {
                TaskCreateScreen(
                    taskUIState = taskUiState,
                    onDescriptionChange = taskViewModel::onDescriptionCreate,
                    onNameChange = taskViewModel::onNameCreate,
                    onCreate = {
                        taskViewModel.createTask()
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}