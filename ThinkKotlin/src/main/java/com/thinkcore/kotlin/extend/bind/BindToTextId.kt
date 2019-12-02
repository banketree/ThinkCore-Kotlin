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

fun Activity.bindToTextId(@IdRes textViewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToTextIdP { findViewById(textViewId) as TextView }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToTextId(@IdRes textViewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToTextIdP { view!!.findViewById(textViewId) as TextView }

fun FrameLayout.bindToTextId(@IdRes textViewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToTextIdP { findViewById(textViewId) as TextView }

fun Activity.bindToTextId(viewProvider: () -> TextView): ReadWriteProperty<Any?, Int?>
        = bindToTextIdP { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToTextId(viewProvider: () -> TextView): ReadWriteProperty<Any?, Int?>
        = bindToTextIdP { viewProvider() }

fun TextView.bindToTextId(): ReadWriteProperty<Any?, Int?>
        = bindToTextIdP { this }

private fun bindToTextIdP(viewProvider: () -> TextView): ReadWriteProperty<Any?, Int?>
        = TextIdBinding(lazy(viewProvider))

private class TextIdBinding(lazyViewProvider: Lazy<TextView>) : ReadWriteProperty<Any?, Int?> {

    val view by lazyViewProvider
    var textId: Int? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int? {
        return textId
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
        value ?: return
        textId = value
        view.setText(value)
    }
}