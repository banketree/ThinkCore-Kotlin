package com.thinkcore.kotlin.extend.all

import java.util.concurrent.ExecutorService
import java.util.concurrent.Callable
import java.util.concurrent.Future

public inline fun ExecutorService.execute(noinline action: () -> Unit): Unit {
    execute(Runnable(action))
}

public inline fun <T> ExecutorService.submit(crossinline action: () -> T?): Future<out T>? {
    return submit(object : Callable<T> {
        public override fun call(): T? = action()
    })
}

public inline fun <T> ExecutorService.submit(result: T, noinline action: () -> Unit): Future<out T>? {
    return submit(Runnable(action), result)
}
