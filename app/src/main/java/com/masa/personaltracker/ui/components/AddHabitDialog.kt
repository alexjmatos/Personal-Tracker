package com.masa.personaltracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.masa.personaltracker.R
import com.masa.personaltracker.data.models.Habit
import kotlin.random.Random

@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onHabitCreated: (Habit) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var frequencyType by remember { mutableStateOf("DAILY") }
    var difficultyLevel by remember { mutableIntStateOf(1) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Create New Habit",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Habit Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Frequency",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    RadioButton(
                        selected = frequencyType == "DAILY",
                        onClick = { frequencyType = "DAILY" }
                    )
                    Text(
                        text = "Daily",
                        modifier = Modifier.padding(start = 8.dp, top = 12.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    RadioButton(
                        selected = frequencyType == "WEEKLY",
                        onClick = { frequencyType = "WEEKLY" }
                    )
                    Text(
                        text = "Weekly",
                        modifier = Modifier.padding(start = 8.dp, top = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Difficulty",
                    style = MaterialTheme.typography.titleMedium
                )

                Slider(
                    value = difficultyLevel.toFloat(),
                    onValueChange = { difficultyLevel = it.toInt() },
                    valueRange = 1f..3f,
                    steps = 1,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Easy")
                    Text("Medium")
                    Text("Hard")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            // Create a new habit with calculated rewards based on difficulty
                            val xpReward = when (difficultyLevel) {
                                1 -> 10
                                2 -> 20
                                else -> 30
                            }

                            val coinReward = when (difficultyLevel) {
                                1 -> 5
                                2 -> 10
                                else -> 15
                            }

                            // In a real app, you'd have a proper icon selection
                            val iconResourceId = R.drawable.ic_habit_default

                            // Create a random color (in a real app, user would select)
                            val color = android.graphics.Color.rgb(
                                Random.nextInt(256),
                                Random.nextInt(256),
                                Random.nextInt(256)
                            )

                            val newHabit = Habit(
                                title = title,
                                description = description,
                                frequencyType = frequencyType,
                                frequencyValue = if (frequencyType == "WEEKLY") "1,3,5" else "0",
                                difficultyLevel = difficultyLevel,
                                experienceReward = xpReward,
                                coinReward = coinReward,
                                color = color,
                                iconResourceId = iconResourceId
                            )

                            onHabitCreated(newHabit)
                        },
                        enabled = title.isNotBlank()
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}