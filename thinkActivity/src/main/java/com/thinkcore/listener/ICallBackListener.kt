package com.thinkcore.listener

/**
 * @author banketree
 * @time 2020/1/3 17:06
 * @description 专注回调
 */
open interface ICallBackListener {

    fun onCallBack()
}


inline fun onCallBackListener(crossinline iCallBackListener: () -> Unit): ICallBackListener {
    return object : ICallBackListener {
        override fun onCallBack() {
            iCallBackListener.invoke()
        }
    }
}