package com.thinkcore.kotlin.extend.intent.base

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.thinkcore.kotlin.extend.TypeReader
import com.thinkcore.kotlin.extend.TypeWriter
import com.thinkcore.kotlin.extend.intent.Generic
import com.thinkcore.kotlin.extend.intent.IntentExtra
import java.io.Serializable

inline fun <T> IntentExtra.Bundle(
    crossinline reader: TypeReader<T, Bundle?>,
    crossinline writer: TypeWriter<T, Bundle?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Intent::getBundleExtra,
        Intent::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.CharSequence(
    crossinline reader: TypeReader<T, CharSequence?>,
    crossinline writer: TypeWriter<T, CharSequence?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Intent::getCharSequenceExtra,
        Intent::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.String(
    crossinline reader: TypeReader<T, String?>,
    crossinline writer: TypeWriter<T, String?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Intent::getStringExtra,
        Intent::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T, R : Parcelable> IntentExtra.Parcelable(
    crossinline reader: TypeReader<T, R?>,
    crossinline writer: TypeWriter<T, R?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        Intent::getParcelableExtra,
        Intent::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T, R : Serializable> IntentExtra.Serializable(
    crossinline reader: TypeReader<T, R?>,
    crossinline writer: TypeWriter<T, R?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        { name ->
            @Suppress("UNCHECKED_CAST")
            getSerializableExtra(name) as? R?
        },
        Intent::putExtra,
        reader,
        writer,
        name,
        customPrefix)
