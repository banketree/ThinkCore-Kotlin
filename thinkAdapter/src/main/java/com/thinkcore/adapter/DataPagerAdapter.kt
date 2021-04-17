package com.thinkcore.adapter

import android.view.View
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup


/**
 * @author banketree
 * @time 2020/1/7 15:27
 * @description
 * 封装基础使用
 */
class DataPagerAdapter private constructor() : PagerAdapter() {

    //绑定事件的lambda放发
    private var onGetCount: (() -> Int)? = null
    //绑定事件的lambda放发
    private var onBindView: ((index: Int) -> View)? = null
    private val viewHashMap = hashMapOf<String, View>()


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        onGetCount?.let {
            return it.invoke()
        }
        return 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        onBindView?.let {
            val cacheView = viewHashMap["$position"]
            val pagerView = if (cacheView == null) {
                val view = it.invoke(position)
                viewHashMap["$position"] = view
                view
            } else {
                cacheView
            }

            container.addView(pagerView)
            return pagerView
        }
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        viewHashMap["$position"]?.let {
            container.removeView(it)
        }
    }

    /**
     * 建造者，用来完成adapter的数据组合
     */
    class Builder {
        private var adapter: DataPagerAdapter = DataPagerAdapter()

        /**
         * 绑定View和数据
         */
        fun onBindView(onBindView: ((index: Int) -> View)): Builder {
            adapter.onBindView = onBindView
            return this
        }

        /**
         * 绑定创建布局事件
         */
        fun onGetCount(onGetCount: (() -> Int)): Builder {
            adapter.onGetCount = onGetCount
            return this
        }

        fun create(): DataPagerAdapter {
            return adapter
        }
    }
}