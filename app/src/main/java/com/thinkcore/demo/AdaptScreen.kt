package com.thinkcore.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_adapt.*
import me.jessyan.autosize.TAdapterScreen

class AdaptScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapt)


        info_tv.text = """
            screenWidth:${TAdapterScreen.getInstance().screenWidth}  screenHeight:${TAdapterScreen.getInstance().screenHeight}
            widthPixels:${TAdapterScreen.getInstance().initDisplayMetrics?.widthPixels}  heightPixels:${TAdapterScreen.getInstance().initDisplayMetrics?.heightPixels}
            density:${TAdapterScreen.getInstance().initDisplayMetrics?.density}  densityDpi:${TAdapterScreen.getInstance().initDisplayMetrics?.densityDpi}
            scaledDensity:${TAdapterScreen.getInstance().initDisplayMetrics?.scaledDensity}  xdpi:${TAdapterScreen.getInstance().initDisplayMetrics?.xdpi}
            designWidthInDp:${TAdapterScreen.getInstance().adaptConfig.designWidthInDp}  designHeightInDp:${TAdapterScreen.getInstance().adaptConfig.designHeightInDp}
            isBaseOnWidth:${TAdapterScreen.getInstance().adaptConfig.isBaseOnWidth}  isExcludeFontScale:${TAdapterScreen.getInstance().adaptConfig.isExcludeFontScale}
        """.trimIndent()
    }
}
