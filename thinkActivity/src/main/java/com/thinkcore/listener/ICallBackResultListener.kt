package com.thinkcore.listener

/**
 * @author banketree
 * @time 2020/1/3 17:06
 * @description 带参数的回调
 */
interface ICallBackResultListener {
    fun onCallBack(result: Any)
}

inline fun onCallBackResultListener(crossinline iCallBackResultListener: ((result: Any) -> Unit)): ICallBackResultListener {
    return object : ICallBackResultListener {
        override fun onCallBack(result: Any) = iCallBackResultListener.invoke(result)
    }
}
