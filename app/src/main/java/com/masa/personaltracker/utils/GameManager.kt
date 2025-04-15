package com.masa.personaltracker.utils

import android.content.Context
import com.masa.personaltracker.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class GameManager(private val context: Context, private val scope: CoroutineScope) {
    private val userDao = AppDatabase.getDatabase(context, scope).userDao()

    // Level system configuration
    private val baseXp = 100
    private val xpScaleFactor = 1.5

    // Calculate XP required for a given level
    fun getXpForLevel(level: Int): Int {
        return (baseXp * Math.pow(xpScaleFactor, (level - 1).toDouble())).toInt()
    }

    // Add experience and handle level up
    suspend fun addExperience(amount: Int) {
        withContext(Dispatchers.IO) {
            val user = userDao.getUser().value ?: return@withContext
            var newXp = user.experience + amount
            var currentLevel = user.level
            var levelsGained = 0

            // Check for level ups
            while (newXp >= getXpForLevel(currentLevel)) {
                newXp -= getXpForLevel(currentLevel)
                currentLevel++
                levelsGained++
            }

            // Update user data
            val updatedUser = user.copy(
                level = currentLevel,
                experience = newXp,
                coins = user.coins + (levelsGained * 50) // Bonus coins on level up
            )

            userDao.updateUser(updatedUser)

            if (levelsGained > 0) {
                // Trigger level up celebration
                withContext(Dispatchers.Main) {
                    showLevelUpNotification(context, currentLevel)
                }
            }
        }
    }

    // Add coins to user
    suspend fun addCoins(amount: Int) {
        userDao.addCoins(amount)
    }

    // Check and update streak
    suspend fun checkDailyStreak() {
        withContext(Dispatchers.IO) {
            val user = userDao.getUser().value ?: return@withContext
            val now = System.currentTimeMillis()
            val lastCheckIn = user.lastCheckIn

            // Check if last check-in was yesterday
            val dayDiff =
                TimeUnit.MILLISECONDS.toDays(now) - TimeUnit.MILLISECONDS.toDays(lastCheckIn)

            when {
                dayDiff == 1L -> {
                    // Perfect streak - increment
                    userDao.incrementStreak(now)
                    // Streak bonus
                    val streakBonus = 10 + (user.streakDays * 2) // Base 10 + 2 per day
                    userDao.addCoins(streakBonus)
                    showStreakNotification(context, user.streakDays + 1, streakBonus)
                }

                dayDiff > 1L -> {
                    // Streak broken
                    userDao.resetStreak(now)
                }

                dayDiff == 0L -> {
                    // Already checked in today, do nothing
                }
            }
        }
    }

    // Show notifications
    private fun showLevelUpNotification(context: Context, newLevel: Int) {
        // Implementation for showing level up notification
        Notifications.showLevelUpNotification(context, newLevel)
    }

    private fun showStreakNotification(context: Context, streakDays: Int, bonus: Int) {
        // Implementation for showing streak notification
        Notifications.showStreakNotification(context, streakDays, bonus)
    }
}