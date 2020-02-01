package me.jessyan.autosize.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import me.jessyan.autosize.strategy.IAutoAdaptStrategy

/**
 * [ActivityLifecycleCallbacksImpl] 可用来代替在 BaseActivity 中加入适配代码的传统方式
 * [ActivityLifecycleCallbacksImpl] 这种方案类似于 AOP, 面向接口, 侵入性低, 方便统一管理, 扩展性强, 并且也支持适配三方库的 [Activity]
 */
class ActivityLifecycleCallbacksImpl(
    private var iAutoAdaptStrategy: IAutoAdaptStrategy?//屏幕适配逻辑策略类
) : Application.ActivityLifecycleCallbacks {
    /**
     * 让 [@Fragment] 支持自定义适配参数
     */
    private val fragmentLifecycleCallbacks: FragmentLifecycleCallbacksImpl =
        FragmentLifecycleCallbacksImpl(iAutoAdaptStrategy)

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                fragmentLifecycleCallbacks,
                true
            )
        }

        //Activity 中的 setContentView(View) 一定要在 super.onCreate(Bundle); 之后执行
        iAutoAdaptStrategy?.let {
            it.applyAdapt(activity, activity)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        iAutoAdaptStrategy?.let {
            it.applyAdapt(activity, activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        iAutoAdaptStrategy?.let {
            it.applyAdapt(activity, activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    /**
     * 设置屏幕适配逻辑策略类
     *
     * @param IAutoAdaptStrategy [IAutoAdaptStrategy]
     */
    fun setAutoAdaptStrategy(iAutoAdaptStrategy: IAutoAdaptStrategy) {
        this.iAutoAdaptStrategy = iAutoAdaptStrategy
        this.fragmentLifecycleCallbacks.setAutoAdaptStrategy(iAutoAdaptStrategy)
    }
}
