package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.RedPackage;
import org.bukkit.Bukkit;

public class LangUtil {
    public static String get(String lang) {
        return VexRedEnvelope.plugin.getConfig().getString("lang." + lang).replace("&", "§");
    }

    public static String variable(String str, RedPackage red) {
        str = str.replace("<red.owner.name>", red.getOwner() != null ? Bukkit.getOfflinePlayer(red.getOwner()).getName() : "后台")
                .replace("<red.amount>", String.valueOf(red.getAmount()))
                .replace("<reg.title>", red.getTitle())
                .replace("<word>", red.getWord());
        return str;
    }
}
