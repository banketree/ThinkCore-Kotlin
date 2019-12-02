package com.thinkcore.kotlin.extend.all

import android.os.ResultReceiver
import android.os.Handler
import android.os.Bundle

public inline fun ResultReceiver(crossinline receive: (Int, Bundle?) -> Unit): ResultReceiver = ResultReceiver(Handler(), receive)

public inline fun ResultReceiver(handler: Handler?, crossinline receive: (Int, Bundle?) -> Unit): ResultReceiver {
    return object : ResultReceiver(handler) {
        protected override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            receive(resultCode, resultData)
        }
    }
}
