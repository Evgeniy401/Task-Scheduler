package com.example.data.di

import com.example.data.storage.TaskStorage
import com.example.data.storage.TaskStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    @Singleton
    abstract fun bindTaskStorage(
        taskStorageImpl: TaskStorageImpl
    ): TaskStorage

}