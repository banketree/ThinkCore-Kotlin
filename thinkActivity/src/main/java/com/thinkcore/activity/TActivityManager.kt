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
            if (activityStack.contains(activity))
                activityStack.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity?) {
        var activity = activity
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity)
            activity = null
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
        var i = 0
        val size = activityStack.size
        while (i < size) {
            if (null != activityStack[i]) {
                activityStack[i].finish()
            }
            i++
        }
        activityStack.clear()
    }

    /**
     * 结束指定类名的Activity
     */
    fun hasActivity(cls: Class<*>): Boolean {
        for (activity in activityStack) {
            if (activity::class.java == cls) {
                return true
            }
        }
        return false
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