package com.ral.manages.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeUtil {

    public static DateTimeFormatter formatter_Date = DateTimeFormat.forPattern("yyyyMMdd");
    public static DateTimeFormatter formatter_Time = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前时间
     */
    public  String currentDate(){
        DateTime dateTime = DateTime.now();
        return dateTime.toString(formatter_Date);
    }
    public static String currentTime(){
        DateTime dateTime = DateTime.now();
        return dateTime.toString(formatter_Time);
    }

}
