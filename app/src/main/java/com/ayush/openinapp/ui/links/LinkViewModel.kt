package com.ayush.openinapp.ui.links

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ayush.openinapp.OpeninAppApplication
import com.ayush.openinapp.data.model.DashboardRepository
import com.ayush.openinapp.data.model.DashboardResponse
import com.ayush.openinapp.utill.MutableAppropriateLiveData
import com.ayush.openinapp.utill.Resource
import com.ayush.openinapp.utill.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class LinkViewModel(private val dashboardRepository: DashboardRepository, app: Application):
    AndroidViewModel(app) {

    private val _dashboardResponse = MutableAppropriateLiveData<Resource<DashboardResponse>>()
    val dashboardResponseResult: LiveData<Resource<DashboardResponse>> = _dashboardResponse

    fun getDashboardApiData() {
        viewModelScope.launch {
            safeGetDashboardResponse()
        }
    }
    private suspend fun safeGetDashboardResponse() {
        _dashboardResponse.postValue(Resource.Loading())
        try {
            val connectivityManager = getApplication<OpeninAppApplication>().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            if (hasInternetConnection(connectivityManager)) {
                val response = dashboardRepository.getDashboardApiResponse()
                _dashboardResponse.postValue(handleDashboardApiResponse(response))
            } else {
                _dashboardResponse.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _dashboardResponse.postValue(Resource.Error("Network Failure"))
                else -> _dashboardResponse.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleDashboardApiResponse(response: Response<DashboardResponse>): Resource<DashboardResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}