package com.example.jhzyl.firstapp.ref;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.Time;

import com.example.jhzyl.firstapp.R;

/**
 * 数据工具类
 * @author 099
 *
 */
public class DataUtils {


	/**
	 * 10分钟
	 */
	private static final int tennum = 10;
	/**
	 * 20分钟
	 */
	private static final int twentynum = 20;
	/**
	 * 30分钟
	 */
	private static final int thirtynum = 30;
	/**
	 * 40分钟
	 */
	private static final int fortynum = 40;
	/**
	 * 50分钟
	 */
	private static final int fiftynum = 50;
	/**
	 * 60分钟
	 */
	private static final int sixtynum = 60;

	/**
	 * 1小时
	 */
	private static final int onenum = 1;
	/**
	 * 3小时
	 */
	private static final int threenum = 3;
	/**
	 * 5小时
	 */
	private static final int fivenum = 5;

	/**
	 * 10 分钟
	 */
	public static final long time_10m = 10 * 60 * 1000;
	/**
	 * 20 分钟
	 */
	public static final long time_20m = 20 * 60 * 1000;
	/**
	 * 30 分钟
	 */
	public static final long time_30m = 30 * 60 * 1000;
	/**
	 * 40 分钟
	 */
	public static final long time_40m = 40 * 60 * 1000;
	/**
	 * 50 分钟
	 */
	public static final long time_50m = 50 * 60 * 1000;
	/**
	 * 一小时
	 */
	public static final long time_1h = 60 * 60 * 1000;
	/**
	 * 2小时
	 */
	public static final long time_3h = 3 * 60 * 60 * 1000;
	/**
	 * 5小时
	 */
	public static final long time_5h = 5 * 60 * 60 * 1000;
	/**
	 * 10小时
	 */
	public static final long time_10h = 10 * 60 * 60 * 1000;
	/**
	 * 20小时
	 */
	public static final long time_20h = 20 * 60 * 60 * 1000;
	/**
	 * 24小时
	 */
	public static final long time_24h = 24 * 60 * 60 * 1000;

	/**
	 * FOR TEST
	 */
	public static final int SLEEP_TIME = 3000;
	/**
	 * 网络请求超时时间
	 */
	public static final int CONNECION_TIMEOUT = 30000;
	/**
	 * 10 分钟
	 */
	private static long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

