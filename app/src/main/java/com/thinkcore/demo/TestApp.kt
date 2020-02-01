package com.thinkcore.demo

import android.app.Application
import me.jessyan.autosize.TAdapterScreen
import me.jessyan.autosize.core.AdaptConfig

class TestApp :Application(){

    override fun onCreate() {
        super.onCreate()
        initAdaptScreen()
    }

    private fun initAdaptScreen(){
        val config = AdaptConfig.Builder()
            .setBaseOnWidth(false)
            .setDesignWidthInDp(375)
            .setDesignHeightInDp(750)
            .setExcludeFontScale(true)
            .setUseDeviceSize(true)
            .create()
        TAdapterScreen.getInstance().init(this, config)
    }
}