package me.jessyan.autosize.core

import android.app.Activity

/**
 * AdaptConfig 参数配置类
 */
class AdaptConfig {
    /**
     * 设计图上的总宽度, 单位 dp
     */
    var designWidthInDp: Int = 0
    /**
     * 设计图上的总高度, 单位 dp
     */
    var designHeightInDp: Int = 0

    /**
     * 比例缩放
     * [.isBaseOnWidth] 为 `true` 时代表以宽度等比例缩放, `false` 代表以高度等比例缩放
     * [.isBaseOnWidth] 为全局配置, 默认为 `true`, 每个 [Activity] 也可以单独选择使用高或者宽做等比例缩放
     */
    var isBaseOnWidth = true
    /**
     * 此字段表示是否使用设备的实际尺寸做适配
     * [.isUseDeviceSize] 为 `true` 表示屏幕高度 [.screenHeight] 包含状态栏的高度
     * [.isUseDeviceSize] 为 `false` 表示 [.screenHeight] 会减去状态栏的高度, 默认为 `true`
     */
    var isUseDeviceSize = true

    /**
     * 是否屏蔽系统字体大小
     */
    var isExcludeFontScale: Boolean = false

    class Builder {
        private val adaptConfig = AdaptConfig()

        fun setDesignWidthInDp(designWidthInDp: Int): Builder {
            adaptConfig.designWidthInDp = designWidthInDp
            return this
        }

        fun setDesignHeightInDp(designHeightInDp: Int): Builder {
            adaptConfig.designHeightInDp = designHeightInDp
            return this
        }

        fun setBaseOnWidth(isBaseOnWidth: Boolean): Builder {
            adaptConfig.isBaseOnWidth = isBaseOnWidth
            return this
        }


        fun setUseDeviceSize(isUseDeviceSize: Boolean): Builder {
            adaptConfig.isUseDeviceSize = isUseDeviceSize
            return this
        }

        fun setExcludeFontScale(isExcludeFontScale: Boolean): Builder {
            adaptConfig.isExcludeFontScale = isExcludeFontScale
            return this
        }

        fun create(): AdaptConfig {
            return adaptConfig
        }
    }
}
