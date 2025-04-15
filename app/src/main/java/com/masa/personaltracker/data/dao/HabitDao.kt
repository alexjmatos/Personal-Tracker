package com.masa.personaltracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.masa.personaltracker.data.models.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY createdAt DESC")
    fun getAllHabits(): LiveData<List<Habit>>

    // Add Flow-based queries for Compose
    @Query("SELECT * FROM habits ORDER BY createdAt DESC")
    fun getAllHabitsFlow(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: Int): LiveData<Habit>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitByIdFlow(habitId: Int): Flow<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit): Long

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("UPDATE habits SET streakCount = streakCount + 1 WHERE id = :habitId")
    suspend fun incrementStreak(habitId: Int)

    @Query("UPDATE habits SET streakCount = 0 WHERE id = :habitId")
    suspend fun resetStreak(habitId: Int)
}