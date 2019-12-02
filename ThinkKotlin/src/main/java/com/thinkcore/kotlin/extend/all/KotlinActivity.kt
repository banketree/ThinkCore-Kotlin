package com.thinkcore.kotlin.extend.all

import android.app.Activity
import android.view.View

public inline fun <T : View> Activity.findView(id: Int): T? = findViewById(id) as? T

public inline fun Activity.runOnUiThread(noinline action: () -> Unit): Unit {
    runOnUiThread(Runnable { (action) })
}
