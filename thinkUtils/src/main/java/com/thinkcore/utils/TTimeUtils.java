package com.thinkcore.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @时间操作类（时间字符串、时间戳）
 */
public class TTimeUtils {
    public static final float ONE_MINUTE = 1000 * 60;
    public static final float ONE_HOUR = 60 * ONE_MINUTE;
    public static final float ONE_DAY = 24 * ONE_HOUR;
    public static float oneDaySecond = ONE_DAY / 1000;// 秒
    // public static long oneMonthSecondBy28 = 2419200;// 秒
    // public static long oneMonthSecondBy30 = 2592000; // 秒
    // public static long oneMonthSecondBy31 = 2678400; // 秒

    public static SimpleDateFormat yearmonthFormat = new SimpleDateFormat(
            "yyyy-MM");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat fulTimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat hourTimeFormat = new SimpleDateFormat(
            "HH:mm");
    public static SimpleDateFormat monthTimeFormat = new SimpleDateFormat(
            "MM-dd HH:mm");
    public static SimpleDateFormat fulTimeChinaFormat = new SimpleDateFormat(
            "yyyy年MM月dd日 HH时mm分ss秒");
    public static SimpleDateFormat fulTimeFormat2 = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    private static long day = 7;

    /**
     * 获得当前时间戳
     *
     * @return
     */
    public static String getTimestamp() {
        String unixTimeGMT;
        try {
            long unixTime = System.currentTimeMillis();
            unixTimeGMT = unixTime + "";
        } catch (Exception e) {
            // TODO: handle exception
            unixTimeGMT = "";
        }
        return unixTimeGMT;

    }

