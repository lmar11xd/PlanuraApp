package com.lmar.planuraapp.core.di

import com.lmar.planuraapp.data.local.PlanuraDatabase
import com.lmar.planuraapp.data.local.dao.TaskDao
import com.lmar.planuraapp.data.repository.TaskRepositoryImpl
import com.lmar.planuraapp.domain.repository.ITaskRepository
import com.lmar.planuraapp.domain.usecase.task.AddTaskUseCase
import com.lmar.planuraapp.domain.usecase.task.DeleteTaskUseCase
import com.lmar.planuraapp.domain.usecase.task.GetTaskByIdUseCase
import com.lmar.planuraapp.domain.usecase.task.GetTasksUseCase
import com.lmar.planuraapp.domain.usecase.task.UpdateTaskCompletedUseCase
import com.lmar.planuraapp.domain.usecase.task.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun provideTaskDao(database: PlanuraDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao
    ): ITaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideGetTasksUseCase(
        repository: ITaskRepository
    ): GetTasksUseCase {
        return GetTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTaskByIdUseCase(
        repository: ITaskRepository
    ): GetTaskByIdUseCase {
        return GetTaskByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(
        repository: ITaskRepository
    ): AddTaskUseCase {
        return AddTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskUseCase(
        repository: ITaskRepository
    ): UpdateTaskUseCase {
        return UpdateTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskCompletedUseCase(
        repository: ITaskRepository
    ): UpdateTaskCompletedUseCase {
        return UpdateTaskCompletedUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(
        repository: ITaskRepository
    ): DeleteTaskUseCase {
        return DeleteTaskUseCase(repository)
    }
}