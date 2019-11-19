package com.thinkcore.log

import android.content.Context

interface ILogger {
    fun v(tag: String, message: String)

    fun d(tag: String, message: String)

    fun i(tag: String, message: String)

    fun w(tag: String, message: String)

    fun e(tag: String, message: String)

    fun open(context: Context)

    fun close()

    fun println(priority: Int, tag: String, message: String)
}
