package com.thinkcore.kotlin.extend.all

import android.database.Cursor
import java.util.LinkedList

public inline fun <T> Cursor?.map(transform: Cursor.() -> T): MutableCollection<T> {
    return mapTo(LinkedList<T>(), transform)
}

public inline fun <T, C: MutableCollection<T>> Cursor?.mapTo(result: C, transform: Cursor.() -> T): C {
    return if (this == null) result else {
        if (moveToFirst())
            do {
                result.add(transform())
            } while (moveToNext())
        result
    }
}

public inline fun <T> Cursor?.mapAndClose(create: Cursor.() -> T): MutableCollection<T> {
    try {
        return map(create)
    } finally {
        this?.close()
    }
}
