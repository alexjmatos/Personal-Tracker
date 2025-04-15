package com.masa.personaltracker.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.masa.personaltracker.R

object Notifications {
    private const val CHANNEL_ID = "quest_life_channel"
    private const val LEVEL_UP_NOTIFICATION_ID = 1
    private const val STREAK_NOTIFICATION_ID = 2
    private const val REMINDER_NOTIFICATION_ID = 3

    // Initialize notification channel
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "QuestLife Notifications"
            val descriptionText = "Notifications for your quests and achievements"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Level up notification
    fun showLevelUpNotification(context: Context, newLevel: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_level_up)
            .setContentTitle("Level Up!")
            .setContentText("Congratulations! You've reached level $newLevel!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(LEVEL_UP_NOTIFICATION_ID, notification)
    }

    // Streak notification
    fun showStreakNotification(context: Context, streakDays: Int, bonus: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_streak)
            .setContentTitle("$streakDays Day Streak!")
            .setContentText("You earned a $bonus coin bonus! Keep it up!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(STREAK_NOTIFICATION_ID, notification)
    }

    // Habit reminder notification
    fun showHabitReminderNotification(context: Context, habitTitle: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_reminder)
            .setContentTitle("Habit Reminder")
            .setContentText("Don't forget to complete '$habitTitle' today!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(REMINDER_NOTIFICATION_ID, notification)
    }
}
