package com.example.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.storage.PriorityData

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String,
    val priority: PriorityData,
    val isCompleted: Boolean = false,
    val isDeleted: Boolean = false,
    val lastModified: Long = System.currentTimeMillis()
)

