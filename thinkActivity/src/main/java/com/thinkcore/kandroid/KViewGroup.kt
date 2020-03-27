package com.thinkcore.kandroid

import android.view.ViewGroup

inline val ViewGroup.views
    get() = (0 until childCount).map { getChildAt(it) }