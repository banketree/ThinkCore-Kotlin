package com.thinkcore.preference;

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.text.TextUtils
import androidx.annotation.RequiresApi
import com.thinkcore.encryption.TDes

/**
 * Created by banketree
 * on 2020/2/13.
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class LocalSharedPreferences {
    companion object {
        private var localSharedPreferences: LocalSharedPreferences? = null

        fun getLocalSharedPreferences(context: Context): LocalSharedPreferences {
            if (localSharedPreferences == null) {
                synchronized(LocalSharedPreferences::class.java) {
                    if (localSharedPreferences == null) {
                        localSharedPreferences =
                            LocalSharedPreferences(context, seedKey = "12345678a")
                    }
                }
            }

            return localSharedPreferences!!
        }
    }

    private val context: Context
    private var sp: SharedPreferences
    private var edit: SharedPreferences.Editor
    private val seedKey: String
    var isDes: Boolean = true
        private set

    /**
     * Create SharedPreferences by filename
     *
     * @param context
     * @param filename
     */
    @SuppressLint("WorldWriteableFiles")
    constructor(
        context: Context,
        filename: String,
        seedKey: String
    ) : this(
        context,
        context.getSharedPreferences(filename, Context.MODE_PRIVATE),
        seedKey
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
        sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context),
        seedKey: String = "123456"
    ) {
        this.context = context
        this.seedKey = seedKey
        this.sp = sp
        edit = sp.edit()

        if (seedKey.isNotEmpty()) {
            try {
                TDes.decrypt(seedKey, "test")
            } catch (ex: Exception) {
                isDes = false
            }
        }
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

    fun getKey(): String = seedKey
}

// String
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
inline fun LocalSharedPreferences.getValueByDes(key: String, defaultValue: String): String? {
    var valueAes = getValue(key, defaultValue)
    if (valueAes == defaultValue) return defaultValue
    if (TextUtils.isEmpty(valueAes)) return valueAes
    try {
        if (isDes && valueAes!!.isNotEmpty() && getKey().isNotEmpty()) {
            valueAes = TDes.decrypt(getKey(), valueAes)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return valueAes
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
inline fun LocalSharedPreferences.setValueByDes(key: String, value: String) {
    var valueAes = value
    try {
        if (isDes && getKey().isNotEmpty() && valueAes.isNotEmpty()) {
            valueAes = TDes.encrypt(getKey(), value)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    setValue(key, valueAes)
}


inline fun Context.getLocalSharedPreferences(): LocalSharedPreferences =
    LocalSharedPreferences.getLocalSharedPreferences(this)