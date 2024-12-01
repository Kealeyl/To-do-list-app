package com.example.to_do.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_do.data.TaskUIState
import com.example.to_do.data.createNumAmountOfTasks
import com.example.to_do.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun TaskCreateScreen(
    taskUIState: TaskUIState,
    onDescriptionChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onHighClick: () -> Unit = {},
    onMediumClick: () -> Unit = {},
    onLowClick: () -> Unit = {},
    alertOn: Boolean = true,
    onAlertOnChange: (Boolean) -> Unit = {},
    onCreate: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        WeekBar()

        NameAndDesc(
            name = taskUIState.createTempTask.name,
            description = taskUIState.createTempTask.description,
            onDescriptionChange = onDescriptionChange,
            onNameChange = onNameChange
        )

        Priority(onHighClick, onMediumClick, onLowClick)

        AlertSwitch(alertOn, onAlertOnChange)

        CreateButton(onCreate = onCreate, task = taskUIState.createTempTask)
    }
}

@Composable
fun CreateButton(onCreate: () -> Unit, task: Task) {

    val context = LocalContext.current // for the toast

    // the button is enabled when the user writes a name
    Button(
        onClick = {
            onCreate()
            Toast.makeText(context, "Created ${task.name}", Toast.LENGTH_SHORT).show()
        },
        enabled = task.name.isNotEmpty(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Create Task")
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskCreateScreenPreview() {

    val listOfTasks = MutableStateFlow(createNumAmountOfTasks(6)).asStateFlow()
    val numberOfTasks = MutableStateFlow(6).asStateFlow()

    TaskCreateScreen(
        onDescriptionChange = {},
        onNameChange = {},
        taskUIState = TaskUIState(listOfTasks = listOfTasks, numberOfTasks = numberOfTasks)
    )
}