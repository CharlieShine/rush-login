package com.rush.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 17512 on 2018/3/19.
 */
public class DateUtil {

    public static final String FORMATTER_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATTER_2 = "yyyy-MM-dd";

    public static String format (Date src, String formatter) {
        if (src == null || formatter == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.format(src);
    }

    public static Date parse (String src, String formatter) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatter);
            return sdf.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算两个时间点相差的分钟数
     * @param start
     * @param end
     * @return
     */
    public static Integer getMinutesDistance (Date start, Date end) {
        if (start == null || end == null) {
            return null;
        }
        Integer result = new BigDecimal((end.getTime() - start.getTime()) / ( 1000 * 60 ))
                .setScale(0, BigDecimal.ROUND_DOWN).intValue();
        return result;
    }

    /**
     * 生成一天开始的时间(00:00:00)
     * @return
     */
    public static Date getStartTimeOfDay (Date src) {
        if (src == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 生成一天结束的时间(23:59:59)
     * @return
     */
    public static Date getEndTimeOfDay (Date src) {
        if (src == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取偏移day天的时间
     * @param src
     * @param day
     * @return
     */
    public static Date getDateWithOffset(Date src, Integer day) {
        if (src == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
        return calendar.getTime();
    }

    public static boolean isToday (Date src) {
        if (src == null) {
            return false;
        }
        Date todayStart = getStartTimeOfDay(new Date());
        Date todayEnd = getEndTimeOfDay(new Date());
        return src.after(todayStart) && src.before(todayEnd);
    }

    /**
     * 获取当前时间的上个月时间
     * @return
     */
    public static Date lastMonth () {
        Calendar result = Calendar.getInstance();
        result.setTime(new Date());
        result.set(Calendar.DAY_OF_YEAR, result.get(Calendar.DAY_OF_YEAR) - 30);
        return result.getTime();
    }
}
