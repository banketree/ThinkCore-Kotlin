package com.thinkcore.kotlin.extend.bundle.base

import android.os.Bundle
import com.thinkcore.kotlin.extend.TypeReader
import com.thinkcore.kotlin.extend.TypeWriter
import com.thinkcore.kotlin.extend.bundle.BundleExtra
import com.thinkcore.kotlin.extend.bundle.Generic

inline fun <T> BundleExtra.BooleanArray(
    crossinline reader: TypeReader<T, BooleanArray?>,
    crossinline writer: TypeWriter<T, BooleanArray?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getBooleanArray,
        Bundle::putBooleanArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.ByteArray(
    crossinline reader: TypeReader<T, ByteArray?>,
    crossinline writer: TypeWriter<T, ByteArray?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getByteArray,
        Bundle::putByteArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.CharArray(
    crossinline reader: TypeReader<T, CharArray?>,
    crossinline writer: TypeWriter<T, CharArray?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getCharArray,
        Bundle::putCharArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.IntArray(
    crossinline reader: TypeReader<T, IntArray?>,
    crossinline writer: TypeWriter<T, IntArray?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getIntArray,
        Bundle::putIntArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.ShortArray(
    crossinline reader: TypeReader<T, ShortArray?>,
    crossinline writer: TypeWriter<T, ShortArray?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getShortArray,
        Bundle::putShortArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.LongArray(
    crossinline reader: TypeReader<T, LongArray?>,
    crossinline writer: TypeWriter<T, LongArray?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getLongArray,
        Bundle::putLongArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.DoubleArray(
    crossinline reader: TypeReader<T, DoubleArray?>,
    crossinline writer: TypeWriter<T, DoubleArray?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getDoubleArray,
        Bundle::putDoubleArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.FloatArray(
    crossinline reader: TypeReader<T, FloatArray?>,
    crossinline writer: TypeWriter<T, FloatArray?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getFloatArray,
        Bundle::putFloatArray,
        reader,
        writer,
        name,
        customPrefix)
