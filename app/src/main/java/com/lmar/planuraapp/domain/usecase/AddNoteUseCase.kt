package com.lmar.planuraapp.domain.usecase

import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddNoteUseCase (
    private val repository: INoteRepository,
    private val scope: CoroutineScope
) {
    operator fun invoke(
        note: Note
    ) {
        scope.launch {
            repository.addNote(note)
        }
    }
}