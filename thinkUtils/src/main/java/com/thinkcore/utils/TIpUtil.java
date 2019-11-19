package com.thinkcore.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

//import org.apache.http.conn.util.InetAddressUtils;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.Enumeration;
//
///**
// * @返回客户端IP地址
// */
//public class TIpUtil {
//	/**
//	 * 使用Wifi时获取IP 设置用户权限
//	 *
//	 * <uses-permission
//	 * android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
//	 *
//	 * <uses-permission
//	 * android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
//	 *
//	 * <uses-permission
//	 * android:name="android.permission.WAKE_LOCK"></uses-permission>
//	 *
//	 * @return
//	 */
//	public static String getWifiIp(Context context) {
//		// 获取wifi服务
//		WifiManager wifiManager = (WifiManager) context
//				.getSystemService(Context.WIFI_SERVICE);
//		// 判断wifi是否开启
//		if (!wifiManager.isWifiEnabled()) {
//			wifiManager.setWifiEnabled(true);
//		}
//		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//		int ipAddress = wifiInfo.getIpAddress();
//		return intToIp(ipAddress);
//	}
//
//	private static String intToIp(int i) {
//		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
//				+ "." + (i >> 24 & 0xFF);
//
//	}
//
//	/**
//	 * 使用GPRS上网，时获取ip地址，设置用户上网权限
//	 *
//	 * <uses-permission
//	 * android:name="android.permission.INTERNET"></uses-permission>
//	 *
//	 * @return
//	 */
//	public static String getGPRSIp() {
//		try {
//			for (Enumeration<NetworkInterface> en = NetworkInterface
//					.getNetworkInterfaces(); en.hasMoreElements();) {
//				NetworkInterface intf = en.nextElement();
//				for (Enumeration<InetAddress> enumIpAddr = intf
//						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//					InetAddress inetAddress = enumIpAddr.nextElement();
//					if (!inetAddress.isLoopbackAddress()) {
//						return inetAddress.getHostAddress().toString();
//					}
//				}
//			}
//		} catch (SocketException ex) {
//			TLog.d("IpUtil", ex.getMessage());
//		}
//		return "";
//	}
//
//	/**
//	 * 使用GPRS上网，时获取ip地址，设置用户上网权限
//	 *
//	 * <uses-permission
//	 * android:name="android.permission.INTERNET"></uses-permission>
//	 *
//	 * @return
//	 */
//	public static String getGPRSIpv4() {
//		try {
//			for (Enumeration<NetworkInterface> en = NetworkInterface
//					.getNetworkInterfaces(); en.hasMoreElements();) {
//				NetworkInterface intf = en.nextElement();
//				for (Enumeration<InetAddress> enumIpAddr = intf
//						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//					InetAddress inetAddress = enumIpAddr.nextElement();
//					if (!inetAddress.isLoopbackAddress()
//							&& !inetAddress.isLinkLocalAddress()) {
//						return inetAddress.getHostAddress().toString();
//					} else if (!inetAddress.isLoopbackAddress()
//							&& InetAddressUtils.isIPv4Address(inetAddress
//									.getHostAddress())) {
//
//						return inetAddress.getHostAddress().toString();
//					}
//				}
//			}
//		} catch (SocketException ex) {
//			TLog.d("IpUtil", ex.getMessage());
//		}
//		return "";
//	}
//
//	/**
//	 * 获取本机host ip
//	 *
//	 * <uses-permission
//	 * android:name="android.permission.INTERNET"></uses-permission>
//	 *
//	 * @return
//	 */
//	public static String getInetAddress(String host) {
//		String IPAddress = "";
//		InetAddress ReturnStr1 = null;
//		try {
//			ReturnStr1 = InetAddress.getByName(host);
//			IPAddress = ReturnStr1.getHostAddress();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		return IPAddress;
//	}
//
//	/**
//	 * 获取mac地址
//	 *
//	 * @return
//	 */
//	public static String getMacAddress(Context context) {
//		// 获取mac地址：
//		String macAddress = "000000000000";
//		try {
//			WifiManager wifiMgr = (WifiManager) context
//					.getSystemService(Context.WIFI_SERVICE);
//			WifiInfo info = (null == wifiMgr ? null : wifiMgr
//					.getConnectionInfo());
//			if (null != info) {
//				if (!TextUtils.isEmpty(info.getMacAddress()))
//					macAddress = info.getMacAddress().replace(":", "");
//				else
//					return macAddress;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return macAddress;
//		}
//		return macAddress;
//	}
//
//	public static String getIp() {
//		String ipAddress = "";
//		Context context = TApplication.getInstance().getApplicationContext();
//		if (TNetWorkUtil.isWifiConnected()) {
//			ipAddress = TIpUtil.getWifiIp(context);
//		} else if (TNetWorkUtil.isMobileConnected()) {
//			ipAddress = TIpUtil.getGPRSIpv4();
//		}
//
//		return ipAddress;
//	}
//}
