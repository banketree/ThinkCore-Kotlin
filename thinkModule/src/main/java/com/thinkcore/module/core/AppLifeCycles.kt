package com.thinkcore.module.core

import android.app.Application
import android.content.Context

/***
 * Application
 * 生命周期接口
 */

interface AppLifeCycles {

    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}