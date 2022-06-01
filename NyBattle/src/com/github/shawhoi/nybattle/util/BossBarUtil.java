// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.util;

import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import java.util.Arrays;
import java.util.HashMap;

import static org.inventivetalent.bossbar.BossBarAPI.Style.NOTCHED_10;

public class BossBarUtil
{
    private static HashMap<String, BossBar> playerbar;
    
    public static void setPlayerViewBossBar(final Player p) {
        if (!BossBarUtil.playerbar.containsKey(p.getName())) {
            BossBar bar = BossBarAPI.addBar(Arrays.asList(p), p.getName(), BossBarAPI.Color.WHITE, BossBarAPI.Style.NOTCHED_10, 1.0f);

            final BossBar bb = bar;
            bb.setProgress(0.0f);
            BossBarUtil.playerbar.put(p.getName(), bb);
            BossBarUtil.playerbar.get(p.getName()).addPlayer(p);
        }
        final int yaw = (int)p.getLocation().getYaw();
        String title = "";
        final int by = buildYaw(yaw);
        title = ((by - 20 < 0) ? (360 - (20 - by)) : (by - 20)) + " " + ((by - 15 < 0) ? (360 - (15 - by)) : (by - 15)) + " " + ((by - 10 < 0) ? (360 - (10 - by)) : (by - 10)) + " " + ((by - 5 < 0) ? (360 - (5 - by)) : (by - 5)) + " " + by + " " + ((by + 5 > 360) ? (by + 5 - 360) : (by + 5)) + " " + ((by + 10 > 360) ? (by + 10 - 360) : (by + 10)) + " " + ((by + 15 > 360) ? (by + 15 - 360) : (by + 15)) + " " + ((by + 20 > 360) ? (by + 20 - 360) : (by + 20));
        BossBarUtil.playerbar.get(p.getName()).setMessage(title.replace(" 90", " ¡ìe¡ìl[E]¡ìf").replace(" 180", " ¡ìa¡ìl[S]¡ìf").replace(" 270", " ¡ìb¡ìl[W]¡ìf").replace(" 360", " ¡ì6¡ìl[N]¡ìf").replace(" 0", " ¡ì6¡ìl[N]¡ìf"));
    }
    
    public static Integer buildYaw(final int y) {
        final int yaw = (y > 0) ? y : (360 + y);
        String sy = String.valueOf(yaw);
        final char[] chars = sy.toCharArray();
        if (yaw % 10 <= 5 && yaw % 10 > 0) {
            chars[sy.length() - 1] = String.valueOf("5").charAt(0);
        }
        else {
            chars[sy.length() - 1] = String.valueOf("0").charAt(0);
        }
        sy = String.valueOf(chars);
        return Integer.parseInt(sy);
    }
    
    public static void removePlayerViewBossBar(final Player p) {
        BossBarUtil.playerbar.get(p.getName()).removePlayer(p);
        BossBarUtil.playerbar.remove(p.getName());
    }
    
    static {
        BossBarUtil.playerbar = new HashMap<>();
    }
}
