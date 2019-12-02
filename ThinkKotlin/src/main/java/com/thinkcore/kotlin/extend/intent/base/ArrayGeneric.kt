package com.thinkcore.kotlin.extend.intent.base

import android.content.Intent
import android.os.Parcelable
import com.thinkcore.kotlin.extend.TypeReader
import com.thinkcore.kotlin.extend.TypeWriter
import com.thinkcore.kotlin.extend.intent.Generic
import com.thinkcore.kotlin.extend.intent.IntentExtra

inline fun <T> IntentExtra.ParcelableArray(
    crossinline reader: TypeReader<T, Array<Parcelable?>?>,
    crossinline writer: TypeWriter<T, Array<Parcelable?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Intent::getParcelableArrayExtra,
        Intent::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.CharSequenceArray(
    crossinline reader: TypeReader<T, Array<CharSequence?>?>,
    crossinline writer: TypeWriter<T, Array<CharSequence?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Intent::getCharSequenceArrayExtra,
        Intent::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.StringArray(
    crossinline reader: TypeReader<T, Array<String?>?>,
    crossinline writer: TypeWriter<T, Array<String?>?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Intent::getStringArrayExtra,
        Intent::putExtra,
        reader,
        writer,
        name,
        customPrefix)
