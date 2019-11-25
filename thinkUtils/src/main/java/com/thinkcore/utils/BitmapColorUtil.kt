package com.wym.autostart.utils

import android.graphics.Bitmap
import android.graphics.Color

/**
 * bitmap颜色转换
 * Created by unknow on 2019/1/7.
 */
object BitmapColorUtil {

    fun replaceColor(bm: Bitmap, fillStartColor: Int, fillEndColor: Int): Bitmap {
        val newBitmap = bm.copy(Bitmap.Config.ARGB_8888, true)
        bm.recycle()
        fun getNeedColor(ratio: Float, alpha: Int = 255): Int {
            val sRed = Color.red(fillStartColor)
            val sGreen = Color.green(fillStartColor)
            val sBlue = Color.blue(fillStartColor)
            val eRed = Color.red(fillEndColor)
            val eGreen = Color.green(fillEndColor)
            val eBlue = Color.blue(fillEndColor)
            return Color.argb(
                    alpha,
                    (sRed * (1 - ratio) + eRed * ratio).toInt(),
                    (sGreen * (1 - ratio) + eGreen * ratio).toInt(),
                    (sBlue * (1 - ratio) + eBlue * ratio).toInt()
            )
        }

        fun getAlpha(pixel: Int) = pixel and -0x1000000 shr 24

        for (i in 0 until newBitmap.width) {
            back@ for (j in 0 until newBitmap.height) {
                val color = newBitmap.getPixel(i, j)
                if (color == 0) continue@back
                val needColor = getNeedColor(i.toFloat() / newBitmap.width, getAlpha(color))
                newBitmap.setPixel(i, j, needColor)
            }
        }
        return newBitmap
    }

}