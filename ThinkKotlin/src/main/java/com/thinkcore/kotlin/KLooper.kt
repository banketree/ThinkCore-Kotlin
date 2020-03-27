package com.thinkcore.kotlin

import android.os.Looper

/**
 * 判断是否在当前主线程
 * @return
 */
inline fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}