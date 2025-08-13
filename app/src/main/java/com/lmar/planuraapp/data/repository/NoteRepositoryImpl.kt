package com.lmar.planuraapp.data.repository

import com.lmar.planuraapp.data.local.dao.NoteDao
import com.lmar.planuraapp.data.mappers.toDomain
import com.lmar.planuraapp.data.mappers.toDto
import com.lmar.planuraapp.data.mappers.toEntity
import com.lmar.planuraapp.data.remote.dto.FirebaseNoteService
import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val firebaseService: FirebaseNoteService
) : INoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map {
            notes -> notes.map { it.toDomain() }
        }
    }

    override suspend fun getNoteById(id: String): Note? =
        noteDao.getNoteById(id)?.toDomain()

    override suspend fun addNote(note: Note) {
        noteDao.insertNote(note.toEntity(true))
        //firebaseService.addNote(note.toDto())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
        //firebaseService.updateNote(note.toDto())
    }

    override suspend fun deleteNote(noteId: String) {
        noteDao.deleteNote(noteId)
        //firebaseService.deleteNote(noteId)
    }

    override suspend fun syncOnce() {
        val remoteNotes = firebaseService.getNotes()
            .map { it.toDomain() }
            .map { it.toEntity(false) }

        noteDao.replaceNotes(remoteNotes)
    }
}