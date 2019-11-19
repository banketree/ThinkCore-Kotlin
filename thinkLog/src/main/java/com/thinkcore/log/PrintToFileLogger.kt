package com.thinkcore.log

import android.content.Context
import android.text.TextUtils

import com.thinkcore.storage.Storage
import com.thinkcore.storage.TFilePath
import com.thinkcore.storage.TStorageUtils

import java.text.SimpleDateFormat
import java.util.Date

//打印到sdcard上面的日志类
class PrintToFileLogger : ILogger {

    private var nameString = ""
    private var cacheDirName = ""
    private var storage: Storage? = null

    private val currentTimeString: String
        get() {
            var now: Date? = Date()
            var simpleDateFormat: SimpleDateFormat? = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
            )
            val result = simpleDateFormat!!.format(now!!)
            now = null
            simpleDateFormat = null
            return result.replace(":".toRegex(), "-")
        }

    constructor(context: Context) {
        open(context)
    }

    constructor(context: Context, fileName: String) {
        nameString = fileName
        open(context)
    }

    override fun open(context: Context) {
        try {
            if (TextUtils.isEmpty(nameString))
                nameString = "$currentTimeString.log"
            val filePath = TFilePath(context)
            var result = false
            if (TStorageUtils.isExternalStoragePresent()) {
                storage = filePath.externalStorage
            } else {
                storage = filePath.internalStorage
            }

            cacheDirName = filePath.cacheDir
            result = storage!!.createFile(cacheDirName, nameString, "")
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun d(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        println(DEBUG, tag, message)
    }

    override fun e(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        println(ERROR, tag, message)
    }

    override fun i(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        println(INFO, tag, message)
    }

    override fun v(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        println(VERBOSE, tag, message)
    }

    override fun w(tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        println(WARN, tag, message)
    }

    override fun println(priority: Int, tag: String, message: String) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message))
            return
        var printMessage = ""
        when (priority) {
            VERBOSE -> printMessage = ("[V]|"
                    + tag
                    + "|" + message)
            DEBUG -> printMessage = ("[D]|"
                    + tag
                    + "|" + message)
            INFO -> printMessage = ("[I]|"
                    + tag
                    + "|" + message)
            WARN -> printMessage = ("[W]|"
                    + tag
                    + "|" + message)
            ERROR -> printMessage = ("[E]|"
                    + tag
                    + "|" + message)
            else -> {
            }
        }
        println(printMessage)

    }

    fun println(message: String) {
        if (TextUtils.isEmpty(nameString) || !TStorageUtils.isExternalStorageWrittenable())
            return
        try {
            val content = getFullTime(System.currentTimeMillis()) + "=====>" + message
            if (storage != null) {
                if (storage!!.isFileExist(cacheDirName, nameString)) {
                    storage!!.appendFile(
                        cacheDirName,
                        nameString, content
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun close() {
        nameString = ""
    }

    fun getFullTime(time: Long): String {
        var fulTimeFormat: SimpleDateFormat? = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss"
        )
        val strTime: String
        var date: Date? = Date(time)
        strTime = fulTimeFormat!!.format(date!!)
        date = null
        fulTimeFormat = null
        return strTime
    }

    companion object {
        val VERBOSE = 2
        val DEBUG = 3
        val INFO = 4
        val WARN = 5
        val ERROR = 6
        val ASSERT = 7
    }
}
