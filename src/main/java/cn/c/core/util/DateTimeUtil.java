package cn.c.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd"
	 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * HH:mm:ss
	 */
	public static final String TIME_PATTERN = "HH:mm:ss";
	/**
	 * HH:mm
	 */
	public static final String SIMPLE_TIME_PATTERN = "HH:mm";
	
	private static SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
    
	
	public static String formatDate(Date date) {
		return formatDate(date, "");
	}

	public static Date parse(String strDate) {
		return parse(strDate, "");
	}
	
	public static String formatDate(Date date, String pattern) {
		synchronized (sdf) {
			if(pattern != null && !pattern.equals("")) {
				sdf.applyPattern(pattern);
			}
			return sdf.format(date);
		}
	}

	public static Date parse(String strDate, String pattern) {
		Date date = null;
		try {
			synchronized (sdf) {
				if(pattern != null && !pattern.equals("")) {
					sdf.applyPattern(pattern);
				}
				date = sdf.parse(strDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 防止实例化
	 */
	private DateTimeUtil(){}
}
