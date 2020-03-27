package com.thinkcore.kotlin

import android.graphics.Bitmap
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


inline fun <T : Bitmap?, R> T.use(block: (T) -> R): R {
    var recycled = false
    try {
        return block(this)
    } catch (e: Exception) {
        recycled = true
        try {
            this?.recycle()
        } catch (exception: Exception) {
        }
        throw e
    } finally {
        if (!recycled) {
            this?.recycle()
        }
    }
}

/** 保存方法  */
inline fun Bitmap.saveBitmap(path: String): Boolean {
    val f = File(path)
    if (f.parentFile != null) {
        f.parentFile.mkdirs()
    }

    if (!f.exists()) {
        f.createNewFile()
    }
    try {
        val out = FileOutputStream(f)
        compress(Bitmap.CompressFormat.PNG, 90, out)
        out.flush()
        out.close()
        return true
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return false
}