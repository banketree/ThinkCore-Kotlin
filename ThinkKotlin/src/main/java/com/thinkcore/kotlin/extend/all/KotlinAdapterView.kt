package com.thinkcore.kotlin.extend.all

import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView.OnItemLongClickListener

public inline fun OnItemClickListener(crossinline action: (AdapterView<out Adapter?>?, View?, Int, Long) -> Unit): OnItemClickListener? {
    return object : OnItemClickListener {
        public override fun onItemClick(p0: AdapterView<out Adapter?>?, p1: View?, p2: Int, p3: Long) {
            action(p0, p1, p2, p3)
        }
    }
}

public inline fun OnItemLongClickListener(crossinline action: (AdapterView<out Adapter?>?, View?, Int, Long) -> Boolean): OnItemLongClickListener? {
    return object : OnItemLongClickListener {
        public override fun onItemLongClick(p0: AdapterView<out Adapter?>?, p1: View?, p2: Int, p3: Long): Boolean = action(p0, p1, p2, p3)
    }
}

public inline fun AdapterView<out Adapter?>.setOnItemClickListener(noinline action: (AdapterView<out Adapter?>?, View?, Int, Long) -> Unit): Unit {
    setOnItemClickListener(OnItemClickListener(action))
}

public inline fun AdapterView<out Adapter?>.setOnItemLongClickListener(noinline action: (AdapterView<out Adapter?>?, View?, Int, Long) -> Boolean): Unit {
    setOnItemLongClickListener(OnItemLongClickListener(action))
}