    /**
     * 获得当前时间戳
     *
     * @return
     */
    public static long getIntTimestamp() {
        long unixTimeGMT = 0;
        try {
            unixTimeGMT = System.currentTimeMillis();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return unixTimeGMT;

    }

    /**
     * 返回时间戳间隔
     *
     * @return
     */
    public static boolean compareTimestamp(long currentTimestap,
                                           long oldTimestap) {
        Boolean isExceed = false;
        if (gapTimestamp(currentTimestap, oldTimestap) > 86400 * day) {
            isExceed = true;
        }
        return isExceed;
    }

    public static long gapTimestamp(long currentTimestap, long oldTimestap) {
        return (currentTimestap - oldTimestap);
    }

    /**
     * 对时间戳格式进行格式化，保证时间戳长度为13位
     *
     * @param timestamp 时间戳
     * @return 返回为13位的时间戳
     */
    public static String formatTimestamp(String timestamp) {
        if (timestamp == null || "".equals(timestamp)) {
            return "";
        }
        String tempTimeStamp = timestamp + "00000000000000";
        StringBuffer stringBuffer = new StringBuffer(tempTimeStamp);
        return tempTimeStamp = stringBuffer.substring(0, 13);
    }

    /**
     * 根据 timestamp 生成各类时间状态串
     *
     * @param timestamp 距1970 00:00:00 GMT的秒数
     * @param format    格式
     * @return 时间状态串(如：刚刚5分钟前)
     */
    public static String getTimeState(String timestamp, String format) {
        if (timestamp == null || "".equals(timestamp)) {
            return "";
        }

        try {
            timestamp = formatTimestamp(timestamp);
            long _timestamp = Long.parseLong(timestamp);
            if (System.currentTimeMillis() - _timestamp < 1 * 60 * 1000) {
                return "刚刚";
            } else if (System.currentTimeMillis() - _timestamp < 30 * 60 * 1000) {
                return ((System.currentTimeMillis() - _timestamp) / 1000 / 60)
                        + "分钟前";
            } else {
                Calendar now = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(_timestamp);
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("今天 HH:mm");
                    return sdf.format(c.getTime());
                }
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && c.get(Calendar.DATE) == now.get(Calendar.DATE) - 1) {
                    SimpleDateFormat sdf = new SimpleDateFormat("昨天 HH:mm");
                    return sdf.format(c.getTime());
                } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                    SimpleDateFormat sdf = null;
                    if (format != null && !format.equalsIgnoreCase("")) {
                        sdf = new SimpleDateFormat(format);

                    } else {
                        sdf = new SimpleDateFormat("M月d日 HH:mm:ss");
                    }

                    return sdf.format(c.getTime());
                } else {
                    SimpleDateFormat sdf = null;
                    if (format != null && !format.equalsIgnoreCase("")) {
                        sdf = new SimpleDateFormat(format);

                    } else {
                        sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss");
                    }
                    return sdf.format(c.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // /////////////////////////////////

    /**
     * 根据时间获取秒钟
     */
    public static String getSecondTimeString(long second) {
        if (second <= 0) {
            return "00:00:00";
        }
        long hour = second / 60 / 60;
        long min = (second - hour * 60 * 60) / 60;
        long sec = second % 60;
        String strHour = "00", strMin = "00", strSec = "00";
        if (hour > 0 && hour < 10) {
            strHour = "0" + hour;
        } else {
            strHour = "00";
        }

        if (min > 0 && min < 10) {
            strMin = "0" + min;
        } else {
            strMin = "00";
        }

        if (sec > 0 && sec < 10) {
            strSec = "0" + sec;
        } else {
            strSec = "" + sec;
        }

        return strHour + ":" + strMin + ":" + strSec;
    }

    /**
     * 根据时间获取时钟全称
     */
    public static String getFullTime(long time) {
        String strTime;
        Date date = new Date(time);
        strTime = fulTimeFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 根据时间获取时钟全称
     */
    public static String getChinaFullTime(long time) {
        String strTime;
        Date date = new Date(time);
        strTime = fulTimeChinaFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 根据时间获取年月日
     */
    public static String getYearMonDay(long time) {
        String strTime;
        Date date = new Date(time);
        strTime = dateFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 根据时间获取年月日
     */
    public static String getYearMon(long time) {
        String strTime;
        Date date = new Date(time);
        strTime = yearmonthFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 根据时间获取钟秒
     */
    public static String gethourTimeString(long calltime) {
        String info = "";
        Date callTime = new Date(calltime);
        info = hourTimeFormat.format(callTime);
        return info;
    }

    /**
     * 根据时间获取月
     */
    public static String getMonthTimeString(long calltime) {
        String info = "";
        Date callTime = new Date(calltime);
        info = monthTimeFormat.format(callTime);
        return info;
    }

    /**
     * 根据时间获取标准时间
     */
    public static String getStandardDate(long t) { // 将时间戳转为代表"距现在多久之前"的字符串
        StringBuffer sb = new StringBuffer();

        long time = System.currentTimeMillis() - (t);
        long mill = (long) Math.ceil(time / 1000);// 秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            if (day >= 30) {
                long month = (long) Math.ceil(day / 30);
                if (month >= 12) {
                    long year = 1;// (long) Math.ceil(month /12);
                    sb.append(year + "年");
                } else {
                    sb.append(month + "月");
                }
            } else {
                sb.append(day + "天");
            }
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }

        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    // //////////////////////////////////////////////////////////
    // ///////////////////////////

    /**
     * 是否同一天
     */
    public static boolean isSameDay(long time1, long time2) {

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = (Calendar) calendar1.clone();
        calendar1.setTimeInMillis(time1);
        calendar2.setTimeInMillis(time2);

        boolean isSame = (calendar1.get(Calendar.YEAR) == calendar2
                .get(Calendar.YEAR))
                && (calendar1.get(Calendar.MONTH) == calendar2
                .get(Calendar.MONTH))
                && (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2
                .get(Calendar.DAY_OF_MONTH));
        calendar1 = calendar2 = null;
        return isSame;
    }

    /**
     * 是否包含日期
     */
    public static boolean isContainsDate(List<Calendar> selectedCals,
                                         Calendar cal) {
        for (Calendar selectedCal : selectedCals) {
            if (isSameDate(cal, selectedCal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取最小的日期
     */
    public static Calendar minDate(List<Calendar> selectedCals) {
        if (selectedCals == null || selectedCals.size() == 0) {
            return null;
        }
        Collections.sort(selectedCals);
        return selectedCals.get(0);
    }

    /**
     * 获取最大的日期
     */
    public static Calendar maxDate(List<Calendar> selectedCals) {
        if (selectedCals == null || selectedCals.size() == 0) {
            return null;
        }
        Collections.sort(selectedCals);
        return selectedCals.get(selectedCals.size() - 1);
    }

    /**
     * 同一日期
     */
    public static boolean isSameDate(Calendar cal, Calendar selectedDate) {
        return cal.get(Calendar.MONTH) == selectedDate
                .get(Calendar.MONTH)
                && cal.get(Calendar.YEAR) == selectedDate
                .get(Calendar.YEAR)
                && cal.get(Calendar.DAY_OF_MONTH) == selectedDate
                .get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 是否为两日期之间
     */
    public static boolean isBetweenDates(Calendar cal, Calendar minCal,
                                         Calendar maxCal) {
        final Date date = cal.getTime();
        return isBetweenDates(date, minCal, maxCal);
    }

    /**
     * 是否为两日期之间
     */
    public static boolean isBetweenDates(Date date, Calendar minCal,
                                         Calendar maxCal) {
        final Date min = minCal.getTime();
        return (date.equals(min) || date.after(min)) // >= minCal
                && date.before(maxCal.getTime()); // && < maxCal
    }

    public static int getDaysByTimeInterval(long firstTime, long endTime) {
        Calendar firstCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        if (firstTime < endTime) {
            firstCalendar.setTimeInMillis(firstTime);
            endCalendar.setTimeInMillis(endTime);
        } else {
            firstCalendar.setTimeInMillis(endTime);
            endCalendar.setTimeInMillis(firstTime);
        }

        firstCalendar.set(Calendar.HOUR_OF_DAY, 0);
        firstCalendar.set(Calendar.MINUTE, 0);
        firstCalendar.set(Calendar.SECOND, 0);

        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);

        firstTime = firstCalendar.getTimeInMillis();
        endTime = endCalendar.getTimeInMillis();
        firstCalendar = null;
        endCalendar = null;

        if (firstTime == endTime)
            return 0;

        long interval = endTime - firstTime;
        int days = (int) ((interval + 1000 * 59) / (oneDaySecond * 1000));
        return days;
    }

    public static int getDaysByMonth(long monthTime) {
        int result = 0;
        Calendar monthCalendar = Calendar.getInstance();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1); // 取得系统当前时间所在月第一天时间对象
        monthCalendar.add(Calendar.MONTH, 1);// 日期减一,取得上月最后一天时间对象
        monthCalendar.add(Calendar.DAY_OF_MONTH, -1);
        result = monthCalendar.get(Calendar.DAY_OF_MONTH);// 输出上月最后一天日期
        monthCalendar = null;
        return result;
    }

    public static long[] getMinMaxByDay(long dayTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dayTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long minValue = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long maxValue = calendar.getTimeInMillis();
        calendar = null;

        return new long[]{minValue, maxValue};
    }

    public static long[] getMinMaxByMonth(long monthTime) {
        int days = getDaysByMonth(monthTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(monthTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long minValue = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, days - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long maxValue = calendar.getTimeInMillis();
        calendar = null;

        return new long[]{minValue, maxValue, days};
    }

    public static long[] getMinMaxByYear(long time) {
        long yearHead = 0, yearEnd = 0;
        Date date = new Date(time);
        yearHead = TStringUtils.string2long("20" + date.getYear() % 100
                + "-01-01 0:0:1", fulTimeFormat);
        yearEnd = TStringUtils.string2long("20" + date.getYear() % 100
                + "-12-31 0:0:1", fulTimeFormat);
        date = null;
        return new long[]{yearHead, yearEnd};
    }

    public static float getIntervalHour(long time) {
        return (float) (time / ONE_HOUR);
    }

    public static float getIntervalMinute(long time) {
        return (float) (time % ONE_HOUR / ONE_MINUTE);
    }

    public static float getIntervalSecond(long time) {
        return (float) (time % ONE_HOUR % ONE_MINUTE / 1000);
    }
}
