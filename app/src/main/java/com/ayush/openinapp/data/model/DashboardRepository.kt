package com.ayush.openinapp.data.model

import com.ayush.openinapp.api.RetrofitInstance

class DashboardRepository {

    suspend fun getDashboardApiResponse() =
        RetrofitInstance.api.dashboardApi()
}