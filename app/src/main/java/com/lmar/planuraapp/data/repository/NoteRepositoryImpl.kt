package com.lmar.planuraapp.data.repository

import android.util.Log
import com.lmar.planuraapp.data.local.dao.NoteDao
import com.lmar.planuraapp.data.mappers.toDomain
import com.lmar.planuraapp.data.mappers.toEntity
import com.lmar.planuraapp.data.remote.service.FirebaseNoteService
import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val firebaseService: FirebaseNoteService
) : INoteRepository {

    companion object {
        private const val TAG = "NoteRepositoryImpl"
    }

    init {
        Log.d(TAG, "Inicializando NoteRepositoryImpl")
    }

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map {
            notes -> notes.map { it.toDomain() }
        }.catch { e ->
            Log.e(TAG, "Error al obtener las notas", e)
            emit(emptyList())
        }
    }

    override suspend fun getNoteById(id: String): Note? {
        try {
            return noteDao.getNoteById(id)?.toDomain()
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener la nota por ID", e)
            throw e
        }
    }

    override suspend fun addNote(note: Note) {
        try {
            Log.d(TAG, "Agregando nota: $note")
            noteDao.insertNote(note.toEntity(true))
            //firebaseService.addNote(note.toDto())
        } catch (e: Exception) {
            Log.e(TAG, "Error al agregar la nota", e)
            throw e
        }
    }

    override suspend fun updateNote(note: Note) {
        try {
            Log.d(TAG, "Actualizando nota: $note")
            noteDao.updateNote(note.toEntity())
            //firebaseService.updateNote(note.toDto())
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar la nota", e)
            throw e
        }
    }

    override suspend fun updateNoteFields(note: Note) {
        try {
            Log.d(TAG, "Actualizando campos de la nota: $note")
            noteDao.updateNoteFields(
                note.id,
                note.title,
                note.content,
                note.color,
                note.updatedAt
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar los campos de la nota", e)
            throw e
        }
    }

    override suspend fun deleteNote(noteId: String) {
        try {
            Log.d(TAG, "Eliminando nota con ID: $noteId")
            noteDao.deleteNote(noteId)
            //firebaseService.deleteNote(noteId)
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar la nota", e)
            throw e
        }
    }

    override suspend fun deleteLogicNote(noteId: String) {
        try {
            Log.d(TAG, "Eliminando lógicamente la nota con ID: $noteId")
            noteDao.deleteLogicNote(noteId)
            //firebaseService.deleteLogicNote(noteId)
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar lógicamente la nota", e)
            throw e
        }
    }

    override suspend fun syncOnce() {
        try {
            Log.d(TAG, "Sincronizando una vez")
            val remoteNotes = firebaseService.getNotes()
                .map { it.toDomain() }
                .map { it.toEntity(false) }

            noteDao.replaceNotes(remoteNotes)
        } catch (e: Exception) {
            Log.e(TAG, "Error al sincronizar una vez", e)
            throw e
        }
    }
}