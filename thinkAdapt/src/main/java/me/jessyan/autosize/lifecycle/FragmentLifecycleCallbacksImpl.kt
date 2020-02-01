package me.jessyan.autosize.lifecycle

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import me.jessyan.autosize.strategy.IAutoAdaptStrategy

/**
 * [FragmentLifecycleCallbacksImpl] 可用来代替在 BaseFragment 中加入适配代码的传统方式
 * [FragmentLifecycleCallbacksImpl] 这种方案类似于 AOP, 面向接口, 侵入性低, 方便统一管理, 扩展性强, 并且也支持适配三方库的 [@Fragment]
 */
class FragmentLifecycleCallbacksImpl(
    private var IAutoAdaptStrategy: IAutoAdaptStrategy?//屏幕适配逻辑策略类
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        IAutoAdaptStrategy?.applyAdapt(f, f.activity!!)
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        IAutoAdaptStrategy?.applyAdapt(f, f.activity!!)
    }

    /**
     * 设置屏幕适配逻辑策略类
     *
     * @param IAutoAdaptStrategy [IAutoAdaptStrategy]
     */
    fun setAutoAdaptStrategy(IAutoAdaptStrategy: IAutoAdaptStrategy) {
        this.IAutoAdaptStrategy = IAutoAdaptStrategy
    }
}
