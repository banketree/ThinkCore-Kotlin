package com.thinkcore.kotlin.extend.bundle

import android.os.Bundle
import com.thinkcore.kotlin.extend.ExtraReader
import com.thinkcore.kotlin.extend.ExtraWriter
import com.thinkcore.kotlin.extend.PropertyDelegate
import com.thinkcore.kotlin.extend.TypeReader
import com.thinkcore.kotlin.extend.TypeWriter

typealias BundlePropertyDelegate<T> = PropertyDelegate<Bundle, T>

/**
 * Helper to create an [BundlePropertyDelegate].
 *
 * @param R The raw type that will be read/written directly to the [Bundle].
 * @param T The transformed type (from [R]) that will be read/written from the property.
 * @param typeReader Transforms the raw type [R] in the property type [T] when reading from the [Bundle].
 * @param typeWriter Transforms the property type [T] in the raw type [R] when writing to the [Bundle].
 * @param bundleReader Reads the value from the [Bundle].
 * @param bundleWriter Writes the value to the [Bundle].
 * @param name An optional name for the [Bundle] extra. If missing, a compile-time constant will be used equal to the qualified name of the class
 * in which the property is declared plus the name of the property itself.
 * @param customPrefix An optional prefix for the [Bundle] extra, to be used before the property name.
 * Note that this is ignored if [name] is present.
 */
inline fun <T, R> BundleExtra.Generic(
    crossinline bundleReader: ExtraReader<Bundle, R>,
    crossinline bundleWriter: ExtraWriter<Bundle, R>,
    crossinline typeReader: TypeReader<T, R>,
    crossinline typeWriter: TypeWriter<T, R>,
    name: String? = null,
    customPrefix: String? = null
): BundlePropertyDelegate<T> =
    PropertyDelegate(bundleReader, bundleWriter, typeReader, typeWriter, name, customPrefix)
