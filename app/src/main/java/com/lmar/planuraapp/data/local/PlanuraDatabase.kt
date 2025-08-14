package com.lmar.planuraapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lmar.planuraapp.data.local.dao.NoteDao
import com.lmar.planuraapp.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], exportSchema = false, version = 2)
abstract class PlanuraDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}