package com.ayush.openinapp

import android.app.Application
import android.content.Context

class OpeninAppApplication:Application() {

    companion object{
        var appContext: Context? = null
    }
}