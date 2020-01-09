package com.thinkcore.activity

import android.view.KeyEvent

interface IKeyDownListener {
    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean
}