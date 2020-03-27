package com.thinkcore.kotlin

import java.io.File

object KFile {
    tailrec fun getFileSize(file: File): Long {
        if (file.isFile) return file.length()
        val children = file.listFiles()
        var total: Long = 0
        children?.let {
            for (child in children) total += getFileSize(child)
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

inline fun File.safeDelete() {
    KFile.safeDeleteFile(this)
}