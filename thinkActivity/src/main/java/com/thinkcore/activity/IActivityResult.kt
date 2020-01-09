package com.thinkcore.activity

import android.content.Intent

interface IActivityResult {
    fun onActivityResult(resultCode: Int, intent: Intent?)
}