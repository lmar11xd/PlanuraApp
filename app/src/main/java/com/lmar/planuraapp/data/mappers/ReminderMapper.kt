package com.lmar.planuraapp.data.mappers

import com.lmar.planuraapp.data.local.entity.ReminderEntity
import com.lmar.planuraapp.data.remote.dto.ReminderDto
import com.lmar.planuraapp.domain.model.Reminder
import com.lmar.planuraapp.domain.model.ReminderType

fun ReminderEntity.toDomain() = Reminder(
    id,
    description,
    dueDate,
    dueDate,
    ReminderType.valueOf(reminderType),
    createdAt,
    updatedAt,
    isDeleted
)

fun Reminder.toEntity() =
    ReminderEntity(id, description, date, reminderType.name, createdAt, updatedAt, isDeleted)

fun ReminderDto.toDomain() = Reminder(
    id,
    content,
    dueDate,
    dueDate,
    ReminderType.valueOf(reminderType),
    createdAt,
    updatedAt,
    isDeleted
)

fun Reminder.toDto() =
    ReminderDto(id, description, date, reminderType.name, createdAt, updatedAt, isDeleted)