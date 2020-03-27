@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kotlin

import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * show input
 * */
inline fun EditText.showSoftInput() {
    requestFocus()
    context.inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

/**
 * hide input
 * */
inline fun EditText.hideSoftInput() {
    context.inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}