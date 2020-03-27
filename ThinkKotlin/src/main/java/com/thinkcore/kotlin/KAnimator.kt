package com.thinkcore.kotlin

import android.animation.Animator

inline fun Animator.animListener(init: AnimatorListener.() -> Unit) = addListener(AnimatorListener().apply(init))

class AnimatorListener : Animator.AnimatorListener {

    private var _onAnimationRepeat: ((Animator?) -> Unit)? = null
    private var _onAnimationEnd: ((Animator?) -> Unit)? = null
    private var _onAnimationStart: ((Animator?) -> Unit)? = null
    private var _onAnimationCancel: ((Animator?) -> Unit)? = null

    override fun onAnimationRepeat(animator: Animator?) {
        _onAnimationRepeat?.invoke(animator)
    }

    override fun onAnimationEnd(animator: Animator?) {
        _onAnimationEnd?.invoke(animator)
    }

    override fun onAnimationStart(animator: Animator?) {
        _onAnimationStart?.invoke(animator)
    }

    override fun onAnimationCancel(animator: Animator?) {
        _onAnimationCancel?.invoke(animator)
    }

    fun onAnimationRepeat(listener: (Animator?) -> Unit) {
        _onAnimationRepeat = listener
    }

    fun onAnimationEnd(listener: (Animator?) -> Unit) {
        _onAnimationEnd = listener
    }

    fun onAnimationStart(listener: (Animator?) -> Unit) {
        _onAnimationStart = listener
    }

    fun onAnimationCancel(listener: (Animator?) -> Unit) {
        _onAnimationCancel = listener
    }
}