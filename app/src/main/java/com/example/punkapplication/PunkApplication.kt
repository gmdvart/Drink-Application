package com.example.punkapplication

import android.app.Application
import com.example.punkapplication.di.AppModule
import com.example.punkapplication.di.PunkAppModule

class PunkApplication : Application() {

    private lateinit var _module: AppModule
    val module
        get() = _module

    override fun onCreate() {
        super.onCreate()
        _module = PunkAppModule(this)
    }
}