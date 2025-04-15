package com.masa.personaltracker.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.masa.personaltracker.data.AppDatabase
import com.masa.personaltracker.data.models.Habit
import com.masa.personaltracker.utils.GameManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HabitsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val habitDao = database.habitDao()
    private val gameManager = GameManager(application, viewModelScope)

    // Expose habits as Flow instead of LiveData for Compose
    val habits: Flow<List<Habit>> = habitDao.getAllHabitsFlow()

    // Function to handle habit completion
    fun completeHabit(habit: Habit, completed: Boolean) {
        if (completed) {
            viewModelScope.launch {
                // Award experience and coins
                gameManager.addExperience(habit.experienceReward)
                gameManager.addCoins(habit.coinReward)

                // Update streak
                habitDao.incrementStreak(habit.id)
            }
        }
    }

    // Function to add a new habit
    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.insertHabit(habit)
        }
    }

    // Function to delete a habit
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.deleteHabit(habit)
        }
    }

    // Factory for creating the ViewModel with application context
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HabitsViewModel::class.java)) {
                return HabitsViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}