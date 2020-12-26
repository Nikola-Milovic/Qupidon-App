package com.nikolam.qupidon

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.nikolam.audiobookmate.di.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber

class QupidonApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@QupidonApplication)
        }

        loadKoinModules(listOf(navigationModule))
        // This will initialise Timber
        // This will initialise Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}