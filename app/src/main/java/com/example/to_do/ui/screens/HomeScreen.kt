package com.example.to_do.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_do.R
import com.example.to_do.data.TaskUIState
import com.example.to_do.data.createNumAmountOfTasks
import com.example.to_do.data.defaultTask
import com.example.to_do.model.Task
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(
    onUserSearchChange: (String) -> Unit,
    taskUiState: TaskUIState,
    onTaskClick: (Int) -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var listOfTasks: List<Task>

    if (taskUiState.userSearch.isEmpty()) {
        listOfTasks = taskUiState.listOfTasks.collectAsState().value
    } else {
        listOfTasks = taskUiState.listOfTasksSearch?.collectAsState()?.value ?: listOf()
    }

    val numberOfTasks by taskUiState.numberOfTasks.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
        //.statusBarsPadding()
        //.verticalScroll(rememberScrollState())
        //.safeDrawingPadding(),
    ) {

        SearchBar(taskUiState.userSearch, onUserSearchChange)

        Text(
            text = LocalContext.current.resources.getQuantityString(
                R.plurals.task,
                numberOfTasks
            )
        )

        Box {
            if (numberOfTasks > 0) {
                taskCardColumns(
                    listOfTasks,
                    onTaskClick = onTaskClick,
                    modifier = Modifier
                        .height(460.dp)
                        .fillMaxWidth()
                )
            } else {
                Text(
                    "No tasks. Click + to add.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(460.dp)
                        .fillMaxWidth()
                )
            }


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
        items(items = listOfTasks, key = { it.id }) { task ->

            TaskCard(task, true, {}, modifier = Modifier
                .padding(10.dp)
                .clickable { onTaskClick(task.id) })
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
            contentDescription = stringResource(R.string.add_button)
        )
    }
}

@Composable
fun TaskCard(
    task: Task,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.height(dimensionResource(R.dimen.task_card_height))) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(
                Modifier
                    .width(dimensionResource(R.dimen.task_card_spacer_width))
                    .fillMaxHeight()
                    .background(color = Color.Gray)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = dimensionResource(R.dimen.task_card_column_padding_start)),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.task_card_name_padding_bottom))
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                        contentDescription = "Calendar image",
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.task_card_image_padding_end))
                            .size(dimensionResource(R.dimen.task_card_image_size))
                    )
                    Text(text = task.dateToString(), fontSize = 12.sp)
                }

            }
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.tertiary)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun homeScreenPreview() {

    val listOfTasks = MutableStateFlow(createNumAmountOfTasks(6))
    val numberOfTasks = MutableStateFlow(6)

    // Create TaskUIState and pass StateFlow values correctly
    HomeScreen(
        onUserSearchChange = {},
        onCreateClick = {},
        onTaskClick = {},
        taskUiState = TaskUIState(
            listOfTasks = listOfTasks,
            numberOfTasks = numberOfTasks
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun homeScreenPreviewNoTasks() {

    val listOfTasks = MutableStateFlow(createNumAmountOfTasks(0))
    val numberOfTasks = MutableStateFlow(0)

    HomeScreen(
        onUserSearchChange = {},
        onCreateClick = {},
        onTaskClick = {},
        taskUiState = TaskUIState(
            listOfTasks = listOfTasks,
            numberOfTasks = numberOfTasks
        )
    )
}