package com.thinkcore.log


import android.content.Context

import java.util.HashMap

//日志打印类
object TLog {
    private val Tag = TLog::class.java.simpleName

    private var IgnoreAll = false
    private var IgnoreInfo = false
    private var IgnoreDebug = false
    private var IgnoreWarn = false
    private var IgnoreError = false

    /**
     * Priority constant for the println method; use TLog.v.
     */
    val VERBOSE = 2

    /**
     * Priority constant for the println method; use TLog.d.
     */
    val DEBUG = 3

    /**
     * Priority constant for the println method; use TLog.i.
     */
    val INFO = 4

    /**
     * Priority constant for the println method; use TLog.w.
     */
    val WARN = 5

    /**
     * Priority constant for the println method; use TLog.e.
     */
    val ERROR = 6
    /**
     * Priority constant for the println method.
     */
    val ASSERT = 7
    private val mLoggerHashMap = HashMap<String, ILogger>()
    private var mDefaultLogger: ILogger? = PrintToLogCatLogger()
    private val mIgnoreTagHashMap = HashMap<String, Boolean>()

    fun enablePrintToFileLogger(context: Context, enable: Boolean) {
        mDefaultLogger = null
        if (enable) {
            mDefaultLogger = PrintToFileLogger(context)
        } else {
            mDefaultLogger = PrintToLogCatLogger()
        }
    }


    fun enablePrintToFileLogger(context: Context, fileName: String) {
        if (mDefaultLogger != null) mDefaultLogger!!.close()
        mDefaultLogger = null
        mDefaultLogger = PrintToFileLogger(context, fileName)
    }

    fun release() {
        mDefaultLogger!!.close()
    }

    fun addLogger(context: Context, logger: ILogger) {
        val loggerName = logger.javaClass.name
        val defaultLoggerName = mDefaultLogger!!.javaClass.name
        if (!mLoggerHashMap.containsKey(loggerName) && !defaultLoggerName.equals(loggerName, ignoreCase = true)) {
            logger.open(context)
            mLoggerHashMap[loggerName] = logger
        }
    }

    fun removeLogger(logger: ILogger) {
        val loggerName = logger.javaClass.name
        if (mLoggerHashMap.containsKey(loggerName)) {
            logger.close()
            mLoggerHashMap.remove(loggerName)
        }
    }

    //object
    fun d(`object`: Any?, message: String, vararg args: Any) {
        printLoger(DEBUG, `object` ?: "", message, *args)
    }

    fun d(`object`: Any?, e: Throwable?, vararg args: Any) {
        printLoger(
            DEBUG, `object` ?: "",
            if (e == null) "" else e.message, *args
        )
    }

    fun e(`object`: Any?, e: Throwable?) {
        printLoger(
            ERROR, `object` ?: "",
            if (e == null) "" else e.message
        )
    }

    fun e(`object`: Any?, message: String, vararg args: Any) {
        printLoger(ERROR, `object` ?: "", message, *args)
    }

    fun e(`object`: Any?, message: String, e: Throwable?) {
        printLoger(
            ERROR, `object` ?: "", message + " "
                    + if (e == null) "" else e.message
        )
    }

    fun i(`object`: Any?, message: String, vararg args: Any) {
        printLoger(INFO, `object` ?: "", message, *args)
    }

    fun i(`object`: Any?, e: Throwable?) {
        printLoger(
            INFO, `object` ?: "",
            if (e == null) "" else e.message
        )
    }

    fun v(`object`: Any?, message: String, vararg args: Any) {
        printLoger(VERBOSE, `object` ?: "", message, *args)
    }

    fun v(`object`: Any?, e: Throwable?) {
        printLoger(
            VERBOSE, `object` ?: "",
            if (e == null) "" else e.message
        )
    }

    fun w(`object`: Any?, message: String, vararg args: Any) {
        printLoger(WARN, `object` ?: "", message, *args)
    }

