package com.thinkcore.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

@Suppress("DEPRECATION")
abstract class TLibActivity : TAppActivity() {
    companion object {
        val RESULT_DATA = "RESULT_DATA"
        val PARAMS_DATA = "PARAMS_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutAny = getLayoutAny()
        layoutAny?.let {
            when (it) {
                is Int -> {
                    val rootView = LayoutInflater.from(this).inflate(it, null)
                    setContentView(rootView)
                }
                is View -> {
                    setContentView(it)
                }
                else -> throw Exception("layout error")
            }

            initPlug()
            initView()
            initData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
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
