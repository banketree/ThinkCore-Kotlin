package com.thinkcore.module.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/***
 * Application/Activity/Fragment
 * 生命周期处理类
 */

class AppDelegate(context: Context) :
    AppLifeCycles {
    private var application: Application? = null
    private var moduleList: List<ConfigModule>? = null
    private var appLifeCycles: MutableList<AppLifeCycles> = arrayListOf()
    private var activityLifeCycles: MutableList<Application.ActivityLifecycleCallbacks> = arrayListOf()
    private var fragmentLifeCycles: MutableList<FragmentManager.FragmentLifecycleCallbacks> = arrayListOf()

    init {
        moduleList = ManifestParser(context).parse()
        moduleList?.forEach {
            it.injectAppLifecycle(context, appLifeCycles)
            it.injectActivityLifecycle(context, activityLifeCycles)
            it.injectFragmentLifecycle(context, fragmentLifeCycles)
        }
    }

    override fun attachBaseContext(base: Context) {
        appLifeCycles.forEach {
            it.attachBaseContext(base)
        }
    }

    override fun onCreate(application: Application) {
        this.application = application
        application?.registerActivityLifecycleCallbacks(FragmentLifecycleCallbacks())
        activityLifeCycles.forEach {
            application?.registerActivityLifecycleCallbacks(it)
        }
        appLifeCycles.forEach {
            if (application != null) {
                it.onCreate(application!!)
            }
        }

    }

    override fun onTerminate(application: Application) {
        activityLifeCycles.forEach {
            application?.unregisterActivityLifecycleCallbacks(it)
        }
        appLifeCycles.forEach {
            if (application != null) {
                it.onTerminate(application!!)
            }
        }
    }

    private inner class FragmentLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            if (activity is FragmentActivity) {
                fragmentLifeCycles.forEach {
                    activity.supportFragmentManager
                        .registerFragmentLifecycleCallbacks(it, true)
                }
            }
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

    }
}