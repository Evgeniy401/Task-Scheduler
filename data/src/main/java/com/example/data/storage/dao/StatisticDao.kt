package com.example.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.storage.entity.StatisticEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticDao {

    @Query("SELECT * FROM statistics WHERE id = 1")
    fun getStatistic(): Flow<StatisticEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(statistic: StatisticEntity)

    @Query("UPDATE statistics SET totalCreated = totalCreated + 1 WHERE id = 1")
    suspend fun incrementTotalCreated()

    @Query("UPDATE statistics SET totalCompleted = totalCompleted + 1 WHERE id = 1")
    suspend fun incrementTotalCompleted()

    @Query("UPDATE statistics SET totalDeleted = totalDeleted + 1 WHERE id = 1")
    suspend fun incrementTotalDeleted()

    @Query("UPDATE statistics SET totalCreated = 0, totalCompleted = 0, totalDeleted = 0 WHERE id = 1")
    suspend fun resetStatistics()

    @Query("SELECT totalCreated FROM statistics WHERE id = 1")
    fun getTotalCreated(): Flow<Int>

    @Query("SELECT totalCompleted FROM statistics WHERE id = 1")
    fun getTotalCompleted(): Flow<Int>

    @Query("SELECT totalDeleted FROM statistics WHERE id = 1")
    fun getTotalDeleted(): Flow<Int>

    @Transaction
    suspend fun initializeIfNeeded() {
        val existing = getStatisticStatic()
        if (existing == null) {
            insertOrUpdate(StatisticEntity(id = 1))
        }
    }


    @Query("SELECT * FROM statistics WHERE id = 1")
    suspend fun getStatisticStatic(): StatisticEntity?
}