package com.lmar.planuraapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lmar.planuraapp.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId LIMIT 1")
    suspend fun getNoteById(noteId: String): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("""
        UPDATE notes 
        SET title = :title, 
            content = :content, 
            color = :color, 
            updatedAt = :updatedAt, 
            pendingSync = 1
        WHERE id = :id
    """)
    suspend fun updateNoteFields(
        id: String,
        title: String?,
        content: String?,
        color: String,
        updatedAt: Long
    )

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNote(noteId: String)

    // Inserción de múltiples notas
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertNotes(notes: List<NoteEntity>)

    // Borrar todas las notas
    @Query("DELETE FROM notes")
    suspend fun clearNotes()

    @Transaction
    suspend fun replaceNotes(notes: List<NoteEntity>) {
        clearNotes()
        insertNotes(notes)
    }

    @Query("SELECT * FROM notes WHERE pendingSync = 1")
    suspend fun getPendingSync(): List<NoteEntity>
}