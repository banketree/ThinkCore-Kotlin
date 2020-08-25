package com.thinkcore.storage.kandroid

import android.content.Context
import com.thinkcore.storage.TFilePath
import com.thinkcore.storage.TStorageUtils

inline fun Context.getExternalAppDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.externalAppDir
}

inline fun Context.getExternalAudioDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.externalAudioDir
}

inline fun Context.getExternalCacheDirString(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.externalCacheDir
}

inline fun Context.getExternalDownloadDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.externalDownloadDir
}

inline fun Context.getExternalImageDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.externalImageDir
}

inline fun Context.getExternalVideoDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.externalVideoDir
}

inline fun Context.getExternalLogDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.externalLogDir
}

inline fun Context.getInterAppDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.interAppDir
}

inline fun Context.getInterAudioDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.interAudioDir
}

inline fun Context.getInterCacheDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.interCacheDir
}

inline fun Context.getInterImageDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.interImageDir
}

inline fun Context.getInterDownloadDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.interDownloadDir
}

inline fun Context.getInterVideoDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.interVideoDir
}

inline fun Context.getInterLogDir(): String {
    val filePath = TFilePath(this.applicationContext)
    return filePath.interLogDir
}

inline fun Context.isExternalStoragePresent(): Boolean {
    return TStorageUtils.isExternalStoragePresent() && hasExternalStoragePermission()
}

inline fun Context.hasExternalStoragePermission(): Boolean {
    return TStorageUtils.hasExternalStoragePermission(this.applicationContext)
}