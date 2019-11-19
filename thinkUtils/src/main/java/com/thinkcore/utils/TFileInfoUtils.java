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

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * @文件信息操作类
 */
public class TFileInfoUtils {
	private static String kB_UNIT_NAME = "KB";
	private static String B_UNIT_NAME = "B";
	private static String MB_UNIT_NAME = "MB";
	private static String GB_UNIT_NAME = "GB";

	/**
	 * 返回自定文件或文件夹的大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File f) {// 取得文件大小
		long s = 0;
		try {
			if (f.exists()) {
				FileInputStream fis = null;
				fis = new FileInputStream(f);
				s = fis.available();
			} else {
				f.createNewFile();
				System.out.println("文件不存在");
			}
		} catch (Exception e) {
		}

		return s;
	}

	// 递归
	public static long getFileSizes(File f)// 取得文件夹大小
	{
		long size = 0;
		try {
			File flist[] = f.listFiles();
			for (int i = 0; i < flist.length; i++) {
				if (flist[i].isDirectory()) {
					size = size + getFileSize(flist[i]);
				} else {
					size = size + flist[i].length();
				}
			}
		} catch (Exception e) {
		}

		return size;
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static long getlist(File f) {// 递归求取目录文件个数
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getlist(flist[i]);
				size--;
			}
		}
		return size;
	}

	public static String getSizeString(long size) {
		if (size < 1024) {
			return String.valueOf(size) + B_UNIT_NAME;
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			return String.valueOf(size) + kB_UNIT_NAME;
		} else {
			size = size * 100 / 1024;
		}

		return String.valueOf((size / 100)) + "."
				+ ((size % 100) < 10 ? "0" : "") + String.valueOf((size % 100))
				+ MB_UNIT_NAME;
	}

	/**
	 * 以Gb为单位保留两位小数
	 * 
	 * @param dirSize
	 * @return
	 */
	public static String getGbSize(long dirSize) {
		double size = 0;
		size = (dirSize + 0.0) / (1024 * 1024 * 1024);
		DecimalFormat df = new DecimalFormat("0.00");// 以Mb为单位保留两位小数
		String filesize = df.format(size);
		return filesize;
	}

	/**
	 * 以Mb为单位保留两位小数
	 * 
	 * @param dirSize
	 * @return
	 */
	public static String getMbSize(long dirSize) {
		double size = 0;
		size = (dirSize + 0.0) / (1024 * 1024);
		DecimalFormat df = new DecimalFormat("0.00");// 以Mb为单位保留两位小数
		String filesize = df.format(size);
		return filesize;
	}

	/**
	 * 以Mb为单位保留两位小数
	 * 
	 * @param dirSize
	 * @return
	 */
	public static String getKBSize(long dirSize) {
		double size = 0;
		size = (dirSize + 0.0) / 1024;
		DecimalFormat df = new DecimalFormat("0.00");// 以KB为单位保留两位小数
		String filesize = df.format(size);
		return filesize;
	}
}
