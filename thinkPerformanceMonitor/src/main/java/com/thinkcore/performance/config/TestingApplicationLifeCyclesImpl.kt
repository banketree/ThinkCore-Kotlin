package com.thinkcore.performance.config

import android.app.Application
import android.content.Context
import leakcanary.AppWatcher

import timber.log.Timber
import com.github.moduth.blockcanary.BlockCanary
import com.thinkcore.module.core.AppLifeCycles
import com.thinkcore.performance.AppBlockCanaryContext


internal class TestingApplicationLifeCyclesImpl :
    AppLifeCycles {
    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
        Timber.i("性能检测-> 泄漏检测 + 卡顿检测")
        AppWatcher.config = AppWatcher.config.copy()
        BlockCanary.install(application,
            AppBlockCanaryContext()
        ).start()
    }

    override fun onTerminate(application: Application) {
    }
}