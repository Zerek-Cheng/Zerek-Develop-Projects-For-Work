package com.WeiBoss.bossshoptr.Util;

import com.WeiBoss.bossshoptr.Constructor.Log;
import com.WeiBoss.bossshoptr.File.Config;
import com.WeiBoss.bossshoptr.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WeiUtil {
    private static Main plugin = Main.instance;

    public static String onReplace(String text) {
        return text
                .replace("&", "§");
    }

    public static boolean isNumber(String a) {
        return a.matches("^[0-9]*[1-9][0-9]*$");
    }

    public static void copyFile(InputStream inputStream, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] arrayOfByte = new byte['?'];
            int i;
            while ((i = inputStream.read(arrayOfByte)) > 0) {
                fileOutputStream.write(arrayOfByte, 0, i);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ItemStack createItem(int id, int data, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(Material.getMaterial(id), amount);
        item.setDurability((short) data);
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(name);
        if (lore != null)
            meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getLogItem(Log log) {
        ItemStack item = new ItemStack(log.getItem());
        String player = log.getPlayer().getName();
        String date = DateUtil.getDate(log.getDate());
        String shop = log.getShop();
        String name = log.getName();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        for (String s : Config.getItemLore()) {
            lore.add(s
                    .replace("%player%", player)
                    .replace("%date%", date)
                    .replace("%shop%", shop)
                    .replace("%name%", name));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static List<Log> getPlayerLog(Player p) {
        List<Log> log = new ArrayList<>(plugin.logList);
        List<Log> player = new ArrayList<>();
        for (Log log1 : log) {
            if (log1.getPlayer().getName().equals(p.getName())) {
                player.add(log1);
            }
        }
        return player;
    }

    public static List<Log> getShopLog(List<Log> list, String shop) {
        List<Log> logs = new ArrayList<>();
        for (Log log : list) {
            if (log.getShop().equals(shop)) {
                logs.add(log);
            }
        }
        return logs;
    }

    public static List<Log> getRecentLog(List<Log> list, Integer day) {
        List<Log> logs = new ArrayList<>();
        for (Log log : list) {
            Date date = log.getDate();
            if (DateUtil.getHourSpace(DateUtil.getNowDate(), date) <= day * 24) {
                logs.add(log);
            }
        }
        return logs;
    }

    public static List<Log> getDateSort(List<Log> logs) {
        List<Log> log = new ArrayList<>(logs);
        log.sort((o1,o2) -> o2.getDate().compareTo(o1.getDate()));
        return log;
    }

    public static HashMap<Integer, List<Log>> getPagination(List<Log> logs) {
        HashMap<Integer, List<Log>> map = new HashMap<>();
        List<Log> wei = new ArrayList<>();
        int page = 1;
        int size = 0;
        for (Log log : logs) {
            if (size >= 45) {
                map.put(page, new ArrayList<>(wei));
                wei.clear();
                size = 0;
                page++;
            }
            wei.add(log);
            size++;
        }
        if (wei.size() != 0) {
            map.put(page, new ArrayList<>(wei));
        }
        return map;
    }
}
