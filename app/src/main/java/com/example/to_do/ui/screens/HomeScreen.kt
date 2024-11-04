package com.example.to_do.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.to_do.R
import com.example.to_do.data.listOfTasks
import com.example.to_do.model.Task
import com.example.to_do.data.createNumAmountOfTasks
import com.example.to_do.ui.TaskViewModel

@Composable
fun HomeScreen(
    userSearch: String = "Default",
    onUserSearchChange: (String) -> Unit = {},
    taskUiState: MutableList<Task> = listOfTasks.toMutableList(),
    onTaskClick: (Int) -> Unit = {},
    onCreateClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    //var task by remember { mutableStateOf(listOfTasks[0]) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
        //.statusBarsPadding()
        //.verticalScroll(rememberScrollState())
        //.safeDrawingPadding(),
    ) {

        SearchBar(userSearch, onUserSearchChange)

        Text(
            text = LocalContext.current.resources.getQuantityString(
                R.plurals.task,
                taskUiState.size
            )
        )

        Box {
            taskCardColumns(
                taskUiState,
                onTaskClick = onTaskClick,
                modifier = Modifier.height(460.dp)
            )

            FloatingCreateButton(
                onClick = onCreateClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
            )
        }


    }

}

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {

    //var value by remember { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_search_24), null) },
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.search)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun taskCardColumns(
    listOfTasks: List<Task>,
    onTaskClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(listOfTasks) { i, task ->
            TaskCard(task, true, {}, modifier = Modifier
                .padding(10.dp)
                .clickable {
                    onTaskClick(i)
                })
        }
    }
}

@Composable
fun FloatingCreateButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun homeScreenPreview() {
    HomeScreen()
}