	/**
	 * 按照指定格式时间到string
	 *
	 * @param pattern
	 *            转换格式
	 * @param date
	 *            时间
	 * @return 转化串
	 */
	public static String getDateToString(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 两个时间相差的时间
	 *
	 * @param frist
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 时间差（分钟）
	 */
	public static long getDateInterval(Date frist, Date end) {
		long result = end.getTime() - frist.getTime();
		result = result / 1000 / 60;
		return result;
	}

	/**
	 * 获取时间,然后进行判断已返回相应时间
	 *
	 * @param date
	 *            指定时间与当前时间的比较
	 * @return xx分、秒、小时前
	 */
	public static String getTime(Date date,
								 Context activity) {/*
	 * String result = ""; Date cachedate = new
	 * Date(); // 分钟 int Minutes = date .getMinutes
	 * (); // 小时 int Hours = date.getHours (); // 日
	 * int day = date.getDate (); // 月 int Month =
	 * date.getMonth () + 1; // 当前月 int creenMonth =
	 * cachedate .getMonth() + 1; // 当前天 int
	 * creenday = cachedate .getDate(); // 当前时 int
	 * creenHours = cachedate .getHours(); // 当前分
	 * int creenMinutes = cachedate. getMinutes();
	 * if (creenMonth > Month || creenday > day) {
	 * return Month + activity .getString (R.string
	 * .yue) + day + activity .getString (R.string
	 * .ri); } else { if (creenMonth == Month) { if
	 * (creenHours == Hours) { Minutes =
	 * creenMinutes - Minutes; if (Minutes < tennum)
	 * { result = activity .getString (R.string
	 * .ganggang); } else if (Minutes < twentynum) {
	 * result = activity .getString (R.string
	 * .fz10); } else if (Minutes < thirtynum) {
	 * result =activity .getString (R.string .fz20);
	 * } else if (Minutes < fortynum) { result =
	 * activity .getString (R.string .fz30); } else
	 * if (Minutes < fiftynum) { result = activity
	 * .getString (R.string .fz40); } else if
	 * (Minutes < sixtynum) { result = activity
	 * .getString (R.string .fz50); } } else { Hours
	 * = creenHours - Hours; if (Hours <onenum) {
	 * result = activity .getString (R.string .xs1);
	 * } else if (Hours < threenum) { result =
	 * activity .getString (R.string .xs3); } else
	 * if (Hours < fivenum) { result = activity
	 * .getString (R.string .xs5); } else if (Hours
	 * < thirtynum) { result = activity .getString
	 * (R.string .xs10); } else if (Hours <
	 * fortynum) { result = activity .getString
	 * (R.string .xs20); } } } } return result;
	 */

		// --------------
		Date time = date;
		String result = "";
		if ("".equals(time)) {
			return result;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long currentTime = System.currentTimeMillis();
		long commentTime = time.getTime();
		long timeCofigSet = currentTime - commentTime;
		Date now = new Date();
		if (time.getYear() < now.getYear()) {
			result = sdf.format(time);
		} else if (time.getMonth() < now.getMonth()) {
			result = sdf.format(time);
		} else if (time.getDate() < now.getDate() && timeCofigSet > time_24h) {
			result = sdf.format(time);
		} else if (timeCofigSet < time_10m) {
			result = activity.getString(R.string.ganggang);
		} else if (timeCofigSet < time_20m && timeCofigSet > time_10m) {
			result = activity.getString(R.string.fz10);
		} else if (timeCofigSet < time_30m && timeCofigSet > time_20m) {
			result = activity.getString(R.string.fz20);
		} else if (timeCofigSet < time_40m && timeCofigSet > time_30m) {
			result = activity.getString(R.string.fz30);
		} else if (timeCofigSet < time_50m && timeCofigSet > time_40m) {
			result = activity.getString(R.string.fz40);
		} else if (timeCofigSet < time_1h && timeCofigSet > time_50m) {
			result = activity.getString(R.string.fz50);
		} else if (timeCofigSet < time_3h && timeCofigSet > time_1h) {
			result = activity.getString(R.string.xs1);
		} else if (timeCofigSet < time_5h && timeCofigSet > time_3h) {
			result = activity.getString(R.string.xs3);
		} else if (timeCofigSet < time_10h && timeCofigSet > time_5h) {
			result = activity.getString(R.string.xs5);
		} else if (timeCofigSet < time_20h && timeCofigSet > time_10h) {
			result = activity.getString(R.string.xs10);
		} else if (timeCofigSet < time_24h && timeCofigSet > time_20h) {
			result = activity.getString(R.string.xs20);
		}
		return result;

	}

	/**
	 * 指定时间与当前时间比较（超过一天，则为具体时间串）
	 *
	 * @param time
	 *            时间串（2012-12-20 样式）
	 * @return 相差时间描述
	 */

	/**
	 * 指定时间与当前时间比较（超过一天，则为具体时间串）
	 *
	 * @param time
	 *            date 类型
	 * @return 相差时间描述
	 */

	/**
	 * 时间起始 1900
	 */
	public static final double YEAR_BASE = 1900;

	/**
	 * get time string
	 *
	 * @param time
	 *            time
	 * @return time string
	 */

	/**
	 * 将date转换为string 指定格式 如果不指定格式 默认为yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 * @return
	 */
	public static String dealDate2String(Date date, String patten) {
		if (patten == null) {
			patten = "yyyy-MM-dd HH:mm:ss";// 默认格式
		}
		SimpleDateFormat format = new SimpleDateFormat(patten);
		return format.format(date);
	}

	/**
	 * 描述串转date时间
	 *
	 * @param dateStr
	 * @param patten
	 *            格式， 默认为 "yyyy-MM-dd HH:mm:ss"
	 * @return date
	 */

	/**
	 * 将日期按照指定格式输出
	 *
	 * @param pattern
	 *            转化格式
	 * @param date
	 *            指定时间
	 * @return 时间描述
	 */
	public static String SimpleDateFormat(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 讲字符串转化为date
	 *
	 * @param pattern
	 *            转化格式
	 * @param string
	 *            需要解析的时间描述
	 * @return date
	 * @throws Exception
	 */
	public static Date SimpleDateFormatToDate(String pattern, String string) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(string);
	}

	/**
	 * 返回最晚时间
	 *
	 * @return 最晚时间
	 */
	@SuppressWarnings("deprecation")
	public static Date getUpdateDate() {
		final int year = 1000;
		final int one = 1;
		return new Date(year, one, one);
	}

	/**
	 * 是否是今天
	 *
	 * @param date
	 *            待判定日期
	 * @return 是今天
	 */
	public static boolean isToday(Date date) {
		if (date == null) {
			return false;
		}
		final Date today = new Date();
		return date.getYear() == today.getYear() && date.getMonth() == today.getMonth()
				&& date.getDate() == today.getDate();
	}

	/**
	 * 根据毫秒值获取具体的日期信息
	 *
	 * @param time
	 *            time
	 * @return String
	 */

	/**
	 * 得到当前零点毫秒值
	 *
	 * @return long
	 */
	private static long getFirstSecondOfToday() {
		final Time t = new Time();
		t.setToNow();
		t.hour = 0;
		t.minute = 0;
		t.second = 0;
		final long firstSecondOfToday = t.toMillis(false);
		return firstSecondOfToday;
	}

	/**
	 * 当前时间
	 *
	 * @return 20121220 时间描述
	 */
	public static String GetCreenDate() {
		String result = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		result = simpleDateFormat.format(new Date());
		return result;
	}

	/**
	 * 获取当前时间 如果获取不到 默认为1900-01-01 00:00:00
	 *
	 * @param pattern
	 *            格式
	 * @return 当前时间描述
	 */
	public static String GetCreenDate(String pattern) {
		String result = "1900-01-01 00:00:00";
		if (pattern == null) {
			pattern = "yyyy-MM-dd hh:mm:ss";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		result = simpleDateFormat.format(new Date());
		return result;
	}

	/**
	 *
	 * @param date
	 * @param isShowLastYear
	 *            true 当跨年时显示 年 false 不显示年
	 * @return
	 */

	/**
	 * 根据毫秒值获取具体的日期信息
	 *
	 * @param time
	 *            time
	 * @return String
	 */



}
