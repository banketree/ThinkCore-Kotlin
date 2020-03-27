@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kotlin

import java.text.SimpleDateFormat
import java.util.*

inline fun toTime(timeFormat: SimpleDateFormat, value: Long): String {
    var result = ""
    try {
        val date = Date()
        date.time = value
        result = timeFormat.format(date)
    } catch (ex: Exception) {
    }
    return result
}

/**
 * 时间转换
 * 1:00:00
 * */
inline fun Number.toTimeForMinSecondByMillisecond(): String {
    return toTime(SimpleDateFormat("mm:ss"), toLong())
}

inline fun Number.toTimeForMinSecond(): String {
    return (toLong() * 1000).toTimeForMinSecondByMillisecond()
}

/**
 * 时间转换
 * 01:00
 * */
inline fun Number.toTimeForHourMinSecondByMillisecond(): String {
    return toTime(SimpleDateFormat("HH:mm:ss"), toLong())
}

/**
 * 时间转换
 * 01:00
 * */
inline fun Number.toTimeForYYMMDDHHMMSS(): String {
    return toTime(SimpleDateFormat("yyyyMMddHHmmssSS"), toLong())
}

/**
 * 时间转换
 * 01:00
 * */
inline fun Number.toTimeForChineseYYMMDDHHMMSS(): String {
    return toTime(SimpleDateFormat("yyyy年M月d日 h时m分s秒"), toLong())
}

/**
 * 时间转换
 * 01:00
 * */
inline fun Number.toTimeForMMDDHHMMByMillisecond(): String {
    return toTime(SimpleDateFormat("MM.dd HH:mm"), toLong())
}

inline fun Number.toTimeForHourMinSecond(): String {
    return (toLong() * 1000).toTimeForHourMinSecondByMillisecond()
}

inline fun Number.toHourTimeByMillisecond(): Int {
    return (toLong() / 1000 / 60 / 60).toInt()
}

inline fun Number.toMinuteTimeByMillisecond(): Int {
    val minSecond = (toLong() / 1000) % 3600
    return (minSecond / 60).toInt()
}

inline fun Number.toSecondTime(): Int {
    val second = (toLong() / 1000) % 60
    return (second).toInt()
}

/**
 * 字节 转换为B MB GB
 * @param type  0 bit 1 kb 2 MB 3 GB
 * @return
 */
inline fun Number.toBitSizeType(type: Int = 0): String {
    when (type) {
//        0 -> {
//        }
        1 -> { //kb
            return "${toLong() / 1024.000}"
        }
        2 -> { //mb
            return "${toLong() / 1024.000 / 1024.000}"
        }
        3 -> { //gb
            return "${toLong() / 1024.000 / 1024.000 / 1024.000}"
        }
        else -> { //b
            return "${toLong()}"
        }
    }
}

inline fun Number.toBitSizeString(): String {
    var size = toLong()
    var rest: Long = 0
    size /= if (size < 1024) {
        return size.toString() + "B"
    } else {
        1024
    }
    if (size < 1024) {
        return size.toString() + "KB"
    } else {
        rest = size % 1024
        size /= 1024
    }
    return if (size < 1024) {
        size *= 100
        (size / 100).toString() + "." + (rest * 100 / 1024 % 100).toString() + "MB"
    } else {
        size = size * 100 / 1024
        (size / 100).toString() + "." + (size % 100).toString() + "GB"
    }
}

/**
 * 距今多少天了
 * */
inline fun Number.getDaysSoFar(): Int {
    val second = (System.currentTimeMillis() - toLong()) / 1000
    val days = second / (24 * 60 * 60)
    return (days).toInt()
}