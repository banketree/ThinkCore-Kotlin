@file:Suppress("NOTHING_TO_INLINE")
package com.thinkcore.kotlin.extend.kandroid

import android.view.View
import android.view.View.*
import androidx.annotation.IdRes

@Deprecated("Use findViewById() instead", ReplaceWith("findViewById()"))
inline fun <reified T : View> View.find(@IdRes id: Int): T = findViewById(id)

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

inline fun View.setSize(width:Int, height:Int) {
    val params = layoutParams
    params.width = width
    params.height = height
    layoutParams = params
}