package com.example.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statistics")
data class StatisticEntity(
    @PrimaryKey
    val id: Int = 1,
    val totalCreated: Int = 0,
    val totalCompleted: Int = 0,
    val totalDeleted: Int = 0
)
