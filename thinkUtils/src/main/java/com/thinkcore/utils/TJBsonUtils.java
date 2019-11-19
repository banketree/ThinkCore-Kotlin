package com.thinkcore.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @操作Json/Bson的辅助类
 */
public class TJBsonUtils {
	public static String getString(JSONObject jsonObject, String key) {
		String ret = "";
		if (jsonObject.has(key) && (jsonObject.isNull(key) == false)) {
			try {
				ret = jsonObject.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static int getInt(JSONObject jsonObject, String key) {
		int ret = 0;
		if (jsonObject.has(key) && (jsonObject.isNull(key) == false)) {
			try {
				ret = jsonObject.getInt(key);
			} catch (JSONException e) {
			}
		}
		return ret;
	}

	public static long getLong(JSONObject jsonObject, String key) {
		long ret = 0;
		if (jsonObject.has(key) && (jsonObject.isNull(key) == false)) {
			try {
				ret = jsonObject.getLong(key);
			} catch (JSONException e) {
			}
		}
		return ret;
	}

	public static double getDouble(JSONObject jsonObject, String key) {
		double ret = 0;
		if (jsonObject.has(key) && (jsonObject.isNull(key) == false)) {
			try {
				ret = jsonObject.getDouble(key);
			} catch (JSONException e) {
			}
		}
		return ret;
	}
}
