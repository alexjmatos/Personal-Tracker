package com.masa.personaltracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.masa.personaltracker.data.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = 1")
    fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE users SET experience = experience + :amount WHERE id = 1")
    suspend fun addExperience(amount: Int)

    @Query("UPDATE users SET coins = coins + :amount WHERE id = 1")
    suspend fun addCoins(amount: Int)

    @Query("UPDATE users SET streakDays = streakDays + 1, lastCheckIn = :timestamp WHERE id = 1")
    suspend fun incrementStreak(timestamp: Long)

    @Query("UPDATE users SET streakDays = 0, lastCheckIn = :timestamp WHERE id = 1")
    suspend fun resetStreak(timestamp: Long)
}