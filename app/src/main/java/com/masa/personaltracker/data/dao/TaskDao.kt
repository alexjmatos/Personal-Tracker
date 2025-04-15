package com.masa.personaltracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.masa.personaltracker.data.models.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE completed = 0 ORDER BY dueDate ASC, priority DESC")
    fun getPendingTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE completed = 1 ORDER BY createdAt DESC")
    fun getCompletedTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks SET completed = :isCompleted WHERE id = :taskId")
    suspend fun setTaskCompleted(taskId: Int, isCompleted: Boolean)
}