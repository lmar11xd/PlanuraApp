package com.lmar.planuraapp.domain.usecase.note

import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.domain.repository.INoteRepository

class GetNoteByIdUseCase (
    private val repository: INoteRepository
) {
    suspend operator fun invoke(noteId: String): Note? {
        return try {
            repository.getNoteById(noteId)
        } catch (_: Exception) {
            null
        }
    }
}