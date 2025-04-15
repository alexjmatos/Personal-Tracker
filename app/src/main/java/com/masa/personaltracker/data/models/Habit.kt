package com.masa.personaltracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var frequencyType: String, // DAILY, WEEKLY
    var frequencyValue: String, // For weekly: "1,3,5" for Mon,Wed,Fri
    var difficultyLevel: Int, // 1-3 (Easy, Medium, Hard)
    var experienceReward: Int,
    var coinReward: Int,
    var streakCount: Int = 0,
    var createdAt: Long = System.currentTimeMillis(),
    var color: Int, // Resource ID for habit color
    var iconResourceId: Int // Resource ID for habit icon
)