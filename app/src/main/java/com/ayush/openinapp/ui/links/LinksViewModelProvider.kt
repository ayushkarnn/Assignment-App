package com.ayush.openinapp.ui.links

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ayush.openinapp.data.model.DashboardRepository

class LinksViewModelProvider(private val application: Application):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LinkViewModel::class.java)) {
            return LinkViewModel(
                dashboardRepository = DashboardRepository(
                ), application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}