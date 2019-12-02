package com.thinkcore.kotlin.extend.bundle.base

import com.thinkcore.kotlin.extend.TypeReader
import com.thinkcore.kotlin.extend.TypeWriter
import com.thinkcore.kotlin.extend.bundle.BundleExtra
import com.thinkcore.kotlin.extend.bundle.Generic
import com.thinkcore.kotlin.extend.bundle.getBoolean
import com.thinkcore.kotlin.extend.bundle.getByte
import com.thinkcore.kotlin.extend.bundle.getChar
import com.thinkcore.kotlin.extend.bundle.getDouble
import com.thinkcore.kotlin.extend.bundle.getFloat
import com.thinkcore.kotlin.extend.bundle.getInt
import com.thinkcore.kotlin.extend.bundle.getLong
import com.thinkcore.kotlin.extend.bundle.getShort
import com.thinkcore.kotlin.extend.bundle.putBoolean
import com.thinkcore.kotlin.extend.bundle.putByte
import com.thinkcore.kotlin.extend.bundle.putChar
import com.thinkcore.kotlin.extend.bundle.putDouble
import com.thinkcore.kotlin.extend.bundle.putFloat
import com.thinkcore.kotlin.extend.bundle.putInt
import com.thinkcore.kotlin.extend.bundle.putLong
import com.thinkcore.kotlin.extend.bundle.putShort

inline fun <T> BundleExtra.Boolean(
    crossinline reader: TypeReader<T, Boolean?>,
    crossinline writer: TypeWriter<T, Boolean?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getBoolean,
        ::putBoolean,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.Int(
    crossinline reader: TypeReader<T, Int?>,
    crossinline writer: TypeWriter<T, Int?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getInt,
        ::putInt,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.Long(
    crossinline reader: TypeReader<T, Long?>,
    crossinline writer: TypeWriter<T, Long?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getLong,
        ::putLong,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.Short(
    crossinline reader: TypeReader<T, Short?>,
    crossinline writer: TypeWriter<T, Short?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getShort,
        ::putShort,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.Double(
    crossinline reader: TypeReader<T, Double?>,
    crossinline writer: TypeWriter<T, Double?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getDouble,
        ::putDouble,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.Float(
    crossinline reader: TypeReader<T, Float?>,
    crossinline writer: TypeWriter<T, Float?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getFloat,
        ::putFloat,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.Char(
    crossinline reader: TypeReader<T, Char?>,
    crossinline writer: TypeWriter<T, Char?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getChar,
        ::putChar,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.Byte(
    crossinline reader: TypeReader<T, Byte?>,
    crossinline writer: TypeWriter<T, Byte?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getByte,
        ::putByte,
        reader,
        writer,
        name,
        customPrefix)
