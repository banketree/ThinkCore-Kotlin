package com.thinkcore.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @日期比较差值不包括起始日期,包括最后日期
 */
public class TDateCalculateUtils {
	private static String TAG = TDateCalculateUtils.class.getSimpleName();
	private long differenceOfMonths;// 月份差值
	private long differenceOfDays;// 天数差值

	public static TDateCalculateUtils calculate1(String startdate, String endDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			TDateCalculateUtils dateCalculate = calculate1(
					dateFormat.parse(startdate), dateFormat.parse(endDate));
			dateFormat = null;
			return dateCalculate;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		dateFormat = null;
		return null;
	}

	public static TDateCalculateUtils calculate1(long startTime, long endTime) {
		TDateCalculateUtils dateCalculateUtils;
		Date date1 = new Date(startTime);
		Date date2 = new Date(endTime);
		dateCalculateUtils = calculate1(date1, date2);
		date1 = date2 = null;
		return dateCalculateUtils;
	}

	/**
	 * 计算差值,注意 endDate > startDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static TDateCalculateUtils calculate1(Date startDate, Date endDate) {
		if (startDate.after(endDate)) {
			Date temp = (Date) startDate.clone();
			startDate = (Date) endDate.clone();
			endDate = (Date) temp.clone();
			temp = null;
		}

		TDateCalculateUtils dataCalculate = new TDateCalculateUtils();
		Calendar firstDay = Calendar.getInstance(); // 起始
		Calendar lastDay = Calendar.getInstance(); // 终止
		firstDay.setTime(startDate);
		lastDay.setTime(endDate);

		// 算出天数总差值
		long allDays = TTimeUtils.getDaysByTimeInterval(
				lastDay.getTimeInMillis(), firstDay.getTimeInMillis());

		Calendar loopEndDay = calculateLoopEndOfDate(firstDay, lastDay);// 循环计算

		dataCalculate.setDifferenceOfDays(0);
		dataCalculate.setDifferenceOfMonths(0);

		int month = firstDay.get(Calendar.MONTH);
		while (!firstDay.equals(loopEndDay)) {
			firstDay.add(Calendar.DAY_OF_MONTH, 1);
			allDays--;
			if (month != firstDay.get(Calendar.MONTH)) {
				month = firstDay.get(Calendar.MONTH);
				dataCalculate.setDifferenceOfMonths(dataCalculate
						.getDifferenceOfMonths() + 1);
			}
		}
		dataCalculate.setDifferenceOfDays(allDays);
		return dataCalculate;

	}

	/**
	 * 计算循环终止日期 例如:开始日：2011/03/17 结束日 2012/02/13 ,循环终止日期 2012/01/17;
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private static Calendar calculateLoopEndOfDate(Calendar startDate,
			Calendar endDate) {
		int year = endDate.get(Calendar.YEAR);
		int month = endDate.get(Calendar.MONTH);
		int day = startDate.get(Calendar.DAY_OF_MONTH);
		int maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year,
				month, 1));

		if (year > startDate.get(Calendar.YEAR)) {
			if (month == Calendar.JANUARY) {
				year -= 1;
				month = Calendar.DECEMBER;
			} else {
				if (day > maxDaysInMonth) {
					month -= 1;
					endDate.set(year, month, 1);
					day = getMaxDaysOfMonth(new GregorianCalendar(year, month,
							1));
				} else {
					if (day > endDate.get(Calendar.DAY_OF_MONTH)) {
						month -= 1;
						endDate.set(year, month, 1);
						maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(
								year, month, 1));
						;
						if (day > maxDaysInMonth) {
							day = maxDaysInMonth;
						}
					}
				}
			}
		} else {
			if (day > maxDaysInMonth) {
				month -= 1;
				endDate.set(year, month, 1);
				day = getMaxDaysOfMonth(new GregorianCalendar(year, month, 1));
			} else {
				if (day > endDate.get(Calendar.DAY_OF_MONTH)) {
					month -= 1;
					endDate.set(year, month, 1);
					maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(
							year, month, 1));
					if (day > maxDaysInMonth) {
						day = maxDaysInMonth;
					}
				}
			}
		}

		return new GregorianCalendar(year, month, day);
	}

	/**
	 * 获取一月最大天数,考虑年份是否为润年 (对API中的 getMaximum(int field)不了解,
	 * date.getMaximum(Calendar.DAY_OF_MONTH)却不是月份的最大天数)
	 * 
	 * @param date
	 * @return
	 */
	private static int getMaxDaysOfMonth(GregorianCalendar date) {
		int month = date.get(Calendar.MONTH);
		int maxDays = 0;
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.MARCH:
		case Calendar.MAY:
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.OCTOBER:
		case Calendar.DECEMBER:
			maxDays = 31;
			break;
		case Calendar.APRIL:
		case Calendar.JUNE:
		case Calendar.SEPTEMBER:
		case Calendar.NOVEMBER:
			maxDays = 30;
			break;
		case Calendar.FEBRUARY:
			if (date.isLeapYear(date.get(Calendar.YEAR))) {
				maxDays = 29;
			} else {
				maxDays = 28;
			}
			break;
		}
		return maxDays;
	}

	public long getDifferenceOfMonths() {
		return differenceOfMonths;
	}

	public void setDifferenceOfMonths(long differenceOfmonths) {
		this.differenceOfMonths = differenceOfmonths;
	}

	public long getDifferenceOfDays() {
		return differenceOfDays;
	}

	public void setDifferenceOfDays(long differenceOfDays) {
		this.differenceOfDays = differenceOfDays;
	}

	public long getTotalDays() {
		return differenceOfDays + differenceOfMonths * 30;
	}
}