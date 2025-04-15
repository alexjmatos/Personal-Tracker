package com.masa.personaltracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masa.personaltracker.data.models.Habit
import com.masa.personaltracker.ui.components.AddHabitDialog
import com.masa.personaltracker.ui.viewmodels.HabitsViewModel
import kotlinx.coroutines.launch

@Composable
fun HabitsScreen() {
    val context = LocalContext.current
    val viewModel: HabitsViewModel = viewModel(
        factory = HabitsViewModel.Factory(context.applicationContext as android.app.Application)
    )

    val habits by viewModel.habits.collectAsState(initial = emptyList())
    val showAddDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        if (habits.isEmpty()) {
            EmptyHabitsView(modifier = Modifier.align(Alignment.Center))
        } else {
            HabitsList(
                habits = habits,
                onHabitCompleted = { habit, completed ->
                    scope.launch {
                        viewModel.completeHabit(habit, completed)
                    }
                }
            )
        }

        FloatingActionButton(
            onClick = { showAddDialog.value = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Habit")
        }
    }

    if (showAddDialog.value) {
        AddHabitDialog(
            onDismiss = { showAddDialog.value = false },
            onHabitCreated = { newHabit ->
                scope.launch {
                    viewModel.addHabit(newHabit)
                    showAddDialog.value = false
                }
            }
        )
    }
}

@Composable
fun EmptyHabitsView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No habits found",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Add your first habit to get started!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun HabitsList(
    habits: List<Habit>,
    onHabitCompleted: (Habit, Boolean) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(habits) { habit ->
            HabitItem(
                habit = habit,
                onToggleCompleted = { completed ->
                    onHabitCompleted(habit, completed)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitItem(
    habit: Habit,
    onToggleCompleted: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick = { /* Show habit details */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator
            Box(
                modifier = Modifier
                    .size(width = 4.dp, height = 64.dp)
                    .background(Color(habit.color))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = habit.iconResourceId),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = habit.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = habit.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${habit.streakCount} days",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    val difficultyText = when (habit.difficultyLevel) {
                        1 -> "Easy"
                        2 -> "Medium"
                        else -> "Hard"
                    }

                    Text(
                        text = difficultyText,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "+${habit.experienceReward} XP, +${habit.coinReward} coins",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }

            Checkbox(
                checked = isChecked,
                onCheckedChange = { checked ->
                    isChecked = checked
                    onToggleCompleted(checked)
                }
            )
        }
    }
}