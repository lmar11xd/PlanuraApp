package com.lmar.planuraapp.domain.model

data class Note(
    val id: String,
    val title: String?,
    val content: String?,
    val color: String,
    val createdAt: Long, // epoch ms
    val updatedAt: Long, // epoch ms
    val isDeleted: Boolean = false
)