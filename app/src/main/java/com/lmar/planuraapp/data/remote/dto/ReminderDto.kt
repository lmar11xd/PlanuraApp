package com.lmar.planuraapp.data.remote.dto

data class ReminderDto(
    val id: String = "",
    val content: String = "",
    val dueDate: Long = 0L,
    val reminderType: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val isDeleted: Boolean = false
)