package com.thinkcore.kotlin

import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import android.os.Environment.MEDIA_MOUNTED_READ_ONLY

inline val Environment.isExternalStorageWritable
    get() = Environment.getExternalStorageState() == MEDIA_MOUNTED

inline val Environment.isExternalStorageReadable
    get() = Environment.getExternalStorageState() == MEDIA_MOUNTED_READ_ONLY || isExternalStorageWritable