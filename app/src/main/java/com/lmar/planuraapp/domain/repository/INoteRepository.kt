package com.lmar.planuraapp.domain.repository

import com.lmar.planuraapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: String): Note?
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun updateNoteFields(note: Note)
    suspend fun deleteNote(noteId: String)
    suspend fun deleteLogicNote(noteId: String)
    suspend fun syncOnce() // fuerza sincronización inmediata
}