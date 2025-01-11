package com.example.to_do.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_do.R
import com.example.to_do.ui.navigaton.CreateTaskDestination
import com.example.to_do.ui.navigaton.EditTaskDestination
import com.example.to_do.ui.navigaton.HomeDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    listSize: Int = 0,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (currentScreenTitle == 0) {
                Text(
                    LocalContext.current.resources.getQuantityString(
                        R.plurals.amount_of_tasks,
                        listSize,
                        listSize
                    )
                )
            } else {
                Text(stringResource(currentScreenTitle))
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },

        actions = {

//            if (currentScreenTitle == 0) {
//                IconButton(onClick = { }) {
//                    Icon(Icons.Default.Settings, contentDescription = "Settings")
//                }
//            }
        }
    )
}

@Preview
@Composable
fun ToDoAppBarHome() {
    ToDoAppBar(
        currentScreenTitle = HomeDestination.titleRes,
        canNavigateBack = false,
        navigateUp = {},
        listSize = 8
    )
}

@Preview
@Composable
fun ToDoAppBarEditTask() {
    ToDoAppBar(
        currentScreenTitle = EditTaskDestination.titleRes,
        canNavigateBack = true,
        navigateUp = {}
    )
}

@Preview
@Composable
fun ToDoAppBarCreateTask() {
    ToDoAppBar(
        currentScreenTitle = CreateTaskDestination.titleRes,
        canNavigateBack = true,
        navigateUp = {}
    )
}