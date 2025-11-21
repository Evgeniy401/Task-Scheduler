package com.example.app.di

import com.example.data.repository.TaskRepositoryImpl
import com.example.data.storage.TaskStorage
import com.example.data.storage.TaskStorageImpl
import com.example.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object  DataModule {

    @Singleton
    @Provides
    fun provideTaskStorage(): TaskStorage {
        return TaskStorageImpl()
    }

    @Singleton
    @Provides
    fun provideTaskRepository(taskStorage: TaskStorage): TaskRepository {
        return TaskRepositoryImpl(taskStorage = taskStorage)
    }
}