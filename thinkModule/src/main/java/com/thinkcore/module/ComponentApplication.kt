package com.thinkcore.module

import android.app.Application
import android.content.Context
import com.thinkcore.module.core.AppDelegate

open class ComponentApplication : Application() {
    private var appDelegate: AppDelegate? = null

    override fun onCreate() {
        super.onCreate()
        appDelegate?.onCreate(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (appDelegate == null) {
            appDelegate = AppDelegate(base)
        }
        appDelegate?.attachBaseContext(base)
    }

    override fun onTerminate() {
        super.onTerminate()
        appDelegate?.onTerminate(this)
    }
}