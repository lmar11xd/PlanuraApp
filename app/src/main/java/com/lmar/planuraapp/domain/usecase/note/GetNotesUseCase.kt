package com.lmar.planuraapp.domain.usecase.note

import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase (
    private val repository: INoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes()
    }
}