package com.thinkcore.kandroid

import android.view.ViewGroup

inline val ViewGroup.views
    get() = (0..childCount - 1).map { getChildAt(it) }