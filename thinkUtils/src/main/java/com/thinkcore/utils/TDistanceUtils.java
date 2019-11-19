package com.thinkcore.utils;

/**
 * LBS距离计算
 * 
 */
public class TDistanceUtils {
	private static double EARTH_RADIUS = 6378137.0;

	public static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = (lat1 * Math.PI / 180.0);
		double radLat2 = (lat2 * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng1 - lng2) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;

		s = Math.round(s * 10000) / 10000;

		return s;
	}
}
