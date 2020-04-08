package com.thinkcore.utils

import android.content.SharedPreferences
import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.thinkcore.utils.LocalSharedPreferences.Companion.localSharedPreferences


/**
 * Created by banketree
 * on 2020/2/13.
 */
class LocalSharedPreferences {
    companion object {
        private var localSharedPreferences: LocalSharedPreferences? = null

        fun getLocalSharedPreferences(context: Context): LocalSharedPreferences {
            if (localSharedPreferences == null) {
                synchronized(LocalSharedPreferences::class.java) {
                    if (localSharedPreferences == null) {
                        localSharedPreferences = LocalSharedPreferences(context)
                    }
                }
            }

            return localSharedPreferences!!
        }
    }

    private val context: Context
    private var sp: SharedPreferences
    private var edit: SharedPreferences.Editor

    /**
     * Create SharedPreferences by filename
     *
     * @param context
     * @param filename
     */
    @SuppressLint("WorldWriteableFiles")
    constructor(context: Context, filename: String) : this(
        context,
        context.getSharedPreferences(filename, Context.MODE_PRIVATE)
    )

    /**
     * Create SharedPreferences by SharedPreferences
     *
     * @param context
     * @param sp
     */
    @JvmOverloads
    constructor(
        context: Context,
        sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    ) {
        this.context = context
        this.sp = sp
        edit = sp.edit()
    }

    // Set

    // Boolean
    fun setValue(key: String, value: Boolean) {
        edit.putBoolean(key, value)
        edit.commit()
    }

    fun setValue(resKey: Int, value: Boolean) {
        setValue(this.context.getString(resKey), value)
    }

    // Float
    fun setValue(key: String, value: Float) {
        edit.putFloat(key, value)
        edit.commit()
    }

    fun setValue(resKey: Int, value: Float) {
        setValue(this.context.getString(resKey), value)
    }

    // Integer
    fun setValue(key: String, value: Int) {
        edit.putInt(key, value)
        edit.commit()
    }

    fun setValue(resKey: Int, value: Int) {
        setValue(this.context.getString(resKey), value)
    }

    // Long
    fun setValue(key: String, value: Long) {
        edit.putLong(key, value)
        edit.commit()
    }

    fun setValue(resKey: Int, value: Long) {
        setValue(this.context.getString(resKey), value)
    }

    // String
    fun setValue(key: String, value: String) {
        edit.putString(key, value)
        edit.commit()
    }

    fun setValue(resKey: Int, value: String) {
        setValue(this.context.getString(resKey), value)
    }

    // Get

    // Boolean
    fun getValue(key: String, defaultValue: Boolean): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    fun getValue(resKey: Int, defaultValue: Boolean): Boolean {
        return getValue(this.context.getString(resKey), defaultValue)
    }

    // Float
    fun getValue(key: String, defaultValue: Float): Float {
        return sp.getFloat(key, defaultValue)
    }

    fun getValue(resKey: Int, defaultValue: Float): Float {
        return getValue(this.context.getString(resKey), defaultValue)
    }

    // Integer
    fun getValue(key: String, defaultValue: Int): Int {
        return sp.getInt(key, defaultValue)
    }

    fun getValue(resKey: Int, defaultValue: Int): Int {
        return getValue(this.context.getString(resKey), defaultValue)
    }

    // Long
    fun getValue(key: String, defaultValue: Long): Long {
        return sp.getLong(key, defaultValue)
    }

    fun getValue(resKey: Int, defaultValue: Long): Long {
        return getValue(this.context.getString(resKey), defaultValue)
    }

    // String
    fun getValue(key: String, defaultValue: String): String? {
        return sp.getString(key, defaultValue)
    }

    fun getValue(resKey: Int, defaultValue: String): String? {
        return getValue(this.context.getString(resKey), defaultValue)
    }

    // Delete
    fun remove(key: String) {
        edit.remove(key)
        edit.commit()
    }

    fun clear() {
        edit.clear()
        edit.commit()
    }
}

inline fun Context.getLocalSharedPreferences(): LocalSharedPreferences =
    LocalSharedPreferences.getLocalSharedPreferences(this)