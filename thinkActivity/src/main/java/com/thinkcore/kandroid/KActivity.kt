@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kandroid

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

@Deprecated("Use findViewById() instead", ReplaceWith("findViewById()"))
inline fun <reified T : View> Activity.find(@IdRes id: Int): T = findViewById(id)

inline fun <reified T : Any> Activity.startActivityForResult(
    requestCode: Int,
    options: Bundle? = null,
    action: String? = null
) =
    startActivityForResult(IntentFor<T>(this).setAction(action), requestCode, options)

inline fun Activity.lockCurrentScreenOrientation() {
    requestedOrientation = when (resources.configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        else -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    }
}

inline fun Activity.unlockScreenOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
}

inline fun Activity.hideSoftInput() {
    val view = window.peekDecorView()
    view?.let {
        inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

inline fun Activity.postDelay(delayMillis: Long, crossinline actionFun: () -> Unit = {}) {
    val view = window.peekDecorView()
    view?.let {
        view.postDelayed({
            if (isFinishing) return@postDelayed
            actionFun()
        }, delayMillis)
    }
}

/**
 * Activity show
 */
inline fun <reified T : Fragment> FragmentActivity.showFragment(
    replaceViewId: Int, init: (T).() -> Unit = {}
): T {
    val sfm = supportFragmentManager
    val transaction = sfm.beginTransaction()
    var fragment = sfm.findFragmentByTag(T::class.java.name)
    val isFirstFragment = fragment == null
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
    return fragment
}

inline fun <reified T : Fragment> FragmentActivity.getFragment(
    init: (T)?.() -> Unit = {}
): T? {
    val fragment = supportFragmentManager.findFragmentByTag(T::class.java.name)
    init(fragment as T?)
    return fragment
}

/**
 * Activity show
 */
inline fun FragmentActivity.showFragment(
    fragment: Fragment,
    replaceViewId: Int
) {
    val sfm = supportFragmentManager
    val transaction = sfm.beginTransaction()
    val isFirstFragment = !fragment.isAdded
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