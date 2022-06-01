package com._0myun.minecraft.keepbleed;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class BleedItem {
    public final static String bleedPattern = "(?<=流血几率\\+)\\d*(?=%)";
    public final static String damagePattern = "(?<=每秒流血伤害:)\\d*";
    public final static String timePattern = "(?<=流血持续:)\\d*(?=秒)";
    private int chance = 0;
    private int damage = 0;
    private int time = 0;

    public static BleedItem getBleedItem(@NotNull ItemStack item) {
        if (item.getType().equals(Material.AIR) || !item.getItemMeta().hasLore()) return new BleedItem();
        return getBleedItem(item.getItemMeta().getLore());
    }

    public static BleedItem getBleedItem(@NotNull List<String> lores) {
        BleedItem bleedItem = new BleedItem();
        lores.forEach(lore -> {
            String tmplore = delColor(lore);
            Matcher m = Pattern.compile(bleedPattern).matcher(tmplore);
            if (m.find()) {
                bleedItem.setChance(Integer.valueOf(tmplore.substring(m.start(), m.end())));
                return;
            }
            m = Pattern.compile(damagePattern).matcher(tmplore);
            if (m.find()) {
                bleedItem.setDamage(Integer.valueOf(tmplore.substring(m.start(), m.end())));
                return;
            }
            m = Pattern.compile(timePattern).matcher(tmplore);
            if (m.find()) {
                bleedItem.setTime(Integer.valueOf(tmplore.substring(m.start(), m.end())));
                return;
            }
        });
        return bleedItem;
    }

    public boolean validate() {
        return this.getChance() != 0 && this.getDamage() != 0 && this.time != 0;
    }

    public boolean rand() {
        int rand = Double.valueOf(Math.random() * 100).intValue();
        return rand <= this.getChance();
    }


    public static String delColor(String color) {
        try {
            StringBuffer sb = new StringBuffer(color);
            for (int i = sb.length() - 1; i >= 0; i--) {
                String subStr = sb.substring(i, i + 1);
                if (!subStr.equalsIgnoreCase("&") && !subStr.equalsIgnoreCase("§")) continue;
                sb.deleteCharAt(i);
                sb.deleteCharAt(i);
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
