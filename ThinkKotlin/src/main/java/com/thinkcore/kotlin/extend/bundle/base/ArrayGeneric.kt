package com.thinkcore.kotlin.extend.bundle.base

import android.os.Bundle
import android.os.Parcelable
import com.thinkcore.kotlin.extend.TypeReader
import com.thinkcore.kotlin.extend.TypeWriter
import com.thinkcore.kotlin.extend.bundle.BundleExtra
import com.thinkcore.kotlin.extend.bundle.Generic

inline fun <T> BundleExtra.ParcelableArray(
    crossinline reader: TypeReader<T, Array<Parcelable?>?>,
    crossinline writer: TypeWriter<T, Array<Parcelable?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getParcelableArray,
        Bundle::putParcelableArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.CharSequenceArray(
    crossinline reader: TypeReader<T, Array<CharSequence?>?>,
    crossinline writer: TypeWriter<T, Array<CharSequence?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getCharSequenceArray,
        Bundle::putCharSequenceArray,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.StringArray(
    crossinline reader: TypeReader<T, Array<String?>?>,
    crossinline writer: TypeWriter<T, Array<String?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getStringArray,
        Bundle::putCharSequenceArray,
        reader,
        writer,
        name,
        customPrefix)
