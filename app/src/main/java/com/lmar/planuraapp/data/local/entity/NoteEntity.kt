package com.lmar.planuraapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val color: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean = false,
    val pendingSync: Boolean = true // marca para sincronizar
)