package com.thinkcore.demo

import android.os.Bundle
import com.thinkcore.activity.TAppActivity

class TestActivity : TAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
