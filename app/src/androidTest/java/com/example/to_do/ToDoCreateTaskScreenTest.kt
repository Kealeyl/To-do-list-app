package com.example.to_do

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.to_do.fake.FakeTaskRepository
import com.example.to_do.ui.TaskViewModel
import com.example.to_do.ui.screens.TaskCreateScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ToDoCreateTaskScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel = TaskViewModel(tasksRepository = FakeTaskRepository())

    @Before
    fun setupToDoNavHost() {
        composeTestRule.setContent {
            val taskUiState by viewModel.uiState.collectAsState()
            TaskCreateScreen(
                taskUIState = taskUiState,
                onDescriptionChange = viewModel::onDescriptionCreate,
                onNameChange = viewModel::onNameCreate,
                onCreate = {
                    viewModel.createTask()
                }
            )
        }
    }

    @Test
    fun createTaskScreen_verifyNameButtonEnabled() {
        composeTestRule.onNodeWithText("Name").performTextInput("Task1")
        composeTestRule.onNodeWithText("Create Task").assertIsEnabled()
    }

    @Test
    fun createTaskScreen_verifyButtonRemainsDisabled() {
        composeTestRule.onNodeWithText("Create Task").assertIsDisplayed()
    }


}