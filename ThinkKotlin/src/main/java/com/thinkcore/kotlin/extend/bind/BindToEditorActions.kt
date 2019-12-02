package com.thinkcore.kotlin.extend.bind

import android.app.Activity
import android.os.Build
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToEditorActions(@IdRes editTextId: Int, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActionsP(predicate, { findViewById(editTextId) as EditText })

fun Fragment.bindToEditorActions(@IdRes viewId: Int, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActionsP(predicate) { view!!.findViewById(viewId) as EditText }

fun FrameLayout.bindToEditorActions(@IdRes viewId: Int, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActionsP(predicate) { findViewById(viewId) as EditText }

fun Activity.bindToEditorActions(viewProvider: () -> EditText, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActionsP(predicate, { viewProvider() })

fun Fragment.bindToEditorActions(viewProvider: () -> EditText, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActionsP(predicate) { viewProvider() }

fun EditText.bindToEditorActions(predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActionsP(predicate) { this }

private fun bindToEditorActionsP(predicate: (Int?, Int?) -> Boolean, viewProvider: () -> EditText): ReadWriteProperty<Any?, () -> Unit>
        = OnEditorActionBinding(predicate, lazy(viewProvider))

private class OnEditorActionBinding(
        val predicate: (Int?, Int?) -> Boolean,
        viewProvider: Lazy<EditText>
) : ReadWriteProperty<Any?, () -> Unit> {

    val view by viewProvider
    var function: (() -> Unit)? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): () -> Unit {
        return function ?: noop
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: () -> Unit) {
        setUpListener()
        function = value
    }

    fun setUpListener() {
        if (function == null) {
            view.setOnEditorActionListener { _, actionId: Int?, event ->
                val handleAction = predicate(actionId, event?.keyCode)
                if (handleAction) function?.invoke()
                return@setOnEditorActionListener handleAction
            }
        }
    }

    companion object {
        val noop: () -> Unit = {}
    }
}