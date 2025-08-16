package com.lmar.planuraapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: String,
    val description: String,
    val dueDate: Long,
    val reminderType: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean = false
)
