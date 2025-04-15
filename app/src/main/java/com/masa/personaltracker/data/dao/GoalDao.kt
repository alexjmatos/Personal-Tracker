package com.masa.personaltracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.masa.personaltracker.data.models.Goal

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals WHERE completed = 0 ORDER BY targetDate ASC")
    fun getActiveGoals(): LiveData<List<Goal>>

    @Query("SELECT * FROM goals WHERE completed = 1 ORDER BY createdAt DESC")
    fun getCompletedGoals(): LiveData<List<Goal>>

    @Query("SELECT * FROM goals WHERE id = :goalId")
    fun getGoalById(goalId: Int): LiveData<Goal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal): Long

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("UPDATE goals SET progress = :progress WHERE id = :goalId")
    suspend fun updateProgress(goalId: Int, progress: Int)

    @Query("UPDATE goals SET completed = 1 WHERE id = :goalId")
    suspend fun completeGoal(goalId: Int)
}