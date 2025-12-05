package com.example.data.di

import android.content.Context
import com.example.data.storage.database.TaskDatabase
import com.example.data.storage.dao.StatisticDao
import com.example.data.storage.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {
        return TaskDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        database: TaskDatabase
    ): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideStatisticDao(
        database: TaskDatabase
    ): StatisticDao {
        return database.statisticDao()
    }
}