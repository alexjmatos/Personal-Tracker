package com.masa.personaltracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var dueDate: Long?, // Timestamp, nullable
    var priority: Int, // 1-3 (Low, Medium, High)
    var completed: Boolean = false,
    var experienceReward: Int,
    var coinReward: Int,
    var createdAt: Long = System.currentTimeMillis(),
    var category: String = "General"
)