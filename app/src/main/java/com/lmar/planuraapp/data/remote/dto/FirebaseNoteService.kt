package com.lmar.planuraapp.data.remote.dto

import com.google.firebase.database.DatabaseReference
import com.lmar.planuraapp.data.remote.service.NoteDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseNoteService @Inject constructor(
    private val db: DatabaseReference
) {
    suspend fun getNotes(): List<NoteDto> {
        val snapshot = db.get().await()
        return snapshot.children.mapNotNull {
            it.getValue(NoteDto::class.java)?.copy(id = it.key ?: "")
        }
    }

    suspend fun addNote(note: NoteDto) {
        val newRef = db.push()
        val noteWithId = note.copy(id = newRef.key ?: "")
        newRef.setValue(noteWithId).await()
    }

    suspend fun updateNote(note: NoteDto) {
        if (note.id.isNotEmpty()) {
            db.child(note.id).setValue(note).await()
        }
    }

    suspend fun deleteNote(noteId: String) {
        db.child(noteId).removeValue().await()
    }
}