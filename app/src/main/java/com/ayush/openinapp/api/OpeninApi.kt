package com.ayush.openinapp.api

import com.ayush.openinapp.data.model.DashboardResponse
import retrofit2.Response
import retrofit2.http.GET

interface OpeninApi {

    @GET("api/v1/dashboardNew")
    suspend fun dashboardApi(): Response<DashboardResponse>
}