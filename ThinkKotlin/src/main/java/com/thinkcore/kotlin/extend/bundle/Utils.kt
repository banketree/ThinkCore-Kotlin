@file:Suppress("NOTHING_TO_INLINE")

package com.thinkcore.kotlin.extend.bundle

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.thinkcore.kotlin.extend.putExtra
import com.thinkcore.kotlin.extend.readBoolean
import com.thinkcore.kotlin.extend.readByte
import com.thinkcore.kotlin.extend.readChar
import com.thinkcore.kotlin.extend.readDouble
import com.thinkcore.kotlin.extend.readFloat
import com.thinkcore.kotlin.extend.readInt
import com.thinkcore.kotlin.extend.readLong
import com.thinkcore.kotlin.extend.readShort

@TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun getBoolean(
    receiver: Bundle,
    name: String
) =
    receiver.readBoolean(
        Bundle::containsKey,
        Bundle::getBoolean,
        name)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun getInt(
    receiver: Bundle,
    name: String
) =
    receiver.readInt(
        Bundle::containsKey,
        Bundle::getInt,
        name)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun getLong(
    receiver: Bundle,
    name: String
) =
    receiver.readLong(
        Bundle::containsKey,
        Bundle::getLong,
        name)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun getShort(
    receiver: Bundle,
    name: String
) =
    receiver.readShort(
        Bundle::containsKey,
        Bundle::getShort,
        name)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun getDouble(
    receiver: Bundle,
    name: String
) =
    receiver.readDouble(
        Bundle::containsKey,
        Bundle::getDouble,
        name)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun getFloat(
    receiver: Bundle,
    name: String
) =
    receiver.readFloat(
        Bundle::containsKey,
        Bundle::getFloat,
        name)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun getChar(
    receiver: Bundle,
    name: String
) =
    receiver.readChar(
        Bundle::containsKey,
        Bundle::getChar,
        name)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun getByte(
    receiver: Bundle,
    name: String
) =
    receiver.readByte(
        Bundle::containsKey,
        Bundle::getByte,
        name)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
@PublishedApi internal inline fun putBoolean(
    receiver: Bundle,
    name: String,
    value: Boolean?
) =
    receiver.putExtra(
        Bundle::remove,
        Bundle::putBoolean,
        name,
        value)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun putInt(
    receiver: Bundle,
    name: String,
    value: Int?
) =
    receiver.putExtra(
        Bundle::remove,
        Bundle::putInt,
        name,
        value)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun putLong(
    receiver: Bundle,
    name: String,
    value: Long?
) =
    receiver.putExtra(
        Bundle::remove,
        Bundle::putLong,
        name,
        value)

@PublishedApi internal inline fun putShort(
    receiver: Bundle,
    name: String,
    value: Short?
) =
    receiver.putExtra(
        Bundle::remove,
        Bundle::putShort,
        name,
        value)

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@PublishedApi internal inline fun putDouble(
    receiver: Bundle,
    name: String,
    value: Double?
) =
    receiver.putExtra(
        Bundle::remove,
        Bundle::putDouble,
        name,
        value)

@PublishedApi internal inline fun putFloat(
    receiver: Bundle,
    name: String,
    value: Float?
) =
    receiver.putExtra(
        Bundle::remove,
        Bundle::putFloat,
        name,
        value)

@PublishedApi internal inline fun putChar(
    receiver: Bundle,
    name: String,
    value: Char?
) =
    receiver.putExtra(
        Bundle::remove,
        Bundle::putChar,
        name,
        value)

@PublishedApi internal inline fun putByte(
    receiver: Bundle,
    name: String,
    value: Byte?
) =
    receiver.putExtra(
        Bundle::remove,
        Bundle::putByte,
        name,
        value)
