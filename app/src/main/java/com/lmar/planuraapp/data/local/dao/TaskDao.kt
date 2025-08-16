package com.lmar.planuraapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lmar.planuraapp.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY updatedAt DESC")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query(
        """
        UPDATE tasks 
        SET description = :description, 
            isCompleted = :isCompleted, 
            updatedAt = :updatedAt
        WHERE id = :id
    """
    )
    suspend fun updateTaskFields(
        id: String,
        description: String,
        isCompleted: Boolean,
        updatedAt: Long
    )

    @Query(
        """
        UPDATE tasks 
        SET isCompleted = :isCompleted, 
            updatedAt = :updatedAt 
        WHERE id = :taskId
        """
    )
    suspend fun updateTaskCompleted(taskId: String, isCompleted: Boolean, updatedAt: Long)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: String)

}