    fun w(`object`: Any?, e: Throwable?) {
        printLoger(
            WARN, `object` ?: "",
            if (e == null) "" else e.message
        )
    }

    //String
    fun d(tag: String, message: String, vararg args: Any) {
        printLoger(DEBUG, tag, message, *args)
    }

    fun d(tag: String, e: Throwable?) {
        printLoger(DEBUG, tag, if (e == null) "" else e.message)
    }

    fun e(tag: String, message: String, vararg args: Any) {
        printLoger(ERROR, tag, message, *args)
    }

    fun e(tag: String, e: Throwable?) {
        printLoger(ERROR, tag, if (e == null) "" else e.message)
    }

    fun e(tag: String, message: String, e: Throwable?) {
        printLoger(
            ERROR, tag, message + " "
                    + if (e == null) "" else e.message
        )
    }

    fun i(tag: String, message: String, vararg args: Any) {
        printLoger(INFO, tag, message, *args)
    }

    fun i(tag: String, e: Throwable?) {
        printLoger(INFO, tag, if (e == null) "" else e.message)
    }

    fun v(tag: String, message: String, vararg args: Any) {
        printLoger(VERBOSE, tag, message, *args)
    }

    fun v(tag: String, e: Throwable?) {
        printLoger(VERBOSE, tag, if (e == null) "" else e.message)
    }

    fun w(tag: String, message: String, vararg args: Any) {
        printLoger(WARN, tag, message, *args)
    }

    fun w(tag: String, e: Throwable?) {
        printLoger(WARN, tag, if (e == null) "" else e.message)
    }

    //println
    fun println(
        priority: Int, tag: String, message: String,
        vararg args: Any
    ) {
        printLoger(priority, tag, message, *args)
    }

    private fun printLoger(
        priority: Int, `object`: Any, message: String?,
        vararg args: Any
    ) {
        var tag = Tag
        try {
            val cls = `object`.javaClass
            tag = cls.name
            val arrays = tag.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            tag = arrays[arrays.size - 1]
        } catch (e: Exception) {
            tag = Tag
        }

        printLoger(priority, tag, if (args == null || args.size == 0) message else String.format(message!!, *args))
    }

    private fun printLoger(priority: Int, tag: String, message: String?) {
        printLoger(mDefaultLogger, priority, tag, message)
        val iter = mLoggerHashMap.entries
            .iterator()
        while (iter.hasNext()) {
            val entry = iter.next()
            val logger = entry.value
            if (logger != null) {
                printLoger(logger, priority, tag, message)
            }
        }
    }

    private fun printLoger(
        logger: ILogger?, priority: Int, tag: String,
        message: String?
    ) {
        if (!mIgnoreTagHashMap.isEmpty() && mIgnoreTagHashMap.containsKey(tag)) {
            return
        }

        when (priority) {
            VERBOSE -> if (!IgnoreAll)
                logger!!.v(tag, message!!)
            DEBUG -> if (!IgnoreDebug)
                logger!!.d(tag, message!!)
            INFO -> if (!IgnoreInfo)
                logger!!.i(tag, message!!)
            WARN -> if (!IgnoreWarn)
                logger!!.w(tag, message!!)
            ERROR -> if (!IgnoreError)
                logger!!.e(tag, message!!)
            else -> {
            }
        }
    }

    fun ignoreAll(enable: Boolean) {
        IgnoreAll = enable
    }

    fun ignoreInfo(enable: Boolean) {
        IgnoreInfo = enable
    }

    fun ignoreDebug(enable: Boolean) {
        IgnoreDebug = enable
    }

    fun ignoreWarn(enable: Boolean) {
        IgnoreWarn = enable
    }

    fun ignoreError(enable: Boolean) {
        IgnoreError = enable
    }

    fun clearIgnoreTag() {
        mIgnoreTagHashMap.clear()
    }

    @JvmStatic
    fun addIgnoreTag(tags: Array<String>) {
        for (tag in tags) {
            mIgnoreTagHashMap[tag] = true
        }
    }
}
