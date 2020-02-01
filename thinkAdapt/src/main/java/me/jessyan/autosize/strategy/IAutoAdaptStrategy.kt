package me.jessyan.autosize.strategy

import android.app.Activity
import android.util.DisplayMetrics

/**
 * 屏幕适配逻辑策略类
 */
interface IAutoAdaptStrategy {

    /**
     * 开始执行屏幕适配逻辑
     *
     * @param target   需要屏幕适配的对象 (可能是 [Activity] 或者 [@Fragment])
     * @param activity 需要拿到当前的 [Activity] 才能修改 [DisplayMetrics.density]
     */
    fun applyAdapt(target: Any, activity: Activity)
}
