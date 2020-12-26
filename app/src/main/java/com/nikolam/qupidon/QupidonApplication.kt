package com.nikolam.qupidon

import android.app.Application
import com.nikolam.qupidon.di.facebookModule
import com.nikolam.qupidon.di.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class QupidonApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@QupidonApplication)
        }

        loadKoinModules(listOf(navigationModule, facebookModule))
        // This will initialise Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(object : DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "qupidon_$tag", message, t)
                }
            })
        }
    }


}