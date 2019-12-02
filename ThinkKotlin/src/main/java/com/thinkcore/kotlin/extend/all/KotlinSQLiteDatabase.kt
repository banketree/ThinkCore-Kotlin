package com.thinkcore.kotlin.extend.all

import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.content.ContentValues

public inline fun SQLiteDatabase.transaction(action: SQLiteDatabase.() -> Unit): Unit {
    beginTransaction()
    try {
        action()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}

public inline fun <T> SQLiteDatabase.query(distinct: Boolean, table: String?, columns: Array<String>? = null, selection: String? = null,
                                           selectionArgs: Array<String>? = null, groupBy: String? = null, having: String? = null, orderBy: String? = null,
                                           limit: String? = null, create: Cursor.() -> T): MutableCollection<T> {
    return query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit).mapAndClose(create)
}

public inline fun <T> SQLiteDatabase.queryWithFactory(cursorFactory: CursorFactory?, distinct: Boolean, table: String?,
                                                      columns: Array<String>? = null, selection: String? = null, selectionArgs: Array<String>? = null,
                                                      groupBy: String? = null, having: String? = null, orderBy: String? = null, limit: String? = null,
                                                      create: Cursor.() -> T): Collection<out T> {
    return queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit).mapAndClose(create)
}

public inline fun <T> SQLiteDatabase.query(table: String?, columns: Array<String>?, selection: String?,
                                           selectionArgs: Array<String>?, groupBy: String?, having: String?, orderBy: String?,
                                           limit: String?, create: Cursor.() -> T?): MutableCollection<T?> {
    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit).mapAndClose(create)
}

public inline fun <T> SQLiteDatabase.query(table: String?, columns: Array<String>?, selection: String?,
                                           selectionArgs: Array<String>?, groupBy: String?, having: String?, orderBy: String?,
                                           create: Cursor.() -> T): MutableCollection<T> {
    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy).mapAndClose(create)
}

public inline fun <T> SQLiteDatabase.rawQuery(sql: String?, selectionArgs: Array<String>?, create: Cursor.() -> T): MutableCollection<T> {
    return rawQuery(sql, selectionArgs).mapAndClose(create)
}

public inline fun <T> SQLiteDatabase.rawQueryWithFactory(cursorFactory: CursorFactory?, sql: String?, selectionArgs: Array<String>?,
                                                         editTable: String?, create: Cursor.() -> T): MutableCollection<T> {
    return rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable).mapAndClose(create)
}

public inline fun SQLiteDatabase.insert(table: String?, nullColumnHack: String?, create: ContentValues.() -> Unit): Long {
    val values = ContentValues()
    values.create()
    return insert(table, nullColumnHack, values)
}

public inline fun SQLiteDatabase.insertOrThrow(table: String?, nullColumnHack: String?, create: ContentValues.() -> Unit): Long {
    val values = ContentValues()
    values.create()
    return insertOrThrow(table, nullColumnHack, values)
}

public inline fun SQLiteDatabase.insertWithOnConflict(table: String?, nullColumnHack: String?, conflictAlgorithm: Int, create: ContentValues.() -> Unit): Long {
    val values = ContentValues()
    values.create()
    return insertWithOnConflict(table, nullColumnHack, values, conflictAlgorithm)
}

public inline fun SQLiteDatabase.update(table: String?, whereClause: String?, whereArgs: Array<String>?, create: ContentValues.() -> Unit): Int {
    val values = ContentValues()
    values.create()
    return update(table, values, whereClause, whereArgs)
}

public inline fun SQLiteDatabase.updateWithOnConflict(table: String?, whereClause: String?, whereArgs: Array<String>?, conflictAlgorithm: Int, create: ContentValues.() -> Unit): Int {
    val values = ContentValues()
    values.create()
    return updateWithOnConflict(table, values, whereClause, whereArgs, conflictAlgorithm)
}
