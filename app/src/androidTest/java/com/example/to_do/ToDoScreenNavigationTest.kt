package com.example.to_do

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.to_do.ui.navigaton.CreateTaskDestination
import com.example.to_do.ui.navigaton.HomeDestination
import com.example.to_do.ui.screens.ToDoApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ToDoScreenNavigationTest {
    // TestNavHostController

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>() // useful for tests that require a custom Activity

    private lateinit var navController: TestNavHostController

    @Before
    fun setupToDoNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            ToDoApp(navController = navController)
        }
    }
    // HomeDestination is the destination route when the app starts
    @Test
    fun toDoNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun toDoNavHost_verifyBackNavigationNotShownOnHomeScreen() {
        val backDescription = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backDescription).assertDoesNotExist()
    }

    @Test
    fun toDoNavHost_clickAdd_navigatesToAddScreen(){
        val addDescription = composeTestRule.activity.getString(R.string.add_button)
        composeTestRule.onNodeWithContentDescription(addDescription).performClick()
        navController.assertCurrentRouteName(CreateTaskDestination.route)
    }


}