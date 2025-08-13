package com.lmar.planuraapp.domain.usecase

import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.CoroutineScope

class GetNoteByIdUseCase (
    private val repository: INoteRepository,
    private val scope: CoroutineScope
) {
    suspend operator fun invoke(noteId: String): Note? {
        return repository.getNoteById(noteId)
    }
}