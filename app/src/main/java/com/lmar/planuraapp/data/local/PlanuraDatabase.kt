package com.lmar.planuraapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lmar.planuraapp.data.local.dao.NoteDao
import com.lmar.planuraapp.data.local.dao.ReminderDao
import com.lmar.planuraapp.data.local.dao.TaskDao
import com.lmar.planuraapp.data.local.entity.NoteEntity
import com.lmar.planuraapp.data.local.entity.ReminderEntity
import com.lmar.planuraapp.data.local.entity.TaskEntity

@Database(
    entities = [NoteEntity::class, TaskEntity::class, ReminderEntity::class],
    exportSchema = false,
    version = 3
)
abstract class PlanuraDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
    abstract fun reminderDao(): ReminderDao
}