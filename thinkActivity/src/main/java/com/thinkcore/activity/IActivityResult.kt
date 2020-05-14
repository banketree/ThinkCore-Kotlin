package com.thinkcore.activity

import android.content.Intent

interface IActivityResult {
    fun onActivityResult(resultCode: Int, intent: Intent?)
}

inline fun onActivityResult(crossinline iActivityResult: (resultCode: Int, intent: Intent?) -> Unit): IActivityResult {
    return object : IActivityResult {
        override fun onActivityResult(resultCode: Int, intent: Intent?) =
            iActivityResult.invoke(resultCode, intent)
    }
}
