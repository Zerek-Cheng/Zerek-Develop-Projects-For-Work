
package cn.mcraft.timeperm.utils;

import java.io.Closeable;

public enum TimeConvert {
    D("天", 86400000L),
    H("小时", 3600000L),
    M("分钟", 60000L),
    S("秒", 1000L),
    SS("毫秒", 1L);

    private String timeChar;
    private long millseconds;

    private TimeConvert(String timeChar, long millseconds) {
        this.timeChar = timeChar;
        this.millseconds = millseconds;
    }

    public String getTimeChar() {
        return this.timeChar;
    }

    public long getMillseconds() {
        return this.millseconds;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TimeConvert getTimeConvert(String time) {
        for (TimeConvert t : TimeConvert.values()) {
            if (!t.name().equalsIgnoreCase(time) && !t.getTimeChar().equalsIgnoreCase(time)) continue;
            return t;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long parseTime(String time) {
        String[] sp = time.toUpperCase().split(":");
        long millseconds = 0L;
        for (String s : sp) {
            char c;
            String timeS = "";
            for (int i = 0; i < s.length() && (c = s.charAt(i)) >= '0' && c <= '9'; ++i) {
                timeS = timeS + c;
            }
            try {
                millseconds += Long.parseLong(timeS) * TimeConvert.getTimeConvert(s.substring(timeS.length())).getMillseconds();
            } catch (Exception e) {
                return -1L;
            }
        }
        return millseconds;
    }

    public static String formatTime(long millseconds) {
        return TimeConvert.formatTime(millseconds, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String formatTime(long millseconds, boolean key) {
        String str = "";
        long time = 0L;
        for (TimeConvert t : TimeConvert.values()) {
            time = millseconds / t.millseconds;
            if (time <= 0L) continue;
            str = str + time + (key ? t.name() : t.timeChar) + ":";
            millseconds -= time * t.millseconds;
        }
        if (str.endsWith(":")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}

