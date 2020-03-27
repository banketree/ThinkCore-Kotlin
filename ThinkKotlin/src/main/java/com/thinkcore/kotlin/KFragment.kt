@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kotlin

import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun Fragment.getDefaultSharedPreferences() =
    PreferenceManager.getDefaultSharedPreferences(activity)

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

inline fun <reified T : Preference> PreferenceFragment.findPref(key: String): T =
    findPreference(key) as T

/**
 * Fragment show
 */
inline fun <reified T : Fragment> Fragment.showFragment(
    replaceViewId: Int, init: (T).() -> Unit = {}
): T {
    val sfm = childFragmentManager
    val transaction = sfm.beginTransaction()
    var fragment = sfm.findFragmentByTag(T::class.java.name)
    if (fragment == null) {
        fragment = T::class.java.newInstance()
        transaction.add(replaceViewId, fragment, T::class.java.name)
    }
    sfm.fragments.filter { it != fragment }.forEach {
        transaction.hide(it)
    }
    transaction.show(fragment)
    transaction.commitAllowingStateLoss()
    sfm.executePendingTransactions()
    init(fragment as T)
    return fragment as T
}

/**
 * Fragment show
 */
inline fun Fragment.showFragment(
    fragment: Fragment,
    replaceViewId: Int
) {
    val sfm = childFragmentManager
    val transaction = sfm.beginTransaction()
    if (!fragment.isAdded) {
        transaction.add(replaceViewId, fragment, fragment.javaClass.name)
    }
    sfm.fragments.filter { it != fragment }.forEach {
        transaction.hide(it)
    }
    transaction.show(fragment)
    transaction.commitAllowingStateLoss()
    sfm.executePendingTransactions()
}