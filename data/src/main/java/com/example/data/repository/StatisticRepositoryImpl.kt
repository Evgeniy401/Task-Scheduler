package com.example.data.repository

import com.example.data.mapping.StatisticMapper
import com.example.data.storage.dao.StatisticDao
import com.example.domain.model.StatisticDomainModel
import javax.inject.Inject
import javax.inject.Singleton
import com.example.domain.repository.StatisticRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class StatisticRepositoryImpl @Inject constructor(
    private val statisticDao: StatisticDao
) : StatisticRepository {

    override suspend fun initializeStatistics() {
        statisticDao.initializeIfNeeded()
    }

    override suspend fun incrementTotalCreated() {
        statisticDao.incrementTotalCreated()
    }

    override suspend fun incrementTotalCompleted() {
        statisticDao.incrementTotalCompleted()
    }

    override suspend fun incrementTotalDeleted() {
        statisticDao.incrementTotalDeleted()
    }

    override fun getTotalCreated(): Flow<Int> {
        return statisticDao.getTotalCreated()
    }

    override fun getTotalCompleted(): Flow<Int> {
        return statisticDao.getTotalCompleted()
    }

    override fun getTotalDeleted(): Flow<Int> {
        return statisticDao.getTotalDeleted()
    }

    override fun getStatistic(): Flow<StatisticDomainModel?> {
        return statisticDao.getStatistic()
            .map { entity ->
                entity?.let { StatisticMapper.toDomain(it) }
            }
    }

    override suspend fun resetStatistics() {
        statisticDao.resetStatistics()
    }
}
