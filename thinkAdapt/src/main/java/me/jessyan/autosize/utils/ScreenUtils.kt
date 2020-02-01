package me.jessyan.autosize.utils


import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager


object ScreenUtils {
    val statusBarHeight: Int
        get() {
            var result = 0
            try {
                val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    result = Resources.getSystem().getDimensionPixelSize(resourceId)
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }

            return result
        }

    /**
     * 获取当前的屏幕尺寸
     *
     * @param context [Context]
     * @return 屏幕尺寸
     */
    fun getScreenSize(context: Context): IntArray {
        val size = IntArray(2)

        val w = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val d = w.defaultDisplay
        val metrics = DisplayMetrics()
        d.getMetrics(metrics)

        size[0] = metrics.widthPixels
        size[1] = metrics.heightPixels
        return size
    }

    /**
     * 获取原始的屏幕尺寸
     *
     * @param context [Context]
     * @return 屏幕尺寸
     */
    fun getRawScreenSize(context: Context): IntArray {
        val size = IntArray(2)

        val w = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val d = w.defaultDisplay
        val metrics = DisplayMetrics()
        d.getMetrics(metrics)
        // since SDK_INT = 1;
        var widthPixels = metrics.widthPixels
        var heightPixels = metrics.heightPixels

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                widthPixels = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
                heightPixels = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
            } catch (ignored: Exception) {
            }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                val realSize = Point()
                Display::class.java.getMethod("getRealSize", Point::class.java).invoke(d, realSize)
                widthPixels = realSize.x
                heightPixels = realSize.y
            } catch (ignored: Exception) {
            }

        size[0] = widthPixels
        size[1] = heightPixels
        return size
    }

    fun getHeightOfNavigationBar(context: Context): Int {
        //如果小米手机开启了全面屏手势隐藏了导航栏则返回 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(context.contentResolver, "force_fsg_nav_bar", 0) != 0) {
                return 0
            }
        }

        val realHeight = getRawScreenSize(context)[1]
        val displayHeight = getScreenSize(context)[1]
        return realHeight - displayHeight
    }
}
