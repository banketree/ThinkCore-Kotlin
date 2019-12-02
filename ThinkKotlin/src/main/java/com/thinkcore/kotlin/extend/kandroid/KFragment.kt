@file:Suppress("NOTHING_TO_INLINE")
package com.thinkcore.kotlin.extend.kandroid

import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun Fragment.getDefaultSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(activity)

inline fun Fragment.toast(text: CharSequence): Toast = activity!!.toast(text)

inline fun Fragment.longToast(text: CharSequence): Toast = activity!!.longToast(text)

inline fun Fragment.toast(@StringRes resId: Int): Toast = activity!!.toast(resId)

inline fun Fragment.longToast(@StringRes resId: Int): Toast = activity!!.longToast(resId)

//inline fun SupportFragment.getDefaultSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(activity)
//
//inline fun SupportFragment.toast(text: CharSequence): Toast? = activity?.toast(text)
//
//inline fun SupportFragment.longToast(text: CharSequence): Toast? = activity?.longToast(text)
//
//inline fun SupportFragment.toast(@StringRes resId: Int): Toast? = activity?.toast(resId)
//
//inline fun SupportFragment.longToast(@StringRes resId: Int): Toast? = activity?.longToast(resId)

inline fun <reified T : Preference> PreferenceFragment.findPref(key: String): T = findPreference(key) as T
