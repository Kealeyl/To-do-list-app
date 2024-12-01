package com.example.to_do.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
fun TaskEditScreen(
    taskUIState: TaskUIState,
    onDescriptionChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onHighClick: () -> Unit = {},
    onMediumClick: () -> Unit = {},
    onLowClick: () -> Unit = {},
    alertOn: Boolean = true,
    onAlertOnChange: (Boolean) -> Unit = {},
    onSave: () -> Unit = {},
    onDelete: (Task) -> Unit = {},
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
            name = taskUIState.editTempTask.name,
            description = taskUIState.editTempTask.description,
            onDescriptionChange = onDescriptionChange,
            onNameChange = onNameChange
        )

        Priority(onHighClick, onMediumClick, onLowClick)

        AlertSwitch(alertOn, onAlertOnChange)

        EditButtons(onSave = onSave, onDelete = onDelete, task = taskUIState.editTempTask)
    }
}

@Composable
fun EditButtons(onSave: () -> Unit, onDelete: (Task) -> Unit, task: Task) {

    val context = LocalContext.current // for the toast

    Row {
        Button(
            onClick = {
                onSave()
                Toast.makeText(context, "Saved changes of ${task.name}", Toast.LENGTH_SHORT).show()
            },
            enabled = task.name.isNotEmpty(),
            modifier = Modifier.weight(1f)
        ) { Text("Save Changes") }
        Button(onClick = {
            onDelete(task)
            Toast.makeText(context, "Deleted ${task.name}", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.weight(1f)) { Text("Delete Task") }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskEditScreenPreview() {

    val listOfTasks = MutableStateFlow(createNumAmountOfTasks(6)).asStateFlow()
    val numberOfTasks = MutableStateFlow(6).asStateFlow()

    TaskEditScreen(
        onDescriptionChange = {},
        onNameChange = {},
        onDelete = {},
        taskUIState = TaskUIState(listOfTasks = listOfTasks, numberOfTasks = numberOfTasks)
    )
}