package com.example.data.network

import com.example.data.storage.entity.TaskRemoteEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {
    @GET("tasks")
    suspend fun getAllTasks(): List<TaskRemoteEntity>

    @POST("tasks")
    suspend fun createTask(@Body task: TaskRemoteEntity): TaskRemoteEntity

    @PUT("tasks/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body task: TaskRemoteEntity): TaskRemoteEntity

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}