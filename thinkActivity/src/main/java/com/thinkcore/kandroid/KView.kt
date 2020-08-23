@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kandroid

import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.annotation.IdRes

@Deprecated("Use findViewById() instead", ReplaceWith("findViewById()"))
inline fun <reified T : View> View.find(@IdRes id: Int): T? = findViewById(id)

var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

inline fun View.hide(gone: Boolean = true) {
    visibility = if (gone) GONE else INVISIBLE
}

inline fun View.show() {
    visibility = VISIBLE
}

inline fun View.setWidthAndHeight(width: Int, height: Int) {
    val params = layoutParams
    params.width = width
    params.height = height
    layoutParams = params
}

inline fun View.setWidth(width: Int) {
    val params = layoutParams
    params.width = width
    layoutParams = params
}

inline fun View.setHeight(height: Int) {
    val params = layoutParams
    params.height = height
    layoutParams = params
}

inline fun View.setSize(width: Int, height: Int) {
    val params = layoutParams
    params.width = width
    params.height = height
    layoutParams = params
}

/**
 * 将View从父控件中移除
 */
inline fun View.removeViewFromParent() {
    this.parent ?: return
    if (this.parent is ViewGroup) {
        (parent as ViewGroup).removeView(this)
    }
}

/**
 * @param
 * @return
 * @author banketree
 * @time 2020/8/21 12:27 AM
 * @description
 * 防抖动事件
 */
inline fun View.setOnClickThrottleListener(
    millisecond: Long = 500,
    crossinline actionFun: () -> Unit
) {
    setOnClickListener(object : View.OnClickListener {
        @Volatile
        var clickTime = 0L

        override fun onClick(v: View?) {
            val localValue = System.currentTimeMillis() - clickTime
            if (localValue <= millisecond)
                return
            clickTime = System.currentTimeMillis()
            actionFun.invoke()
        }
    })
}

inline fun setOnClickThrottleListListener(
    vararg views: View,
    millisecond: Long = 1000,
    crossinline actionFun: () -> Unit
) {
    if (views.isEmpty()) return
    val onClickListener = object : View.OnClickListener {
        @Volatile
        var clickTime = 0L

        override fun onClick(v: View?) {
            val localValue = System.currentTimeMillis() - clickTime
            if (localValue <= millisecond)
                return
            clickTime = System.currentTimeMillis()
            actionFun.invoke()
        }
    }

    for (view in views) {
        view.setOnClickListener(onClickListener)
    }
}