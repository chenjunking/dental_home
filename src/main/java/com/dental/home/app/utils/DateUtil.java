package com.dental.home.app.utils;



import cn.hutool.core.util.StrUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Administrator
 * @date 2017/11/23
 */
public class DateUtil {

    /**
     * 将日期格式化为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateToStr(Date date, String pattern) {
        if (date == null || StrUtil.isEmpty(pattern)) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(pattern);
        String dateStr = df.format(date);
        return dateStr;
    }

    /**
     * 将字符串转换为日期
     *
     * @param str
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date formatStrToDate(String str, String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(str);
    }

    public static String dateDiffToStr(Date startTime, Date endTime) {
        //一天的毫秒数
        long nd = 1000 * 24 * 60 * 60;
        //一小时的毫秒数
        long nh = 1000 * 60 * 60;

        long diff = endTime.getTime() - startTime.getTime();
        if (diff < 0) {
            return "已结束";
        }
        if (diff < nh) {
            return "即将结束";
        }
        //计算差多少天
        long day = diff / nd;
        //计算差多少小时
        long hour = diff % nd / nh;
        StringBuffer str = new StringBuffer();
        if (day > 0) {
            str.append(Long.toString(day)).append("天");
        }
        if (hour > 0) {
            str.append(Long.toString(hour)).append("小时");
        }
        if (StrUtil.isEmpty(str.toString())) {
            return "即将结束";
        }

        return str.toString();
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

    public static boolean isValidDate(String dateStr, String pattern) {
        boolean validate = true;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(dateStr);
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            validate = false;
        }
        return validate;
    }
}
