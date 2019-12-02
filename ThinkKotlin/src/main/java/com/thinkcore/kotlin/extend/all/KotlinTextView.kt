package com.thinkcore.kotlin.extend.all

import android.widget.TextView
import android.view.KeyEvent
import android.widget.TextView.OnEditorActionListener

public inline fun OnEditorActionListener(crossinline action: (TextView?, Int, KeyEvent?) -> Boolean): OnEditorActionListener {
    return object : OnEditorActionListener {
        public override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean = action(p0, p1, p2)
    }
}

public inline fun TextView.setOnEditorActionListener(noinline action: (TextView?, Int, KeyEvent?) -> Boolean): Unit {
    setOnEditorActionListener(OnEditorActionListener(action))
}
