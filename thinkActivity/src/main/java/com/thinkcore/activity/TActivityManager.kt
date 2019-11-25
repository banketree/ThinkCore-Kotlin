package com.thinkcore.activity

import android.app.Activity
import java.util.*

//程序管理
open class TActivityManager private constructor() {
    private val activityStack = Stack<Activity>()// 进行Activity运行记录

    val sizeOfActivityStack: Int
        get() = activityStack?.size ?: 0

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        if (activityStack.isEmpty()) return null
        return activityStack.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        if (activityStack.isEmpty()) return
        val activity = activityStack.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            synchronized(TActivityManager::class.java) {
                if (activityStack.contains(activity))
                    activityStack.remove(activity)
                activity?.finish()
                activity = null
            }
        }
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            synchronized(TActivityManager::class.java) {
                if (activityStack.contains(activity)) {
                    activityStack.remove(activity)
                    activity = null
                }
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity::class.java == cls) {
                finishActivity(activity)
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        if (activityStack == null || activityStack.isEmpty()) return
        synchronized(TActivityManager::class.java) {
            val iterator = activityStack.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                iterator.remove()
                next.finish()
            }
        }

        activityStack.clear()
    }

    /**
     * 结束指定类名的Activity
     */
    fun hasActivity(cls: Class<*>): Boolean {
        if (activityStack == null || activityStack.isEmpty()) return false
        for (activity in activityStack) {
            if (activity::class.java == cls) {
                return true
            }
        }
        return false
    }

    /**
     * 退出应用程序
     *
     *
     * 此方法经测试在某些机型上并不能完全杀死 App 进程, 几乎试过市面上大部分杀死进程的方式, 但都发现没卵用, 所以此
     * 方法如果不能百分之百保证能杀死进程, 就不能贸然调用 [.release] 释放资源, 否则会造成其他问题, 如果您
     * 有测试通过的并能适用于绝大多数机型的杀死进程的方式, 望告知
     */
    fun appExit() {
        try {
            finishAllActivity()
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        /**
         * 单一实例
         */
        private var instance: TActivityManager? = null
            get() {
                if (field == null) {
                    field = TActivityManager()
                }
                return field
            }

        @Synchronized
        fun get(): TActivityManager {
            return instance!!
        }
    }
}