package com.thinkcore.kotlin.extend.bundle.base

import android.os.Bundle
import android.os.Parcelable
import com.thinkcore.kotlin.extend.TypeReader
import com.thinkcore.kotlin.extend.TypeWriter
import com.thinkcore.kotlin.extend.bundle.BundleExtra
import com.thinkcore.kotlin.extend.bundle.Generic
import java.util.ArrayList

inline fun <T> BundleExtra.IntArrayList(
    crossinline reader: TypeReader<T, ArrayList<Int?>?>,
    crossinline writer: TypeWriter<T, ArrayList<Int?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getIntegerArrayList,
        Bundle::putIntegerArrayList,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.CharSequenceArrayList(
    crossinline reader: TypeReader<T, ArrayList<CharSequence?>?>,
    crossinline writer: TypeWriter<T, ArrayList<CharSequence?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getCharSequenceArrayList,
        Bundle::putCharSequenceArrayList,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> BundleExtra.StringArrayList(
    crossinline reader: TypeReader<T, ArrayList<String?>?>,
    crossinline writer: TypeWriter<T, ArrayList<String?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Bundle::getStringArrayList,
        Bundle::putStringArrayList,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T, R : Parcelable> BundleExtra.ParcelableArrayList(
    crossinline reader: TypeReader<T, ArrayList<R?>?>,
    crossinline writer: TypeWriter<T, ArrayList<R?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        { getParcelableArrayList(it) },
        Bundle::putParcelableArrayList,
        reader,
        writer,
        name,
        customPrefix)
