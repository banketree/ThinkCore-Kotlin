package com.thinkcore.utils;

import android.database.Cursor;

/**
 * Cursor数据读取（内涵异常判断）
 */
public class TCursorUtils {
	public static String getString(Cursor cursor, String columnName) {
		try {
			int index = cursor.getColumnIndex(columnName);
			if (index == -1)
				return "";
			String result = cursor.getString(index);
			if (result == null)
				return "";
			return result;
		} catch (Exception e) {
		}
		return "";
	}

	public static int getInt(Cursor cursor, String columnName) {
		try {
			int index = cursor.getColumnIndex(columnName);
			if (index == -1)
				return -1;
			return cursor.getInt(index);
		} catch (Exception e) {
		}
		return -1;
	}

	public static long getLong(Cursor cursor, String columnName) {
		try {
			int index = cursor.getColumnIndex(columnName);
			if (index == -1)
				return 0;
			return cursor.getLong(index);
		} catch (Exception e) {
		}
		return 0;
	}

	public static float getFloat(Cursor cursor, String columnName) {
		try {
			int index = cursor.getColumnIndex(columnName);
			if (index == -1)
				return -1;
			return cursor.getFloat(index);
		} catch (Exception e) {
		}
		return -1;
	}
}
