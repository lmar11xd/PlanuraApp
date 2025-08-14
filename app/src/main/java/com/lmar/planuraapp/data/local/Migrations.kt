package com.lmar.planuraapp.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 1. Crear tabla temporal con content nullable y title nullable
        db.execSQL("""
            CREATE TABLE notes_new (
                id TEXT NOT NULL PRIMARY KEY,
                title TEXT,
                content TEXT,
                color TEXT NOT NULL,
                createdAt INTEGER NOT NULL,
                updatedAt INTEGER NOT NULL,
                isDeleted INTEGER NOT NULL,
                pendingSync INTEGER NOT NULL
            )
        """)

        // 2. Copiar datos desde la tabla original
        db.execSQL("""
            INSERT INTO notes_new (id, title, content, color, createdAt, updatedAt, isDeleted, pendingSync)
            SELECT id, title, content, color, createdAt, updatedAt, isDeleted, pendingSync
            FROM notes
        """)

        // 3. Eliminar la tabla original
        db.execSQL("DROP TABLE notes")

        // 4. Renombrar la tabla nueva
        db.execSQL("ALTER TABLE notes_new RENAME TO notes")
    }
}