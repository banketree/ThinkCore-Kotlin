package com.thinkcore.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class TLibFragment : TFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutAny = getLayoutAny()
        layoutAny?.let {
            var rootView: View?
            when (it) {
                is Int -> {
                    rootView = inflater.inflate(it, null)
                }
                is View -> {
                    rootView = it
                }
                else -> throw Exception("layout error")
            }

            initPlug()
            return rootView
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    /** 设置布局id 或 View*/
    abstract fun getLayoutAny(): Any?

    /**初始化 插件*/
    abstract fun initPlug()

    /**初始化视图*/
    abstract fun initView()

    /** 初始化数据*/
    abstract fun initData()
}