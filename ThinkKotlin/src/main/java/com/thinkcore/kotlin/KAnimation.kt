package com.thinkcore.kotlin

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

@Suppress("NOTHING_TO_INLINE")
inline fun Context.loadAnimation(@AnimRes id: Int) = AnimationUtils.loadAnimation(applicationContext, id)

inline fun Animation.animListener(init: AnimListener.() -> Unit) = setAnimationListener(AnimListener().apply(init))

class AnimListener : Animation.AnimationListener {

    private var _onAnimationRepeat: ((Animation?) -> Unit)? = null
    private var _onAnimationEnd: ((Animation?) -> Unit)? = null
    private var _onAnimationStart: ((Animation?) -> Unit)? = null

    override fun onAnimationRepeat(animation: Animation?) {
        _onAnimationRepeat?.invoke(animation)
    }

    override fun onAnimationEnd(animation: Animation?) {
        _onAnimationEnd?.invoke(animation)
    }

    override fun onAnimationStart(animation: Animation?) {
        _onAnimationStart?.invoke(animation)
    }

    fun onAnimationRepeat(listener: (Animation?) -> Unit) {
        _onAnimationRepeat = listener
    }

    fun onAnimationEnd(listener: (Animation?) -> Unit) {
        _onAnimationEnd = listener
    }

    fun onAnimationStart(listener: (Animation?) -> Unit) {
        _onAnimationStart = listener
    }
}