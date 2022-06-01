package com._0myun.minecraft.chatautoenter;

import me.clip.deluxechat.events.DeluxeChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(DeluxeChatEvent e) {
        List<Integer> at = new ArrayList<>();
        Player p = e.getPlayer();
        StringBuffer sb = new StringBuffer(e.getChatMessage());
        int nowLength = 0;
        boolean first = false;
        for (int i = 0; i < sb.length(); i++) {
            boolean enter = false;
            String sub = sb.substring(i, i + 1);
            String nextSub = null;
            if (i + 1 < sb.length()) {
                nextSub = sb.substring(i + 1, i + 2);
            }
            nowLength += !isChinese(sub) ? 1 : 2;
            if (nextSub != null) {
                int nextLength = nowLength + (Character.isLetterOrDigit(nextSub.charAt(0)) ? 1 : 2);
                if (nextLength > getConfig().getInt("enterLength")) {
                    at.add(i);
                    first = true;
                    enter = true;
                }
            }

            if (enter) {
                nowLength = 0;
                break;
            }
        }
        for (int i = at.size() - 1; i >= 0; i--) {
            Integer integer = at.get(i);
            sb.insert(integer, "\n");
        }
        e.setChatMessage(sb.toString());
    }

    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;

        return flg;
    }

}
