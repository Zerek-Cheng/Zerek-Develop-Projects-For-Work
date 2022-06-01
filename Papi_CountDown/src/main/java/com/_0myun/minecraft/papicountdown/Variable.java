package com._0myun.minecraft.papicountdown;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.OfflinePlayer;

public class Variable extends EZPlaceholderHook {
    public Variable() {
        super(PapiCountDown.plugin, "countdown");
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        long from = System.currentTimeMillis();
        long to = Long.valueOf(params);
        long need = to - from;
        return secondToTime(Double.valueOf(need / 1000l).longValue());
    }

    /**
     * 将秒数转换为日时分秒，
     *
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        if (second < 0) return "时辰已到";
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second / 60;            //转换分钟
        second = second % 60;                //剩余秒数
        if (days > 0) {
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        } else {
            return hours + "小时" + minutes + "分" + second + "秒";
        }
    }
}