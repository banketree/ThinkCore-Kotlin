package com.thinkcore.kotlin.extend.intent.base

import com.thinkcore.kotlin.extend.TypeReader
import com.thinkcore.kotlin.extend.TypeWriter
import com.thinkcore.kotlin.extend.intent.Generic
import com.thinkcore.kotlin.extend.intent.IntentExtra
import com.thinkcore.kotlin.extend.intent.getBooleanExtra
import com.thinkcore.kotlin.extend.intent.getByteExtra
import com.thinkcore.kotlin.extend.intent.getCharExtra
import com.thinkcore.kotlin.extend.intent.getDoubleExtra
import com.thinkcore.kotlin.extend.intent.getFloatExtra
import com.thinkcore.kotlin.extend.intent.getIntExtra
import com.thinkcore.kotlin.extend.intent.getLongExtra
import com.thinkcore.kotlin.extend.intent.getShortExtra
import com.thinkcore.kotlin.extend.intent.putExtra

inline fun <T> IntentExtra.Boolean(
    crossinline reader: TypeReader<T, Boolean?>,
    crossinline writer: TypeWriter<T, Boolean?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getBooleanExtra,
        ::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.Int(
    crossinline reader: TypeReader<T, Int?>,
    crossinline writer: TypeWriter<T, Int?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getIntExtra,
        ::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.Long(
    crossinline reader: TypeReader<T, Long?>,
    crossinline writer: TypeWriter<T, Long?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getLongExtra,
        ::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.Short(
    crossinline reader: TypeReader<T, Short?>,
    crossinline writer: TypeWriter<T, Short?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getShortExtra,
        ::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.Double(
    crossinline reader: TypeReader<T, Double?>,
    crossinline writer: TypeWriter<T, Double?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getDoubleExtra,
        ::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.Float(
    crossinline reader: TypeReader<T, Float?>,
    crossinline writer: TypeWriter<T, Float?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getFloatExtra,
        ::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.Char(
    crossinline reader: TypeReader<T, Char?>,
    crossinline writer: TypeWriter<T, Char?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getCharExtra,
        ::putExtra,
        reader,
        writer,
        name,
        customPrefix)

inline fun <T> IntentExtra.Byte(
    crossinline reader: TypeReader<T, Byte?>,
    crossinline writer: TypeWriter<T, Byte?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        ::getByteExtra,
        ::putExtra,
        reader,
        writer,
        name,
        customPrefix)
