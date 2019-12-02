package com.thinkcore.kotlin.extend.all

public inline fun async(noinline action: () -> Unit): Unit = Thread(Runnable(action)).start()
