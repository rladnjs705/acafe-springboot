package com.javadeveloperzone.config.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	/**
	 * yyyy-MM-dd HH:mm:ss를 Date로 변환한다.
	 * @param ymdhms
	 * @return
	 */
	public static Date stringToTime(String ymdhms) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = parser.parse(ymdhms);
		} catch (ParseException e) {
			logger.error("stringToTime error " + e.toString());
		}
		return date;
	}
	
//	public static String dateToStr(Date date) {
//		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		parser.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
//		String str = "";
//		if(null == date) {
//			date = new Date();
//		}
//		str = parser.format(date);
//		return str;
//	}
	
	public static String longToDateStr(String msStr) {
		long ms = Long.valueOf(msStr);
		Date date = new Date(ms);
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		parser.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
		return parser.format(date);
	}
	
	/**
	 * 파라미터만큼 날짜를 변경
	 * @param msStr
	 * @return
	 */
	public static String longToDateStr(String msStr, int year, int month, int day) {
		long ms = Long.valueOf(msStr);
		Date date = new Date(ms);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
	    if(0 != year) {
	    	cal.add(Calendar.YEAR, year);
	    }
	    if(0 != month) {
	    	cal.add(Calendar.MONTH, month);
	    }
	    if(0 != day) {
	    	cal.add(Calendar.DATE, day);
	    }
	    
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		parser.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
		return parser.format(cal.getTime());
	}
	
	/**
	 * 파라미터만큼 날짜를 변경
	 * @param msStr
	 * @return
	 */
	public static String ymdhmsToDateStr(String ymdhms, int year, int month, int day) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = parser.parse(ymdhms);
		} catch (ParseException e) {
			logger.error("stringToTime error " + e.toString());
		}
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    
	    if(0 != year) {
	    	cal.add(Calendar.YEAR, year);
	    }
	    if(0 != month) {
	    	cal.add(Calendar.MONTH, month);
	    }
	    if(0 != day) {
	    	cal.add(Calendar.DATE, day);
	    }

	    SimpleDateFormat retParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return retParser.format(cal.getTime());
	}
	
	/**
	 * 날짜만큼 현재 시간에 더해서 내려준다.
	 */
	public static String nowToDateStr(int year, int month, int day) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
	    if(0 != year) {
	    	cal.add(Calendar.YEAR, year);
	    }
	    if(0 != month) {
	    	cal.add(Calendar.MONTH, month);
	    }
	    if(0 != day) {
	    	cal.add(Calendar.DATE, day);
	    }
	    
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		parser.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
		return parser.format(cal.getTime());
	}

	public static String nowToDateStr(int year, int month, int day, int hour, String formatStr) {

		if (null == formatStr || "".equals(formatStr)) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
		if (0 != year) {
			cal.add(Calendar.YEAR, year);
		}
		if (0 != month) {
			cal.add(Calendar.MONTH, month);
		}
		if (0 != day) {
			cal.add(Calendar.DATE, day);
		}
		if (0 != hour) {
			cal.add(Calendar.HOUR, hour);
		}

		SimpleDateFormat parser = new SimpleDateFormat(formatStr);
		parser.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
		return parser.format(cal.getTime());
	}
	
	/**
	 * 날짜만큼 현재 시간에 더해서 한국시간으로 내려준다.
	 */
	public static String nowToDateSeoulStr(int year, int month, int day) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
	    if(0 != year) {
	    	cal.add(Calendar.YEAR, year);
	    }
	    if(0 != month) {
	    	cal.add(Calendar.MONTH, month);
	    }
	    if(0 != day) {
	    	cal.add(Calendar.DATE, day);
	    }

	    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		parser.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
		return parser.format(cal.getTime());
	}
	
	/**
	 * 날짜만큼 현재 시간에 더해서 Ymd형식으로 한국시간으로 내려준다. 
	 * php의 레디스키는 yyyyMMdd형식이라 java와 공유하지않는다.
	 */
	public static String nowToDateSeoulYmdJava(int year, int month, int day) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
		if(0 != year) {
			cal.add(Calendar.YEAR, year);
		}
		if(0 != month) {
			cal.add(Calendar.MONTH, month);
		}
		if(0 != day) {
			cal.add(Calendar.DATE, day);
		}
		
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		parser.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
		return parser.format(cal.getTime());
	}
	
	/**
	 * utc 시간을 서울시간으로 변경한다.
	 */
	public static String stringToSeoulTime(String ymdhms) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = parser.parse(ymdhms);
		} catch (ParseException e) {
			logger.error("stringToTime error " + e.toString());
		}
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    
	    if("UTC".equals(TimeZone.getDefault().getID())) {
	    	cal.add(Calendar.HOUR, 9);
	    }

	    SimpleDateFormat retParser = new SimpleDateFormat("yyyy-MM-dd");
		return retParser.format(cal.getTime());
	}

	/**
	 * utc 시간을 서울시간으로 변경한다.
	 * 
	 * @param ymdhms
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String stringToSeoulTimeHhmmss(String ymdhms) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = parser.parse(ymdhms);
		} catch (ParseException e) {
			logger.error("stringToTime error " + e.toString());
		}
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    
	    if("UTC".equals(TimeZone.getDefault().getID()) || "Etc/GMT".equals(TimeZone.getDefault().getID())) {
	    	cal.add(Calendar.HOUR, 9);
	    }

	    SimpleDateFormat retParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return retParser.format(cal.getTime());
	}
	
	/**
	 * 서울 시간을 UTC시간으로 변경한다.
	 * 
	 * @param ymdhms
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String stringToUtcTimeHhmmss(String ymdhms) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = parser.parse(ymdhms);
		} catch (ParseException e) {
			logger.error("stringToTime error " + e.toString());
		}
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
    	cal.add(Calendar.HOUR, -9);

	    SimpleDateFormat retParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return retParser.format(cal.getTime());
	}
	
	/**
	 * 현재 UTC 날짜를 내려준다.
	 */
	public static String today(String format) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
		SimpleDateFormat parser = new SimpleDateFormat(format);
		parser.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
		return parser.format(cal.getTime());
	}
	
	/**
	 * 오늘날짜를 format 형식에 맞게 반환
	 * 
	 * @param formatStr
	 * @return
	 *
	 */
	public static String todayFormatToDateStr(String formatStr) {

		if (null == formatStr || "".equals(formatStr)) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}

		Date date = new Date();
		SimpleDateFormat parser = new SimpleDateFormat(formatStr);
		String today = parser.format(date);

		return today;
	}
	
	public static String elasticToDate(String ymdthms) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = sdf.parse(ymdthms);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(d);
	    cal.add(Calendar.HOUR, 9);
	    
		return output.format(cal.getTime());
	}

	public static String convertLongToStrFormat(Long lSrc, String sFormat)
	{
		if (null == sFormat || "".equals(sFormat)) {
			sFormat = "yyyy-MM-dd HH:mm:ss";
		}

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(lSrc);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sFormat);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));

		return simpleDateFormat.format(cal.getTime());
	}
	
	public static boolean checkPwdChgTime(String ymd) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, -90);
			return cal.getTime().after(parser.parse(ymd));
		} catch (ParseException e) {
			logger.error("checkPwdChgTime error " + e.toString());
		}
		return false;
	}
	
//	public static void main(String[] args) {
//		System.out.println(checkPwdChgTime("2021-11-10"));
//	}
}