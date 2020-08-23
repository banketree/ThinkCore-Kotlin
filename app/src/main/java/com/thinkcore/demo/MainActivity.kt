package com.thinkcore.demo

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.thinkcore.activity.IActivityResult
import com.thinkcore.activity.TAppActivity
import com.thinkcore.activity.jumpToActivityForResult
import com.thinkcore.cache.memory.impl.UsingFreqLimitedMemoryCache
import com.thinkcore.listener.ICallBackListener
import com.thinkcore.listener.ICallBackResultListener
import com.thinkcore.listener.onCallBackListener
import com.thinkcore.listener.onCallBackResultListener
import com.thinkcore.preference.getLocalSharedPreferences
import com.thinkcore.preference.getValueByDes
import com.thinkcore.preference.setValueByDes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : TAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun initView() {
        test_tv.setOnClickListener {
            startScreen()
        }

        test_params_tv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
            }
        })
        test_params_tv.setOnClickListener {
            val test = getLocalSharedPreferences().getValueByDes("dddd", "ddd")
            val test3 = getLocalSharedPreferences().getValueByDes("dddd3", "")
            getLocalSharedPreferences().setValue("dada", "12312321312321")
            getLocalSharedPreferences().setValueByDes("dada2", "12312321312321")
            val test1 = getLocalSharedPreferences().getValue("dada", "")
            val test2 = getLocalSharedPreferences().getValue("dada2", "")

            Log.i("", "${test1}${test2}")
            //            val adaptConfig = TAdapterScreen.getInstance().adaptConfig
//            adaptConfig.isBaseOnWidth = !adaptConfig.isBaseOnWidth
//            adaptConfig.isExcludeFontScale = !adaptConfig.isExcludeFontScale
//            startScreen()
        }

        testListener(onCallBackListener {

        })


        testListener(onCallBackResultListener {

        })
    }

    private fun startScreen() {
//        jumpToActivityForResult<AdaptScreen>(
//            iActivityResult = object : IActivityResult {
//                override fun onActivityResult(resultCode: Int, intent: Intent?) {
//                    if (resultCode == Activity.RESULT_OK) {
//
//                    }
//                }
//            })
//
//        val cache: UsingFreqLimitedMemoryCache = UsingFreqLimitedMemoryCache(50)
//        cache.put("", "test")
//        cache.get("")
//            val diskCache: DiskLruCache = DiskLruCache.open(null, 1, 2, 50)
    }

    private fun testListener(listenr: ICallBackListener) {

    }


    private fun testListener(listenr: ICallBackResultListener) {

    }
}
