package com.thinkcore.kandroid

import android.util.Base64
import java.io.*


/**
 * @param
 * @return
 * @author banketree
 * @time 2020/4/5 14:19
 * @description
 * File转成编码成BASE64
 */
inline fun File.toBase64(): String {
    var base64: String = ""
    if (!exists()) return base64
    var inputStream: InputStream? = null
    try {
        inputStream = FileInputStream(this)
        val bytes = ByteArray(this.length().toInt())
        inputStream.read(bytes)
        base64 = Base64.encodeToString(bytes, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return base64
}


/**
 * @param
 * @return
 * @author banketree
 * @time 2020/4/5 14:19
 * @description
 * BASE64解码成File文件
 */
inline fun String.base64ToFile(filePath: String): File {
    var file: File = File(filePath)
    if (file.parentFile != null) {
        file.parentFile.mkdirs()
    }
    var bos: BufferedOutputStream? = null
    var fos: FileOutputStream? = null
    try {
        val bytes: ByteArray = Base64.decode(this.toString(), Base64.DEFAULT)
        fos = FileOutputStream(file)
        bos = BufferedOutputStream(fos)
        bos.write(bytes)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    } finally {
        if (bos != null) {
            try {
                bos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (fos != null) {
            try {
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    return file
}


object KFile {
    tailrec fun getFileSize(file: File): Long {
        if (file.isFile) return file.length()
        val children = file.listFiles()
        var total: Long = 0
        children?.let {
            for (child in children) total += getFileSize(
                child
            )
        }
        return total
    }

    tailrec fun safeDeleteFile(file: File, isDirDel: Boolean = false) {
        if (file.isFile) {
            if (file.exists()) {
                file.delete()
            }
            return
        }
        val children = file.listFiles()
        children?.let {
            for (child in children) {
                safeDeleteFile(child)
            }
        }

        if (isDirDel && file.exists()) {
            file.delete()
        }
    }
}

inline fun File.getFileSize(): Long {
    return KFile.getFileSize(this)
}

inline fun File.safeDelete(isDirDel: Boolean = false) {
    KFile.safeDeleteFile(this, isDirDel)
}

