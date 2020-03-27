@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kotlin

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment

inline fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

inline fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

inline fun Fragment.dp(value: Int): Int = activity!!.dp(value)

inline fun Fragment.sp(value: Int): Int = activity!!.sp(value)

//inline fun SupportFragment.dp(value: Int): Int? = activity?.dp(value)
//
//inline fun SupportFragment.sp(value: Int): Int? = activity?.sp(value)

inline fun View.dp(value: Int): Int = context.dp(value)

inline fun View.sp(value: Int): Int = context.sp(value)
