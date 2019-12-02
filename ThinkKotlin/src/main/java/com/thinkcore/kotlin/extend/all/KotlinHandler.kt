package com.thinkcore.kotlin.extend.all

import android.os.Handler
import android.os.Message

public inline fun Handler.post(noinline action: () -> Unit): Boolean = post(Runnable(action))
public inline fun Handler.postAtFrontOfQueue(noinline action: () -> Unit): Boolean = postAtFrontOfQueue(Runnable(action))
public inline fun Handler.postAtTime(uptimeMillis: Long, noinline action: () -> Unit): Boolean = postAtTime(Runnable(action), uptimeMillis)
public inline fun Handler.postDelayed(delayMillis: Long, noinline action: () -> Unit): Boolean = postDelayed(Runnable(action), delayMillis)

public inline fun Handler(crossinline handleMessage: (Message) -> Boolean): Handler {
    return Handler(object : Handler.Callback {
        public override fun handleMessage(p0: Message?) = if (p0 == null) false else handleMessage(p0)
    })
}
