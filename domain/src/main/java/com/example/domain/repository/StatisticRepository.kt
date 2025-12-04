package com.example.domain.repository

import com.example.domain.model.StatisticDomainModel
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
    suspend fun initializeStatistics()
    suspend fun incrementTotalCreated()
    suspend fun incrementTotalCompleted()
    suspend fun incrementTotalDeleted()
    fun getTotalCreated(): Flow<Int>
    fun getTotalCompleted(): Flow<Int>
    fun getTotalDeleted(): Flow<Int>
    suspend fun resetStatistics()
    fun getStatistic(): Flow<StatisticDomainModel?>
}
