package com.thinkcore.activity

import android.view.KeyEvent

interface IKeyDownListener {
    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean
}

inline fun onKeyDownListener(crossinline iActivityResult: (keyCode: Int, event: KeyEvent) -> Boolean): IKeyDownListener {
    return object : IKeyDownListener {
        override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean =
            iActivityResult.invoke(keyCode, event)
    }
}
