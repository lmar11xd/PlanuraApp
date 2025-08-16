package com.lmar.planuraapp.domain.model

data class Task (
    val id: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Long, // epoch ms
    val updatedAt: Long, // epoch ms
    val isDeleted: Boolean = false
)