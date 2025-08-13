package com.lmar.planuraapp.domain.usecase

import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SyncNoteUseCase(
    private val repository: INoteRepository,
    private val scope: CoroutineScope
) {
    operator fun invoke() {
        scope.launch {
            repository.syncOnce()
        }
    }
}