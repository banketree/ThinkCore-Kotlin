package com.thinkcore.kotlin

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


inline fun String.getYearByYYMMDD(): Int? {
    try {
        var dateTime = SimpleDateFormat("yyyy-MM-dd").parse(this)
        val calendar = Calendar.getInstance();
        calendar.time = dateTime
        return calendar.get(Calendar.YEAR)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return null
}

inline fun String.getMonthByYYMMDD(): Int? {
    try {
        var dateTime = SimpleDateFormat("yyyy-MM-dd").parse(this)
        val calendar = Calendar.getInstance();
        calendar.time = dateTime
        return calendar.get(Calendar.MONTH) + 1
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return null
}

inline fun String.getDayByYYMMDD(): Int? {
    try {
        var dateTime = SimpleDateFormat("yyyy-MM-dd").parse(this)
        val calendar = Calendar.getInstance();
        calendar.time = dateTime
        return calendar.get(Calendar.DAY_OF_MONTH)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return null
}

inline fun String.insertSeparator(startIndex: Int, value: String): String {
    if (this.length < startIndex) return this
    return this.substring(0, startIndex) + value + this.substring(startIndex + 1)
}