package com.WeiBoss.bossshoptr.Util;

import com.WeiBoss.bossshoptr.File.Config;
import com.WeiBoss.bossshoptr.File.Message;
import com.WeiBoss.bossshoptr.Main;
import org.bukkit.Bukkit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static Main plugin = Main.instance;
    private static String Color = onDateReplaceA(Message.Color);
    private static String YEAR = Color + "yyyy" + onDateReplaceA(Message.Year);
    private static String MONTH = Color + "M" + onDateReplaceA(Message.Month);
    private static String DAY = Color + "d" + onDateReplaceA(Message.Day);
    private static String HOUR = Color + "H" + onDateReplaceA(Message.Hour);
    private static String SECOND = Color + "s" + onDateReplaceA(Message.Second);
    private static String MINUTE = Color + "m" + onDateReplaceA(Message.Minute);
    private final static SimpleDateFormat DATE_YEAR_FORMAT = new SimpleDateFormat(YEAR + MONTH + DAY + HOUR + MINUTE + SECOND);
    public final static SimpleDateFormat YMD_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private static String onDateReplaceA(String date) {
        return date
                .replace("f", "飯")
                .replace("l", "麓")
                .replace("m", "濹")
                .replace("n", "曩")
                .replace("k", "磡")
                .replace("a", "錒")
                .replace("b", "寳")
                .replace("c", "黜")
                .replace("d", "噵")
                .replace("e", "㕎");
    }

    private static String onDateReplaceB(String date) {
        return date
                .replace("飯", "f")
                .replace("麓", "l")
                .replace("濹", "m")
                .replace("曩", "n")
                .replace("磡", "k")
                .replace("錒", "a")
                .replace("寳", "b")
                .replace("黜", "c")
                .replace("噵", "d")
                .replace("㕎", "e");
    }

    public static String getDate(Date date) {
        return onDateReplaceB(DATE_YEAR_FORMAT.format(date));
    }

    public static String getDateToStr(Date date) {
        return YMD_DATE_FORMAT.format(date);
    }

    public static Date getNowDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }

    public static int getLastHour(Date date) {
        int times = Config.LogTime;
        int Hour = getHourSpace(getNowDate(), date);
        Hour = times * 24 - Hour;
        return Hour;
    }

    public static int getHourSpace(Date date1, Date date2) {
        long dateA = 0;
        long dateB = 0;
        try {
            dateA = YMD_DATE_FORMAT.parse(YMD_DATE_FORMAT.format(date1)).getTime();
            dateB = YMD_DATE_FORMAT.parse(YMD_DATE_FORMAT.format(date2)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) ((dateA - dateB) / (1000 * 60 * 60));
    }
}