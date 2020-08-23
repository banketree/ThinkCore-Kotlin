package com.thinkcore.zip

import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.AesKeyStrength
import net.lingala.zip4j.model.enums.CompressionLevel
import net.lingala.zip4j.model.enums.CompressionMethod
import net.lingala.zip4j.model.enums.EncryptionMethod
import java.io.File

/**
 * @author banketree
 * @time 2020/1/3 16:56
 * @description
 */
object ZipUtil {
    @Throws(Exception::class)
    fun zip(
        filePath: String,
        zipPath: String,
        password: String? = null
    ) {
        val zipFile = ZipFile(zipPath)
        val parameters = ZipParameters()
        parameters.compressionMethod =
            CompressionMethod.getCompressionMethodFromCode(CompressionMethod.DEFLATE.code)
        parameters.compressionLevel = CompressionLevel.NORMAL
        password?.let {
            if (it.isNotEmpty()) {
                parameters.isEncryptFiles = true;
                parameters.encryptionMethod = EncryptionMethod.ZIP_STANDARD;
                parameters.aesKeyStrength = AesKeyStrength.KEY_STRENGTH_128;
                zipFile.setPassword(it.toCharArray())
            }
        }

        val targetFile = File(filePath)
        if (targetFile.isFile) {
            zipFile.addFile(targetFile, parameters)
        } else if (targetFile.isDirectory) {
            zipFile.addFolder(targetFile, parameters)
        }
    }

    @Throws(Exception::class)
    fun unzip(
        zipPath: String,
        dirPath: String,
        password: String?
    ) {
        val zipFile = ZipFile(zipPath)
        if (zipFile.isEncrypted && password != null) {
            zipFile.setPassword(password.toCharArray())
        }
        zipFile.extractAll(dirPath)
    }
}

@Throws(Exception::class)
inline fun File.zip(
    filePath: String,
    zipPath: String,
    password: String? = null
) = ZipUtil.zip(filePath, zipPath,password)


@Throws(Exception::class)
inline fun File.unzip(
    zipPath: String,
    dirPath: String,
    password: String? = null
) = ZipUtil.unzip(zipPath, dirPath, password)
