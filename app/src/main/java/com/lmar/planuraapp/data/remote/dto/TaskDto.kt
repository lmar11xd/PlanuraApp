package com.lmar.planuraapp.data.remote.dto

data class TaskDto(
    val id: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val isDeleted: Boolean = false
)
