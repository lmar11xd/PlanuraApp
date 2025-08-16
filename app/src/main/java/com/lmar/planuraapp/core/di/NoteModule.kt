package com.lmar.planuraapp.core.di

import com.google.firebase.database.DatabaseReference
import com.lmar.planuraapp.data.local.PlanuraDatabase
import com.lmar.planuraapp.data.local.dao.NoteDao
import com.lmar.planuraapp.data.remote.service.FirebaseNoteService
import com.lmar.planuraapp.data.repository.NoteRepositoryImpl
import com.lmar.planuraapp.domain.repository.INoteRepository
import com.lmar.planuraapp.domain.usecase.note.AddNoteUseCase
import com.lmar.planuraapp.domain.usecase.note.GetNoteByIdUseCase
import com.lmar.planuraapp.domain.usecase.note.GetNotesUseCase
import com.lmar.planuraapp.domain.usecase.note.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDao(database: PlanuraDatabase): NoteDao {
        return database.noteDao()
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

    @Provides
    @Singleton
    fun provideGetNotesUseCase(
        repository: INoteRepository
    ): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNoteByIdUseCase(
        repository: INoteRepository
    ): GetNoteByIdUseCase {
        return GetNoteByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddNoteUseCase(
        repository: INoteRepository
    ): AddNoteUseCase {
        return AddNoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateNotesUseCase(
        repository: INoteRepository
    ): UpdateNoteUseCase {
        return UpdateNoteUseCase(repository)
    }

}