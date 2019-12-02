@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kotlin.extend.intent

import android.content.Intent
import com.thinkcore.kotlin.extend.putExtra
import com.thinkcore.kotlin.extend.readBoolean
import com.thinkcore.kotlin.extend.readByte
import com.thinkcore.kotlin.extend.readChar
import com.thinkcore.kotlin.extend.readDouble
import com.thinkcore.kotlin.extend.readFloat
import com.thinkcore.kotlin.extend.readInt
import com.thinkcore.kotlin.extend.readLong
import com.thinkcore.kotlin.extend.readShort

@PublishedApi internal inline fun getBooleanExtra(
    receiver: Intent,
    name: String
) =
    receiver.readBoolean(
        Intent::hasExtra,
        Intent::getBooleanExtra,
        name)

@PublishedApi internal inline fun getIntExtra(
    receiver: Intent,
    name: String
) =
    receiver.readInt(
        Intent::hasExtra,
        Intent::getIntExtra,
        name)

@PublishedApi internal inline fun getLongExtra(
    receiver: Intent,
    name: String
) =
    receiver.readLong(
        Intent::hasExtra,
        Intent::getLongExtra,
        name)

@PublishedApi internal inline fun getShortExtra(
    receiver: Intent,
    name: String
) =
    receiver.readShort(
        Intent::hasExtra,
        Intent::getShortExtra,
        name)

@PublishedApi internal inline fun getDoubleExtra(
    receiver: Intent,
    name: String
) =
    receiver.readDouble(
        Intent::hasExtra,
        Intent::getDoubleExtra,
        name)

@PublishedApi internal inline fun getFloatExtra(
    receiver: Intent,
    name: String
) =
    receiver.readFloat(
        Intent::hasExtra,
        Intent::getFloatExtra,
        name)

@PublishedApi internal inline fun getCharExtra(
    receiver: Intent,
    name: String
) =
    receiver.readChar(
        Intent::hasExtra,
        Intent::getCharExtra,
        name)

@PublishedApi internal inline fun getByteExtra(
    receiver: Intent,
    name: String
) =
    receiver.readByte(
        Intent::hasExtra,
        Intent::getByteExtra,
        name)

@PublishedApi internal inline fun putExtra(
    receiver: Intent,
    name: String,
    value: Boolean?
) =
    receiver.putExtra(
        Intent::removeExtra,
        Intent::putExtra,
        name,
        value)

@PublishedApi internal inline fun putExtra(
    receiver: Intent,
    name: String,
    value: Int?
) =
    receiver.putExtra(
        Intent::removeExtra,
        Intent::putExtra,
        name,
        value)

@PublishedApi internal inline fun putExtra(
    receiver: Intent,
    name: String,
    value: Long?
) =
    receiver.putExtra(
        Intent::removeExtra,
        Intent::putExtra,
        name,
        value)

@PublishedApi internal inline fun putExtra(
    receiver: Intent,
    name: String,
    value: Short?
) =
    receiver.putExtra(
        Intent::removeExtra,
        Intent::putExtra,
        name,
        value)

@PublishedApi internal inline fun putExtra(
    receiver: Intent,
    name: String,
    value: Double?
) =
    receiver.putExtra(
        Intent::removeExtra,
        Intent::putExtra,
        name,
        value)

@PublishedApi internal inline fun putExtra(
    receiver: Intent,
    name: String,
    value: Float?
) =
    receiver.putExtra(
        Intent::removeExtra,
        Intent::putExtra,
        name,
        value)

@PublishedApi internal inline fun putExtra(
    receiver: Intent,
    name: String,
    value: Char?
) =
    receiver.putExtra(
        Intent::removeExtra,
        Intent::putExtra,
        name,
        value)

@PublishedApi internal inline fun putExtra(
    receiver: Intent,
    name: String,
    value: Byte?
) =
    receiver.putExtra(
        Intent::removeExtra,
        Intent::putExtra,
        name,
        value)
