package com.thinkcore.kotlin.extend.all

import android.view.View
import android.app.Activity
import android.view.View.OnClickListener
import android.view.MotionEvent
import android.view.View.OnTouchListener

public inline fun <T: View> View.findView(id: Int): T? = findViewById(id) as? T

public inline fun OnTouchListener(crossinline action: (View?, MotionEvent?) -> Boolean): OnTouchListener {
    return object : OnTouchListener {
        public override fun onTouch(p0: View?, p1: MotionEvent?): Boolean = action(p0, p1)
    }
}

public inline fun OnClickListener(crossinline action: (View?) -> Unit): OnClickListener {
    return object : OnClickListener {
        public override fun onClick(p0: View?) {
            action(p0)
        }
    }
}

public inline fun View.setOnClickListener(noinline action: (View?) -> Unit): Unit {
    setOnClickListener(OnClickListener(action))
}

public inline fun View.setOnTouchListener(noinline action: (View?, MotionEvent?) -> Boolean): Unit {
    setOnTouchListener(OnTouchListener(action))
}