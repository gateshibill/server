package com.ylb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static long getStartTime(String strDateTme) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long lDateTime = null;
		try {
			if (null != strDateTme) {
				lDateTime = dateFormat.parse(strDateTme).getTime();
			} else {
				lDateTime = dateFormat.parse("1900-01-01 0:0:0").getTime();
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			try {
				lDateTime = dateFormat.parse("1900-01-01 0:0:0").getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lDateTime;
	}

	public static long getEndTime(String strDateTme) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long lDateTime = null;
		try {
			if (null != strDateTme) {
				lDateTime = dateFormat.parse(strDateTme).getTime();
			} else {
				lDateTime = dateFormat.parse("3000-01-01 0:0:0").getTime();
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			try {
				lDateTime = dateFormat.parse("3000-01-01 0:0:0").getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lDateTime;
	}

	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param time         System.currentTimeMillis()
	 * @param strDateBegin 开始时间 00:00:00
	 * @param strDateEnd   结束时间 00:05:00
	 * @return
	 */
	public static boolean isInDate(long time, String strDateBegin, String strDateEnd) {
		Calendar calendar = Calendar.getInstance();
		// 处理开始时间
		String[] startTime = strDateBegin.split(":");
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime[0]));
		calendar.set(Calendar.MINUTE, Integer.valueOf(startTime[1]));
		calendar.set(Calendar.SECOND, Integer.valueOf(startTime[2]));
		calendar.set(Calendar.MILLISECOND, 0);
		long startTimeL = calendar.getTimeInMillis();
		// 处理结束时间
		String[] endTime = strDateEnd.split(":");
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(endTime[0]));
		calendar.set(Calendar.MINUTE, Integer.valueOf(endTime[1]));
		calendar.set(Calendar.SECOND, Integer.valueOf(endTime[2]));
		calendar.set(Calendar.MILLISECOND, 0);
		long endTimeL = calendar.getTimeInMillis();
		return time >= startTimeL && time <= endTimeL;
	}

	/**
	 * 验证当前日期是否为周六、周日
	 * 
	 * @return boolean true：yes false：no
	 * @throws ParseException
	 */
	public static boolean isWeekend() {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date bdate;
		try {
			bdate = format1.parse(format1.format(new Date()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(bdate);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				return true;
			} else
				return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 验证是否交易时间
	 * 
	 * @return boolean true：yes false：no
	 */
	public static boolean isTradingHours() {
		if (isInDate(System.currentTimeMillis(), "9:30:00", "11:30:00")) {
			return true;
		} else if (isInDate(System.currentTimeMillis(), "13:00:00", "15:00:00")) {
			return true;
		}
		return false;
	}

	/**
	 * 验证是否是交易后
	 * 
	 * @return
	 */
	public static boolean isTradingEndHours() {
		if (isInDate(System.currentTimeMillis(), "15:06:00", "15:10:00")) {
			return true;
		}
		return false;
	}

	/**
	 * 是否交易前
	 * 
	 * @return
	 */
	public static boolean isTradingStartEndHours() {
		if (isInDate(System.currentTimeMillis(), "9:00:00", "09:00:05")) {
			return true;
		} else if (isInDate(System.currentTimeMillis(), "13:00:00", "15:00:05")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否在24小时之内
	 * 
	 * @param date1
	 * @param date2
	 * @return true：在， false:不在
	 * @throws Exception
	 */
	public static boolean judgmentDate(Date start, Date end) throws Exception {
		long cha = end.getTime() - start.getTime();
		if (cha < 0) {

			return false;

		}
		double result = cha * 1.0 / (1000 * 60 * 60);
		if (result <= 24) {
			return true;
		} else {
			return false;
		}
	}

	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		System.out.println(res);
		return res;
	}

	/**
	 * 判断是否在交易时间
	 */
	public static boolean checkIsTradingTime() {
		boolean isflag = true;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {// 是否周六末
			isflag = false;
		} else {
			isflag = isTradingHours();
		}
		return isflag;
	}

	/**
	 * 生成不带类别标头的编码
	 * 
	 * @param id
	 */
	public static synchronized String getCode(Integer userId) {
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String result = "";
		try {
			result = sdf.format(new Date()) + userId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
