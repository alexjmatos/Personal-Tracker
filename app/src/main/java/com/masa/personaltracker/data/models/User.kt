package com.masa.personaltracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1, // Single user system
    var name: String,
    var level: Int = 1,
    var experience: Int = 0,
    var coins: Int = 0,
    var streakDays: Int = 0,
    var lastCheckIn: Long = 0, // Timestamp
    var avatarResourceId: Int = 0
)