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

fun Activity.bindToLongClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToLongClickP { findViewById(viewId) }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToLongClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToLongClickP { view!!.findViewById(viewId) }

fun FrameLayout.bindToLongClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToLongClickP { findViewById(viewId) }

fun Activity.bindToLongClick(viewProvider: () -> View): ReadWriteProperty<Any?, () -> Unit>
        = bindToLongClickP { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToLongClick(viewProvider: () -> View): ReadWriteProperty<Any?, () -> Unit>
        = bindToLongClickP { viewProvider() }

fun View.bindToLongClick(): ReadWriteProperty<Any?, () -> Unit>
        = bindToLongClickP { this }

private fun bindToLongClickP(viewProvider: () -> View): ReadWriteProperty<Any?, () -> Unit>
        = OnLongClickBinding(lazy(viewProvider))

private class OnLongClickBinding(viewProvider: Lazy<View>) : ReadWriteProperty<Any?, () -> Unit> {

    val view by viewProvider
    var function: (() -> Unit)? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): () -> Unit {
        return function ?: noop
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: () -> Unit) {
        function = value
        view.setOnLongClickListener { function?.invoke(); true }
    }

    companion object {
        val noop: () -> Unit = {}
    }
}