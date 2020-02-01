package me.jessyan.autosize.core

import android.app.Activity
import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import java.lang.reflect.Field

/**
 * 用于屏幕适配的核心方法都在这里
 * 核心原理来自于 [今日头条官方适配方案](https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA)
 */
internal object CoreAdaptScreen {
    var tmpMetricsField: Field? = null

    /**
     * 赋值
     *
     * @param activity      [Activity]
     * @param targetDisplayMetrics [DisplayMetrics]
     */
    fun setDensity(
        activity: Activity? = null,
        application: Application,
        displayMetrics: DisplayMetrics
    ) {
        setDensity(
            activity?.resources,
            application.resources,
            displayMetrics.density,
            displayMetrics.densityDpi,
            displayMetrics.scaledDensity,
            displayMetrics.xdpi
        )
    }

    /**
     * [DisplayMetrics] 赋值
     *
     * @param activity      [Activity]
     * @param density       [DisplayMetrics.density]
     * @param densityDpi    [DisplayMetrics.densityDpi]
     * @param scaledDensity [DisplayMetrics.scaledDensity]
     * @param xdpi          [DisplayMetrics.xdpi]
     */
    fun setDensity(
        activity: Activity,
        density: Float,
        densityDpi: Int,
        scaledDensity: Float,
        xdpi: Float
    ) {
        val application = activity.application
        setDensity(
            activity.resources,
            application.resources,
            density,
            densityDpi,
            scaledDensity,
            xdpi
        )
    }

    fun setDensity(
        application: Application,
        density: Float,
        densityDpi: Int,
        scaledDensity: Float,
        xdpi: Float
    ) {
        setDensity(
            null,
            application.resources,
            density,
            densityDpi,
            scaledDensity,
            xdpi
        )
    }

    /**
     * [DisplayMetrics] 赋值
     *
     * @param activityResources      [Resources]
     * @param appResources           [Resources]
     * @param density       [DisplayMetrics.density]
     * @param densityDpi    [DisplayMetrics.densityDpi]
     * @param scaledDensity [DisplayMetrics.scaledDensity]
     * @param xdpi          [DisplayMetrics.xdpi]
     */
    fun setDensity(
        activityResources: Resources? = null,
        appResources: Resources,
        density: Float,
        densityDpi: Int,
        scaledDensity: Float,
        xdpi: Float
    ) {
        //兼容 MIUI
        activityResources?.let {
            setDensity(
                it.displayMetrics,
                density,
                densityDpi,
                scaledDensity,
                xdpi
            )

            getMetricsByMIUI(
                it,
                appResources
            )?.let { activityDisplayMetricsOnMIUI ->
                setDensity(
                    activityDisplayMetricsOnMIUI,
                    density,
                    densityDpi,
                    scaledDensity,
                    xdpi
                )
            }
        }

        setDensity(
            appResources.displayMetrics,
            density,
            densityDpi,
            scaledDensity,
            xdpi
        )

        getMetricsByMIUI(
            appResources,
            appResources
        )?.let { appDisplayMetricsOnMIUI ->
            setDensity(
                appDisplayMetricsOnMIUI,
                density,
                densityDpi,
                scaledDensity,
                xdpi
            )
        }
    }

    /**
     * 赋值
     *
     * @param displayMetrics [DisplayMetrics]
     * @param density        [DisplayMetrics.density]
     * @param densityDpi     [DisplayMetrics.densityDpi]
     * @param scaledDensity  [DisplayMetrics.scaledDensity]
     * @param xdpi           [DisplayMetrics.xdpi]
     */
    private fun setDensity(
        displayMetrics: DisplayMetrics,
        density: Float,
        densityDpi: Int,
        scaledDensity: Float,
        xdpi: Float
    ) {

        displayMetrics.density = density
        displayMetrics.densityDpi = densityDpi
        displayMetrics.scaledDensity = scaledDensity
//        displayMetrics.xdpi = xdpi
    }

    /**
     * 给 [Configuration] 赋值
     *
     * @param activity       [Activity]
     * @param screenWidthDp  [Configuration.screenWidthDp]
     * @param screenHeightDp [Configuration.screenHeightDp]
     */
    fun setScreenSizeDp(
        activity: Activity? = null,
        application: Application? = null,
        screenWidthDp: Int,
        screenHeightDp: Int
    ) {
        activity?.let {
            setScreenSizeDp(
                it.resources.configuration,
                screenWidthDp,
                screenHeightDp
            )
        }

        application?.let {
            setScreenSizeDp(
                it.resources.configuration,
                screenWidthDp,
                screenHeightDp
            )
        }
    }

    /**
     * Configuration赋值
     *
     * @param configuration  [Configuration]
     * @param screenWidthDp  [Configuration.screenWidthDp]
     * @param screenHeightDp [Configuration.screenHeightDp]
     */
    private fun setScreenSizeDp(
        configuration: Configuration,
        screenWidthDp: Int,
        screenHeightDp: Int
    ) {
        configuration.screenWidthDp = screenWidthDp
        configuration.screenHeightDp = screenHeightDp
    }

    /**
     * 解决 MIUI 更改框架导致的 MIUI7 + Android5.1.1 上出现的失效问题 (以及极少数基于这部分 MIUI 去掉 ART 然后置入 XPosed 的手机)
     * 来源于: https://github.com/Firedamp/Rudeness/blob/master/rudeness-sdk/src/main/java/com/bulong/rudeness/RudenessScreenHelper.java#L61:5
     *
     * @param resources [Resources]
     * @param tmpMetricsField [Field]
     * @return [DisplayMetrics], 可能为 `null`
     */
    private fun getMetricsByMIUI(
        activityResources: Resources,
        appResources: Resources
    ): DisplayMetrics? {
        getMetricsFieldByMIUI(appResources)?.let {
            return try {
                it.get(activityResources) as DisplayMetrics
            } catch (e: Exception) {
                null
            }
        }
        return null
    }

    private fun getMetricsFieldByMIUI(resources: Resources): Field? {
        if (tmpMetricsField != null) return tmpMetricsField
        if ("MiuiResources" == resources.javaClass.simpleName || "XResources" == resources.javaClass.simpleName) {
            try {
                tmpMetricsField = Resources::class.java.getDeclaredField("mTmpMetrics")
                tmpMetricsField?.isAccessible = true
            } catch (e: Exception) {
                tmpMetricsField = null
            }
        }
        return tmpMetricsField
    }
}