package com.lmar.planuraapp.core.di

import com.lmar.planuraapp.data.local.PlanuraDatabase
import com.lmar.planuraapp.data.local.dao.ReminderDao
import com.lmar.planuraapp.data.repository.ReminderRepositoryImpl
import com.lmar.planuraapp.domain.repository.IReminderRepository
import com.lmar.planuraapp.domain.usecase.reminder.AddReminderUseCase
import com.lmar.planuraapp.domain.usecase.reminder.DeleteLogicReminderUseCase
import com.lmar.planuraapp.domain.usecase.reminder.DeleteReminderUseCase
import com.lmar.planuraapp.domain.usecase.reminder.GetReminderByIdUseCase
import com.lmar.planuraapp.domain.usecase.reminder.GetRemindersUseCase
import com.lmar.planuraapp.domain.usecase.reminder.UpdateReminderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReminderModule {

    @Provides
    @Singleton
    fun provideReminderDao(database: PlanuraDatabase): ReminderDao {
        return database.reminderDao()
    }

    @Provides
    @Singleton
    fun provideReminderRepository(
        reminderDao: ReminderDao
    ): IReminderRepository {
        return ReminderRepositoryImpl(reminderDao)
    }

    @Provides
    @Singleton
    fun provideGetRemindersUseCase(
        repository: IReminderRepository
    ): GetRemindersUseCase {
        return GetRemindersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetReminderByIdUseCase(
        repository: IReminderRepository
    ): GetReminderByIdUseCase {
        return GetReminderByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddReminderUseCase(
        repository: IReminderRepository
    ): AddReminderUseCase {
        return AddReminderUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateReminderUseCase(
        repository: IReminderRepository
    ): UpdateReminderUseCase {
        return UpdateReminderUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteReminderUseCase(
        repository: IReminderRepository
    ): DeleteReminderUseCase {
        return DeleteReminderUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteLogicReminderUseCase(
        repository: IReminderRepository
    ): DeleteLogicReminderUseCase {
        return DeleteLogicReminderUseCase(repository)
    }
}