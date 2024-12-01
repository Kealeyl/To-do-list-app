package com.example.to_do

import android.app.Application
import com.example.to_do.data.AppContainer
import com.example.to_do.data.AppDataContainer

class TaskAppApplication : Application()  {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}