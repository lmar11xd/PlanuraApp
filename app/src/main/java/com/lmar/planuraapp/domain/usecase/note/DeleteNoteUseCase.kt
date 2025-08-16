package com.lmar.planuraapp.domain.usecase.note

import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteNoteUseCase (
    private val repository: INoteRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    operator fun invoke(
        noteId: String,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                repository.deleteNote(noteId)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}