package com.lmar.planuraapp.data.remote.service

data class NoteDto(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val color: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val isDeleted: Boolean = false
)