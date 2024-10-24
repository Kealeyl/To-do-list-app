package com.example.to_do.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_do.R
import com.example.to_do.data.listOfTasks
import com.example.to_do.model.Task
import com.example.to_do.ui.theme.TodoTheme


@Composable
fun TaskCard(task: Task, checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
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
                    Image(
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

@Preview(name = "task card", showSystemUi = true)
@Composable
private fun TaskCardPreview() {
    TodoTheme {
        TaskCard(
            listOfTasks[1], checked = true, onCheckedChange = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}