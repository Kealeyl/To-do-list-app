package com.example.to_do

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.to_do.fake.FakeTaskRepository
import com.example.to_do.ui.TaskViewModel
import com.example.to_do.ui.screens.TaskCreateScreen
import org.junit.Rule
import org.junit.Test

class ToDoStateRestorationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel = TaskViewModel(tasksRepository = FakeTaskRepository())

    @Test
    fun testConfigurationChangeOnNameEditText(){
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
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
        composeTestRule.onNodeWithText("Name").performTextInput("Task1")

        // config change
        stateRestorationTester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithText("Name").assertExists()

    }
}