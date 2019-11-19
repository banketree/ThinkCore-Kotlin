package com.thinkcore.log

import android.content.Context
import android.text.TextUtils
import android.util.Log


//打印到LogCat上面的日志类
class PrintToLogCatLogger : ILogger {
    override fun d(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        Log.e(tag, message)
    }

    override fun i(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        Log.i(tag, message)
    }

    override fun v(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        Log.v(tag, message)
    }

    override fun w(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        Log.w(tag, message)
    }

    override fun println(priority: Int, tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        Log.println(priority, tag, message)
    }

    override fun open(context: Context) {}

    override fun close() {}
}
