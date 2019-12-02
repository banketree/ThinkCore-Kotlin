package com.thinkcore.kotlin.extend.bind

import android.app.Activity
import android.os.Build
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToText(@IdRes textViewId: Int): ReadWriteProperty<Any?, String>
        = bindToTextP { findViewById(textViewId) as TextView }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToText(@IdRes textViewId: Int): ReadWriteProperty<Any?, String>
        = bindToTextP { view!!.findViewById(textViewId) as TextView }

fun FrameLayout.bindToText(@IdRes textViewId: Int): ReadWriteProperty<Any?, String>
        = bindToTextP { findViewById(textViewId) as TextView }

fun Activity.bindToText(viewProvider: () -> TextView): ReadWriteProperty<Any?, String>
        = bindToTextP { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToText(viewProvider: () -> TextView): ReadWriteProperty<Any?, String>
        = bindToTextP { viewProvider() }

fun TextView.bindToText(): ReadWriteProperty<Any?, String>
        = bindToTextP { this }

private fun bindToTextP(viewProvider: () -> TextView): ReadWriteProperty<Any?, String>
        = TextBinding(lazy(viewProvider))

private class TextBinding(lazyViewProvider: Lazy<TextView>) : ReadWriteProperty<Any?, String> {

    val view by lazyViewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.text = value
    }
}