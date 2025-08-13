package com.lmar.planuraapp.core.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.lmar.planuraapp.core.utils.Constants
import com.lmar.planuraapp.domain.repository.common.IAuthRepository
import com.lmar.planuraapp.domain.repository.common.IUserRepository
import com.lmar.planuraapp.data.common.FirebaseAuthRepository
import com.lmar.planuraapp.data.common.FirebaseUserRepository
import com.lmar.planuraapp.data.local.dao.NoteDao
import com.lmar.planuraapp.data.remote.dto.FirebaseNoteService
import com.lmar.planuraapp.data.repository.NoteRepositoryImpl
import com.lmar.planuraapp.domain.repository.INoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Auth Instance
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    // Storage
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    // Refs

    @Provides
    @Singleton
    @Named("UserDatabaseRef")
    fun provideUserDatabaseRef(): DatabaseReference {
        return FirebaseDatabase.getInstance()
            .getReference(Constants.USERS_DATABASE)
    }

    @Provides
    @Singleton
    @Named("NoteDatabaseRef")
    fun provideNoteDatabaseRef(): DatabaseReference {
        return FirebaseDatabase.getInstance()
            .getReference("${Constants.APP_DATABASE}/${Constants.NOTES_REFERENCE}")
    }

    @Provides
    @Singleton
    @Named("TaskDatabaseRef")
    fun provideTaskDatabaseRef(): DatabaseReference {
        return FirebaseDatabase.getInstance()
            .getReference("${Constants.APP_DATABASE}/${Constants.TASKS_REFERENCE}")
    }

    @Provides
    @Singleton
    @Named("ReminderDatabaseRef")
    fun provideReminderDatabaseRef(): DatabaseReference {
        return FirebaseDatabase.getInstance()
            .getReference("${Constants.APP_DATABASE}/${Constants.REMINDERS_REFERENCE}")
    }

    // Repositories

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): IAuthRepository {
        return FirebaseAuthRepository(auth)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        @Named("UserDatabaseRef") database: DatabaseReference,
        storage: FirebaseStorage
    ): IUserRepository {
        return FirebaseUserRepository(database, storage)
    }

    @Provides
    @Singleton
    fun provideNoteService(
        @Named("NoteDatabaseRef") database: DatabaseReference
    ): FirebaseNoteService {
        return FirebaseNoteService(database)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDao: NoteDao,
        firebaseService: FirebaseNoteService
    ): INoteRepository {
        return NoteRepositoryImpl(noteDao, firebaseService)
    }

}