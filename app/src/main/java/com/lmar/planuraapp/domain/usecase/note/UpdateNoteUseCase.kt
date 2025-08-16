package com.lmar.planuraapp.domain.usecase.note

import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateNoteUseCase (
    private val repository: INoteRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    operator fun invoke(
        note: Note,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                repository.updateNoteFields(note)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}