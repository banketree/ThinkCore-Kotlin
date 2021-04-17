package com.thinkcore.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * @author banketree
 * @time 2020/3/11 15:27
 * @description
 * 封装基础使用
 */
@Suppress("DEPRECATION")
class FragmentDataPagerAdapter private constructor(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    //绑定事件的lambda放发
    private var onGetCount: (() -> Int)? = null
    //绑定事件的lambda放发
    private var onBindVFragment: ((index: Int) -> Fragment)? = null

    override fun getItem(position: Int): Fragment {
        onBindVFragment?.let {
            return it.invoke(position)
        }
        return Fragment()
    }

    override fun getCount(): Int {
        onGetCount?.let {
            return it.invoke()
        }
        return 0
    }

    /**
     * 建造者，用来完成adapter的数据组合
     */
    class Builder constructor(fragmentManager: FragmentManager) {
        private var adapter: FragmentDataPagerAdapter = FragmentDataPagerAdapter(fragmentManager)

        /**
         * 绑定View和数据
         */
        fun onBindVFragment(onBindVFragment: ((index: Int) -> Fragment)): Builder {
            adapter.onBindVFragment = onBindVFragment
            return this
        }

        /**
         * 绑定创建布局事件
         */
        fun onGetCount(onGetCount: (() -> Int)): Builder {
            adapter.onGetCount = onGetCount
            return this
        }

        fun create(): FragmentDataPagerAdapter {
            return adapter
        }
    }
}