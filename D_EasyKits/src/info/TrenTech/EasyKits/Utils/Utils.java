/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 */
package info.TrenTech.EasyKits.Utils;

import info.TrenTech.EasyKits.EasyKits;
import info.TrenTech.EasyKits.EasyKitsMod;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class Utils {
    private static EasyKitsMod plugin;

    public static void setPlugin(EasyKitsMod plugin) {
        Utils.plugin = plugin;
    }

    public static EasyKitsMod getplugin() {
        return plugin;
    }

    public static EasyKits getPluginContainer() {
        return plugin.getPluginContainer();
    }

    public static FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public static Logger getLogger() {
        return plugin.getLogger();
    }

    public static int getTimeInSeconds(String time) {
        String[] times = time.split(" ");
        int seconds = 0;
        String[] array = times;
        int length = array.length;
        int i = 0;
        while (i < length) {
            String t = array[i];
            if (t.matches("(\\d+)[s]$")) {
                seconds += Integer.parseInt(t.replace("s", ""));
            } else if (t.matches("(\\d+)[m]$")) {
                seconds += Integer.parseInt(t.replace("m", "")) * 60;
            } else if (t.matches("(\\d+)[h]$")) {
                seconds += Integer.parseInt(t.replace("h", "")) * 3600;
            } else if (t.matches("(\\d+)[d]$")) {
                seconds += Integer.parseInt(t.replace("d", "")) * 86400;
            } else if (t.matches("(\\d+)[w]$")) {
                seconds += Integer.parseInt(t.replace("w", "")) * 604800;
            }
            ++i;
        }
        return seconds;
    }

    public static String getReadableTime(int timeInSec) {
        int weeks = timeInSec / 604800;
        int wRemainder = timeInSec % 604800;
        int days = wRemainder / 86400;
        int dRemainder = wRemainder % 86400;
        int hours = dRemainder / 3600;
        int hRemainder = dRemainder % 3600;
        int minutes = hRemainder / 60;
        int seconds = hRemainder % 60;
        String time = null;
        if (weeks > 0) {
            String wks = " \u5468";
            time = String.valueOf(String.valueOf(weeks)) + wks;
        }
        if (days > 0 || days == 0 && weeks > 0) {
            String dys = " \u5929";
            time = time != null ? String.valueOf(String.valueOf(time)) + ", " + days + dys : String.valueOf(String.valueOf(days)) + dys;
        }
        if (hours > 0 || hours == 0 && days > 0) {
            String hrs = " \u65f6";
            time = time != null ? String.valueOf(String.valueOf(time)) + ", " + hours + hrs : String.valueOf(String.valueOf(hours)) + hrs;
        }
        if (minutes > 0 || minutes == 0 && days > 0 || minutes == 0 && hours > 0) {
            String min = " \u5206";
            time = time != null ? String.valueOf(String.valueOf(time)) + ", " + minutes + min : String.valueOf(String.valueOf(minutes)) + min;
        }
        if (seconds > 0) {
            String sec = " \u79d2";
            time = time != null ? String.valueOf(String.valueOf(time)) + seconds + sec : String.valueOf(String.valueOf(seconds)) + sec;
        }
        return time;
    }

    public static UUID getUUID(String playerName) {
        UUID uuid = null;
        HashMap<UUID, String> players = EasyKits.players;
        Set<Map.Entry<UUID, String>> keys = players.entrySet();
        for (Map.Entry<UUID, String> key : keys) {
            if (!key.getValue().equalsIgnoreCase(playerName)) continue;
            uuid = key.getKey();
            break;
        }
        return uuid;
    }
}

