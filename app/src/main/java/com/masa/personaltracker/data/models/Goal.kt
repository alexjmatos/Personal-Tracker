package com.masa.personaltracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var targetDate: Long?, // Timestamp, nullable for open-ended goals
    var progress: Int = 0, // 0-100 (percentage)
    var milestones: String = "", // JSON string of milestone objects
    var experienceReward: Int,
    var coinReward: Int,
    var createdAt: Long = System.currentTimeMillis(),
    var completed: Boolean = false,
    var category: String = "Personal"
)