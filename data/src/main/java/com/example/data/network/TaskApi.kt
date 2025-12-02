package com.example.data.network

import com.example.data.storage.entity.CreateTaskDto
import com.example.data.storage.entity.GetTaskDto
import com.example.data.storage.entity.UpdateTaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {
    @GET("tasks")
    suspend fun getAllTasks(): List<GetTaskDto>

    @POST("tasks")
    suspend fun createTask(@Body task: CreateTaskDto): GetTaskDto

    @PUT("tasks/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body task: UpdateTaskDto): GetTaskDto

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}