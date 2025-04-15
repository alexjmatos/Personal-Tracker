package com.masa.personaltracker

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import com.masa.personaltracker.ui.screens.GoalsScreen
import com.masa.personaltracker.ui.screens.HabitsScreen
import com.masa.personaltracker.ui.screens.ProfileScreen
import com.masa.personaltracker.ui.screens.TasksScreen

sealed class Screen(val route: String, val resourceId: Int, val icon: @Composable () -> Unit) {
    data object Habits : Screen(
        "habits",
        R.string.habits,
        { Icon(Icons.Filled.Home, contentDescription = "Habits") }
    )

    data object Tasks : Screen(
        "tasks",
        R.string.tasks,
        { Icon(Icons.Filled.CheckCircle, contentDescription = "Tasks") }
    )

    data object Goals : Screen(
        "goals",
        R.string.goals,
        { Icon(Icons.Filled.Star, contentDescription = "Goals") }
    )

    data object Profile : Screen(
        "profile",
        R.string.profile,
        { Icon(Icons.Filled.Person, contentDescription = "Profile") }
    )
}

@Composable
fun PersonalTrackerApp() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Habits,
        Screen.Tasks,
        Screen.Goals,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { screen.icon() },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Habits.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Habits.route) { HabitsScreen() }
            composable(Screen.Tasks.route) { TasksScreen() }
            composable(Screen.Goals.route) { GoalsScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}