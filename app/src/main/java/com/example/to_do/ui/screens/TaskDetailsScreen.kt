package com.example.to_do.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_do.R
import com.example.to_do.data.listOfTasks
import com.example.to_do.data.weeks

// weekly calandar
// navigation

@Composable
fun TaskDetailsScreen(
    name: String,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onHighClick: () -> Unit = {},
    onMediumClick: () -> Unit = {},
    onLowClick: () -> Unit = {},
    alertOn: Boolean = true,
    onAlertOnChange: (Boolean) -> Unit = {},
    onCreate: () -> Unit = {},
    isEditScreen: Boolean,
    onSave: () -> Unit = {},
    onDelete: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        //topBar2(appBarTitle, onBackButton = onArrowBack)

        WeekBar()

        NameAndDesc(
            name = name,
            description = description,
            onDescriptionChange = onDescriptionChange,
            onNameChange = onNameChange
        )

        Priority(onHighClick, onMediumClick, onLowClick)

        AlertSwitch(alertOn, onAlertOnChange)

        if (isEditScreen) {
            EditButtons(onSave = onSave, onDelete = onDelete, name = name)
        } else {
            CreateButton(onCreate = onCreate, name = name)
        }


    }
}

@Composable
fun EditButtons(onSave: () -> Unit, onDelete: () -> Unit, name: String) {

    val context = LocalContext.current // for the toast

    Row {
        Button(
            onClick = {
                onSave()
                Toast.makeText(context, "Saved changes of $name", Toast.LENGTH_SHORT).show()
            },
            enabled = name.isNotEmpty(),
            modifier = Modifier.weight(1f)
        ) { Text("Save Changes") }
        Button(onClick = {
            onDelete()
            Toast.makeText(context, "Deleted $name", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.weight(1f)) { Text("Delete Task") }
    }
}

@Composable
fun CreateButton(onCreate: () -> Unit, name: String) {

    val context = LocalContext.current // for the toast

    // the button is enabled when the user writes a name
    Button(
        onClick = {
            onCreate()
            Toast.makeText(context, "Created $name", Toast.LENGTH_SHORT).show()
        },
        enabled = name.isNotEmpty(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Create Task")
    }
}

@Composable
fun AlertSwitch(
    alertOn: Boolean,
    onAlertOnChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(id = R.string.get_alert))
        Switch(
            checked = alertOn,
            onCheckedChange = onAlertOnChange,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Composable
fun Priority(onHighClick: () -> Unit, onMediumClick: () -> Unit, onLowClick: () -> Unit) {
    Column {
        Text("Priority")
        Row() {
            OutlinedButton(onClick = onHighClick, modifier = Modifier.weight(1f)) { Text("High") }
            OutlinedButton(
                onClick = onMediumClick,
                modifier = Modifier.weight(1f)
            ) { Text("Medium") }
            OutlinedButton(onClick = onLowClick, modifier = Modifier.weight(1f)) { Text("Low") }
        }
    }
}

@Composable
fun NameAndDesc(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    isCorrectInput: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column {

        Text(stringResource(R.string.schedule))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = {
                if (isCorrectInput) {
                    Text(stringResource(R.string.name_label))
                } else {
                    Text(stringResource(R.string.error_label))
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = !isCorrectInput,
            modifier = modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(stringResource(R.string.description_label)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = modifier.fillMaxWidth(),
            minLines = 6
        )
    }
}

@Composable
fun WeekBar(
    onArrowBack: () -> Unit = {},
    onArrowForward: () -> Unit = {},
    onDateClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var currentWeek by remember { mutableStateOf(0) }

    val weekData = weeks[currentWeek]
    val listSize = weeks.size - 1

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(
                onClick = {
                    if (currentWeek == listSize) {
                        currentWeek = 0
                    } else {
                        currentWeek++
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = null
                )
            }

            Surface(modifier = Modifier.clickable { onDateClick() }, tonalElevation = 4.dp) {
                Text(
                    weekData.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Image(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = "Arrow forward", modifier = Modifier.clickable {
                    if (currentWeek == 0) {
                        currentWeek = listSize
                    } else {
                        currentWeek--
                    }

                })
        }
        DaysRow(weeks[currentWeek].weekDays)
    }
}

@Composable
fun DayCard(
    weekDay: String,
    numberDate: String,
    onDayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.clickable { onDayClick() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(weekDay, modifier = Modifier.padding(8.dp))
            Text(numberDate, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun DaysRow(daysList: Array<Pair<String, Int>>) {
    LazyRow {
        items(daysList) {
            DayCard(it.first, it.second.toString(), {}, modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskDetailsScreenPreview() {
    val aTask = listOfTasks[0]
    TaskDetailsScreen(
        name = "A default task!",
        description = "A default description!",
        onDescriptionChange = {},
        onNameChange = {},
        isEditScreen = true
    )
}