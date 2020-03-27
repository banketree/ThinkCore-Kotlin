package com.thinkcore.demo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thinkcore.activity.IActivityResult
import com.thinkcore.activity.TActivityUtils
import com.thinkcore.activity.TAppActivity
import com.thinkcore.activity.jumpToActivityForResult
import com.thinkcore.cache.disc.DiskLruCache
import com.thinkcore.cache.memory.impl.UsingFreqLimitedMemoryCache
import com.thinkcore.kotlin.toTimeForYYMMDDHHMMSS
import kotlinx.android.synthetic.main.activity_main.*
import me.jessyan.autosize.TAdapterScreen

class MainActivity : TAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        test_tv.setOnClickListener {
            startScreen()
        }

        test_params_tv.setOnClickListener {
            val adaptConfig = TAdapterScreen.getInstance().adaptConfig
            adaptConfig.isBaseOnWidth = !adaptConfig.isBaseOnWidth
            adaptConfig.isExcludeFontScale = !adaptConfig.isExcludeFontScale
            startScreen()
        }
    }

    private fun startScreen(){
        jumpToActivityForResult<AdaptScreen>(
            iActivityResult = object : IActivityResult {
                override fun onActivityResult(resultCode: Int, intent: Intent?) {
                    if (resultCode == Activity.RESULT_OK) {

                    }
                }
            })

        val cache: UsingFreqLimitedMemoryCache = UsingFreqLimitedMemoryCache(50)
        cache.put("", "test")
        cache.get("")
//            val diskCache: DiskLruCache = DiskLruCache.open(null, 1, 2, 50)
    }
}
