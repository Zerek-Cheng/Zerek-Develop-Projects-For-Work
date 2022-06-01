package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public static FileConfiguration getConfig() {
        return VexRedEnvelope.plugin.getConfig();
    }

    public static boolean isEnable(RedPacketType type) {
        return getConfig().getBoolean("type." + type);
    }

    public static boolean chatWhileGet() {
        return getConfig().getBoolean("chatWhileGet");
    }

    public static boolean broadcast() {
        return getConfig().getBoolean("broadcast");
    }

    public enum RedPacketType {
        GOLD("gold"),

        POINT("point"),

        ITEM("item");
        @Getter
        String type;

        RedPacketType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type;
        }
    }
}
