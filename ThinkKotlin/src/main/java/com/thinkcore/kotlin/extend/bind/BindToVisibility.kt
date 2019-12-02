package com.thinkcore.kotlin.extend.bind

import android.app.Activity
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToVisibility(@IdRes editTextId: Int): ReadWriteProperty<Any?, Boolean>
        = bindToVisibilityP { findViewById(editTextId) }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToVisibility(@IdRes editTextId: Int): ReadWriteProperty<Any?, Boolean>
        = bindToVisibilityP { view!!.findViewById(editTextId) }

fun FrameLayout.bindToVisibility(@IdRes editTextId: Int): ReadWriteProperty<Any?, Boolean>
        = bindToVisibilityP { findViewById(editTextId) }

fun Activity.bindToVisibility(viewProvider: () -> View): ReadWriteProperty<Any?, Boolean>
        = bindToVisibilityP { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToVisibility(viewProvider: () -> View): ReadWriteProperty<Any?, Boolean>
        = bindToVisibilityP { viewProvider() }

fun View.bindToVisibility(): ReadWriteProperty<Any?, Boolean>
        = bindToVisibilityP { this }

private fun bindToVisibilityP(viewProvider: () -> View): ReadWriteProperty<Any?, Boolean>
        = ViewVisibilityBinding(lazy(viewProvider))

private class ViewVisibilityBinding(viewProvider: Lazy<View>) : ReadWriteProperty<Any?, Boolean> {

    val view by viewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return view.visibility == View.VISIBLE
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        view.visibility = if(value) View.VISIBLE else View.GONE
    }
}