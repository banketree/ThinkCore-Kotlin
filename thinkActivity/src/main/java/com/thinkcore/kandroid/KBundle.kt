package com.thinkcore.kandroid

import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.putParcelableCollection(key: String, value: Collection<T>) = putParcelableArray(key, value.toTypedArray())

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Parcelable> Bundle.getParcelableMutableList(key: String): MutableList<T> = (getParcelableArray(key) as Array<T>).toMutableList()

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Parcelable> Bundle.getParcelableMutableSet(key: String): MutableSet<T> = (getParcelableArray(key) as Array<T>).toMutableSet()

// 获取所有的 intent extra 数据
inline fun Bundle.getExtraString(): String {
    val resultString = StringBuilder()
    for (key in this.keySet()) {
        resultString.append("\nkey:" + key.toString() + ", value:" + get(key))
    }

    return resultString.toString()
}