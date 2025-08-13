package com.lmar.planuraapp.domain.usecase

import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteNoteUseCase (
    private val repository: INoteRepository,
    private val scope: CoroutineScope
) {
    operator fun invoke(
        noteId: String
    ) {
        scope.launch {
            repository.deleteNote(noteId)
        }
    }
}