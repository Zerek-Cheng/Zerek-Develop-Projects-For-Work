/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Effect
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Builder
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.meta.FireworkMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.Data;
import com.pedrojm96.referralsystem.Variable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Util {
    public static String plugin_header = "&e\u00ab--------[&7&l&oReferral System&e]--------\u00bb";
    public static String plugin_footer = "&e\u00ab--------------------------------\u00bb";

    public static String rColor(String string) {
        String string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)string);
        return string2;
    }

    public static boolean isint(String string) {
        try {
            int n = Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public static List<String> rColorList(List<String> list) {
        int n = 0;
        while (n < list.size()) {
            String string = ChatColor.translateAlternateColorCodes((char)'&', (String)list.get(n));
            list.set(n, string);
            ++n;
        }
        return list;
    }

    public static String formatime(long l) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        while (l > 60L) {
            if (n2 > 12) {
                ++n;
                n2 = 0;
            }
            if (n3 > 60) {
                ++n2;
                n3 = 0;
            }
            l -= 60L;
            ++n3;
        }
        if (n == 0 && n2 == 0 && n3 == 0) {
            String string = String.valueOf(l) + "s";
            return string;
        }
        if (n == 0 && n2 == 0) {
            String string = String.valueOf(n3) + "m " + l + "s";
            return string;
        }
        if (n == 0) {
            String string = String.valueOf(n2) + "h " + n3 + "m " + l + "s";
            return string;
        }
        String string = String.valueOf(n) + "d " + n2 + "h " + n3 + "m " + l + "s";
        return string;
    }

    public static void forFirework(Player player) {
        int n = 0;
        while (n < 5) {
            int n2 = n * 10;
            ReferralSystem.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)ReferralSystem.getInstance(), new Runnable(){

                @Override
                public void run() {
                    Util.fireword(player);
                }
            }, (long)n2);
            ++n;
        }
    }

    public static void forParticles(Player player) {
        int n = 0;
        while (n < 5) {
            int n2 = n * 10;
            ReferralSystem.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)ReferralSystem.getInstance(), new Runnable(){

                @Override
                public void run() {
                    Util.Particles(player);
                }
            }, (long)n2);
            ++n;
        }
    }

    public static void Particles(Player player) {
        ArrayList<Location> arrayList = Util.getCircle(player.getLocation(), 2.0, 10);
        for (Location location : arrayList) {
            Location location2 = new Location(location.getWorld(), location.getX(), location.getY() + 1.0, location.getZ());
            player.playEffect(location, Effect.ENDER_SIGNAL, 1);
            player.playEffect(location2, Effect.HEART, 1);
        }
    }

    public static ArrayList<Location> getCircle(Location location, double d, int n) {
        World world = location.getWorld();
        double d2 = 6.283185307179586 / (double)n;
        ArrayList<Location> arrayList = new ArrayList<Location>();
        int n2 = 0;
        while (n2 < n) {
            double d3 = (double)n2 * d2;
            double d4 = location.getX() + d * Math.cos(d3);
            double d5 = location.getZ() + d * Math.sin(d3);
            arrayList.add(new Location(world, d4, location.getY(), d5));
            ++n2;
        }
        return arrayList;
    }

    public static void fireword(Player player) {
        Firework firework = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        Random random = new Random();
        int n = random.nextInt(5) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (n == 1) {
            type = FireworkEffect.Type.BALL;
        }
        if (n == 2) {
            type = FireworkEffect.Type.BALL_LARGE;
        }
        if (n == 3) {
            type = FireworkEffect.Type.BURST;
        }
        if (n == 4) {
            type = FireworkEffect.Type.CREEPER;
        }
        if (n == 5) {
            type = FireworkEffect.Type.STAR;
        }
        int n2 = random.nextInt(256);
        int n3 = random.nextInt(256);
        int n4 = random.nextInt(256);
        Color color = Color.fromRGB((int)n2, (int)n4, (int)n3);
        n2 = random.nextInt(256);
        n3 = random.nextInt(256);
        n4 = random.nextInt(256);
        Color color2 = Color.fromRGB((int)n2, (int)n4, (int)n3);
        FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(color).withFade(color2).with(type).trail(random.nextBoolean()).build();
        fireworkMeta.addEffect(fireworkEffect);
        int n5 = random.nextInt(2) + 1;
        fireworkMeta.setPower(n5);
        firework.setFireworkMeta(fireworkMeta);
    }

    public static boolean isdouble(String string) {
        try {
            double d = Double.parseDouble(string);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public static List<String> rVariablesList(List<String> list, Player player) {
        int n = 0;
        while (n < list.size()) {
            String string = Variable.replaceVariables(list.get(n), player);
            list.set(n, string);
            ++n;
        }
        return list;
    }

    public static int inserCode() {
        int n = 0;
        while (n == 0) {
            Random random = new Random();
            int n2 = (int)(random.nextDouble() * 99999.0 + 1000.0);
            if (ReferralSystem.data.checkCode(n2)) continue;
            n = n2;
        }
        return n;
    }

}

