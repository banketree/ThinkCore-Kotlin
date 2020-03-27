package com.thinkcore.kotlin

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.ColorInt


inline fun ImageView.showMask(@ColorInt color: Int = Color.parseColor("#80ffffff")) {
    setColorFilter(
        color,
        PorterDuff.Mode.SCREEN
    )
}

inline fun ImageView.hideMask() {
    clearColorFilter()
}