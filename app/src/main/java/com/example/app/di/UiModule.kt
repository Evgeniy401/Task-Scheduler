package com.example.app.di

import com.example.app.mapping.TaskDomainUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Singleton
    @Provides
    fun provideTaskDomainUiMapper(): TaskDomainUiMapper {
        return TaskDomainUiMapper()
    }
}