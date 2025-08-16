package com.lmar.planuraapp.data.mappers

import com.lmar.planuraapp.data.local.entity.TaskEntity
import com.lmar.planuraapp.data.remote.dto.TaskDto
import com.lmar.planuraapp.domain.model.Task

fun TaskEntity.toDomain() = Task(id, description, isCompleted, createdAt, updatedAt, isDeleted)
fun Task.toEntity(pendingSync: Boolean = true) =
    TaskEntity(id, description, isCompleted, createdAt, updatedAt, isDeleted)

fun TaskDto.toDomain() = Task(id, description, isCompleted, createdAt, updatedAt, isDeleted)
fun Task.toDto() = TaskDto(id, description, isCompleted, createdAt, updatedAt, isDeleted)