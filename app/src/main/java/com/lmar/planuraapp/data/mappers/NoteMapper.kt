package com.lmar.planuraapp.data.mappers

import com.lmar.planuraapp.data.local.entity.NoteEntity
import com.lmar.planuraapp.data.remote.dto.NoteDto
import com.lmar.planuraapp.domain.model.Note

fun NoteEntity.toDomain() = Note(id, title, content, color, createdAt, updatedAt, isDeleted)
fun Note.toEntity(pendingSync: Boolean = true) = NoteEntity(id, title, content, color, createdAt, updatedAt, isDeleted, pendingSync)
fun NoteDto.toDomain() = Note(id, title, content, color, createdAt, updatedAt, isDeleted)
fun Note.toDto() = NoteDto(id, title, content, color, createdAt, updatedAt, isDeleted)