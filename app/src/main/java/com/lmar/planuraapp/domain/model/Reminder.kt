package com.lmar.planuraapp.domain.model

data class Reminder (
    val id: String,
    val description: String,
    val date: Long, // epoch ms
    val time: Long, // epoch ms
    val reminderType: ReminderType,
    val createdAt: Long, // epoch ms
    val updatedAt: Long, // epoch ms
    val isDeleted: Boolean = false
)

enum class ReminderType {
    ONE_TIME, DAILY, WORKING_DAY, WEEKLY, MONTHLY, YEARLY
}