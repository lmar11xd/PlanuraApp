package com.lmar.planuraapp.data.remote.dto

data class NoteDto(
    val id: String = "",
    val title: String? = "",
    val content: String? = "",
    val color: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val isDeleted: Boolean = false
)