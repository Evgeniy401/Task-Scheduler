package com.example.data.storage.entity

data class TaskRemoteEntity(
        val id: Int? = null,
        val title: String,
        val body: String,
        val priority: String,
        val isCompleted: Boolean,
        val createdAt: String? = null,
        val updatedAt: String? = null
)
