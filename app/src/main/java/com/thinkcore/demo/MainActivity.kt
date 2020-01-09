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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : TAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        test_tv.setOnClickListener {
            jumpToActivityForResult<TestActivity>(
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
}
