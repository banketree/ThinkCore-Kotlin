package com.thinkcore.kotlin.extend.all

import java.util.concurrent.Callable

public inline fun <T> callable(crossinline action: () -> T?): Callable<out T> {
    return object : Callable<T> {
        public override fun call(): T? = action()
    }
}
