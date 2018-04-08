package com.ywj.swiftbuy.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ywj
 */
public class DateUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);
    private static final List<Long> TIME_UNITS_DURATION = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1)
    );
    private static final List<String> TIME_UNITS = Arrays.asList("年", "月", "天", "小时", "分钟", "秒");

    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYYMMMDD_HHMMSS = "yyyy-MM-dd hh:mm:ss";

    public static Date parse(String str, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.ROOT);
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse(String str) {
        Date date;
        if (StringUtils.isEmpty(str)) {
            date = getDateOfToday();
        } else {
            date = parse(str, DATE_FORMAT);
        }
        return date;
    }

    public static Date parseReturnNull(String str) {
        Date date;
        if (StringUtils.isEmpty(str)) {
            date = null;
        } else {
            date = parse(str, DATE_FORMAT);
        }
        return date;
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.formatDateInEnglish(new Date(), "EEEE.MMMM dd"));
        System.out.println(getUtcTimeStr());
        System.out.println(DateUtils.parse("20170901").getTime());
    }

    public static Date cloneDate(Date date) {
        return date != null ? (Date) date.clone() : null;
    }

    public static Date subtractDay(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -num);
        return cal.getTime();
    }

    public static Date getStartTimeOfTheDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getEndTimeOfTheDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static String format(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ROOT);
        return dateFormat.format(date);
    }

    public static String format(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.ROOT);
        return dateFormat.format(date);
    }

    public static String getUtcTimeStr() {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
        return dateFormat.format(cal.getTime());
    }

    public static String formatDateInHeader(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM. dd", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String formatDateInEnglish(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String formatWeekInChinese(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINESE);
        return dateFormat.format(date);
    }

    public static String getToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ROOT);
        return dateFormat.format(new Date());
    }

    public static Date getDateOfToday(Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, locale);
        try {
            return dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            LOG.warn("get date of today failed", e);
            return null;
        }
    }

    public static Date getDateOfToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ROOT);
        try {
            return dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            LOG.warn("get date of today failed", e);
            return null;
        }
    }

    public static Date getDateOfYesterday() {
        return subtractDay(getDateOfToday(), 1);
    }

    public static Date getDateOfYesterday(Locale locale) {
        return subtractDay(getDateOfToday(locale), 1);
    }

    public static Date getDateOfOneMonthAgo() {
        return subtractDay(getDateOfToday(), 30);
    }

    public static Date getDateOfLastSunday() {
        Date today = getDateOfToday();
        int dayOfWeek = getDayOfWeek(today);
        //默认状态下星期日为每星期第 1 天, 我们需转换使得星期一为第 1 天, 星期日为最后一天。
        if (dayOfWeek == Calendar.SUNDAY) {
            dayOfWeek = dayOfWeek + 7;
        }
        dayOfWeek = dayOfWeek - 1;
        return subtractDay(today, dayOfWeek);
    }

    public static Date tomorrow(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date yesterday(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date tomorrow() {
        return tomorrow(null);
    }

    public static Date yesterday() {
        return yesterday(null);
    }

    public static int getDaysFromNow(Date uploadTime) {
        long diff = System.currentTimeMillis() - uploadTime.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long getTimeGapFromNow(Date uploadTime, TimeUnit timeUnit) {
        long diff = System.currentTimeMillis() - uploadTime.getTime();
        return timeUnit.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Timestamp toTimestamp(Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }

    public static Timestamp toTimestamp(long time) {
        return new Timestamp(time);
    }

    public static Date toDate(long time) {
        return new Date(time);
    }

    public static Date toDate(Timestamp timestamp) {
        return timestamp == null ? null : new Date(timestamp.getTime());
    }

    public static int getField(String hhmmssStr, int field) {
        Date date = parse(hhmmssStr, "HH:mm:ss");
        if (date == null) {
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    public static int getField(Date date, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    public static boolean isWeekend(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    public static boolean isTuesday(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        return dayOfWeek == Calendar.TUESDAY;
    }

    public static Date calculateTime(Date date, String hhmmssStr) {
        int hour = DateUtils.getField(hhmmssStr, Calendar.HOUR_OF_DAY);
        int min = DateUtils.getField(hhmmssStr, Calendar.MINUTE);
        int sec = DateUtils.getField(hhmmssStr, Calendar.SECOND);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date now() {
        return toDate(System.currentTimeMillis());
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);

        return cal.getTime();
    }

    // 获得 startDate 和这天之前一共 num 个 Date 的 list
    public static List<Date> getDateList(Date startDate, int num) {
        List<Date> list = Lists.newArrayList();
        for (int i = 0; i < num; i++) {
            list.add(subtractDay(startDate, i));
        }
        return list;
    }

    public static List<Date> getDateList(Date startDate, Date endDate) {
        List<Date> list = Lists.newArrayList();
        int num = dateDiff(endDate, startDate);
        for (int i = 0; i <= num; i++) {
            list.add(subtractDay(startDate, -i));
        }
        return list;
    }

    public static String getDate(String startDate, int addDays, String format) {
        String time;
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(format);
        Date date;

        if (!StringUtils.isBlank(startDate)) {
            try {
                date = df.parse(startDate);
                calendar.setTime(date);
            } catch (ParseException e) {
                return null;
            }
        }

        // Subtract add days
        calendar.add(Calendar.DATE, addDays);
        date = calendar.getTime();
        time = df.format(date);
        return time;
    }

    public static String getYesterdayDate(String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(date.getTime() - 86400 * 1000);
        return sf.format(date);
    }

    public static int getSecondFromTime(String time) {
        DateFormat dateFormat;
        if (StringUtils.isEmpty(time)) {
            return 0;
        }
        if (StringUtils.countMatches(time, ":") == 1) {
            dateFormat = new SimpleDateFormat("mm:ss");
        } else if (StringUtils.countMatches(time, ":") == 2) {
            dateFormat = new SimpleDateFormat("HH:mm:ss");
        } else {
            return 0;
        }
        try {
            Date reference = dateFormat.parse("00:00:00");
            Date date = dateFormat.parse(time);
            return (int) ((date.getTime() - reference.getTime()) / 1000L);
        } catch (Exception e) {
            return 0;
        }
    }

    private static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int dateDiff(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return Integer.MAX_VALUE;
        }
        return (int) ((date1.getTime() - date2.getTime()) / 86400000);
    }

    public static String toTimeAgoFormat(Date date) {
        Date now = now();
        if (now.after(date)) {
            return toTimeAgoFormat(now.getTime() - date.getTime());
        } else {
            return "刚";
        }
    }

    private static String toTimeAgoFormat(long duration) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < TIME_UNITS_DURATION.size(); i++) {
            long current = TIME_UNITS_DURATION.get(i);
            long count = duration / current;
            if (count > 0) {
                result.append(count).append(TIME_UNITS.get(i)).append("前");
                break;
            }
        }
        if ("".equals(result.toString()))
            return "刚";
        else
            return result.toString();
    }

    public static String toTimeFormatInClient(Date date) {
        Date now = now();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (dateDiff(now, date) < 1) {
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            return String.format("%02d", hour) + ":" + String.format("%02d", minute) + " ";
        } else {
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            return year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
        }
    }

    public static boolean before(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return false;
        } else if (date1 == null) {
            return true;
        } else if (date2 == null) {
            return false;
        } else {
            return date1.before(date2);
        }
    }

    public static String formatDuration(int duration) {
        int minutes = duration / 60;
        int seconds = duration - minutes * 60;
        return String.format("%02d", minutes) + "\' " + String.format("%02d", seconds) + "\"";
    }

    public static String formatMillSec2HMS(long duration) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(duration);
    }

    public static int compare(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        } else if (date1 == null) {
            return 1;
        } else if (date2 == null) {
            return -1;
        } else {
            return date1.compareTo(date2);
        }
    }


    public static long compareToday(Date date) {
        if (date == null) {
            return -1;
        }
        return (System.currentTimeMillis() - date.getTime()) / (1000 * 60 * 60 * 24);
    }

    public static Date getIntervalDate(Timestamp date, int days) {
        return new Date(date.getTime() + days * 86400000L);
    }

    public static int getDateDifferenceInDayUnit(Date date1, Date date2) {
        return (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
    }

    public static Date randomDate(Date start, Date end) {
        try {
            if (start == null || end == null) {
                return null;
            }
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            LOG.error("error during randomDate,beginDate:" + start + ",endDate:" + end, e);
        }

        return null;

    }

    public static Date getDateBeforeOrAfter(Date date, int dayCount) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_MONTH, dayCount);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;

    }
}