/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinkcore.utils;

import android.text.TextUtils;

import java.io.File;
import java.net.URL;
import java.util.UUID;

/**
 * @url转换工具
 */
public class TUrlParserUtils {
	/**
	 * url转换工具,确保url为绝对路径
	 * 
	 * @param baseUrl
	 *            url的根域名
	 * @param url
	 *            需要转换的url(绝对路径，或相对路径)
	 * @return 返回绝对路径url
	 */
	public static String urlParse(String baseUrl, String url) {
		String returnUrl = "";
		try {
			URL absoluteUrl = new URL(baseUrl);
			URL parseUrl = new URL(absoluteUrl, url);
			returnUrl = parseUrl.toString();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return returnUrl;
	}

	public static String getFileNameFromUrl(String url) {
		// 通过 ‘？’ 和 ‘/’ 判断文件名
		int index = url.lastIndexOf('?');
		String filename;
		if (index > 1) {
			filename = url.substring(url.lastIndexOf('/') + 1, index);
		} else {
			filename = url.substring(url.lastIndexOf('/') + 1);
		}

		if (TextUtils.isEmpty(filename)) {// 如果获取不到文件名称
			filename = UUID.randomUUID() + "";// 默认取一个文件名
		}
		return filename;
	}

	public static String getFileNameByUrl(String url) {
		String filename = "";// 默认取一个文件名
		try {
			URL url2 = new URL(url);
			filename = new File(url2.getFile()).getName();
		} catch (Exception e) {
		}

		if (TextUtils.isEmpty(filename)) {
			filename = UUID.randomUUID() + "";// 默认取一个文件名
		}
		return filename;
	}
}
