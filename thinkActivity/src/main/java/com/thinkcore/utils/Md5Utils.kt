package com.thinkcore.utils

import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Md5Utils {

    fun encode(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest: ByteArray = instance.digest(text.toByteArray())
            var sb: StringBuffer = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                var i: Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0" + hexString
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun getFileMd5(file: File?): String {
        try {
            if (file != null && file.isFile) {
                var messageDigest: MessageDigest? = null
                var fileInputStream: FileInputStream? = null
                val buffer = ByteArray(1024)
                messageDigest = MessageDigest.getInstance("MD5")
                fileInputStream = FileInputStream(file)
                var len: Int
                while (fileInputStream.read(buffer, 0, 1024).also { len = it } != -1) {
                    messageDigest.update(buffer, 0, len)
                }
                val bigInt = BigInteger(1, messageDigest.digest())
                messageDigest = null
                fileInputStream.close()
                return bigInt.toString(16)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    fun getFileMd5(filePath: String?): String {
        return getFileMd5(File(filePath))
    }
}

inline fun File.getMd5() = Md5Utils.getFileMd5(this)

inline fun String.getMd5() = Md5Utils.encode(this)