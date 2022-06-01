package com._0myun.minecraft.eventmsg.itembreaker;

import com._0myun.minecraft.eventmsg.itembreaker.bin.BreakAble;
import com.sun.istack.internal.NotNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigManager {
    public static List<BreakAble> getAllRule() {
        return (List<BreakAble>) ItemBreaker.getPlugin().getConfig().getList("group");
    }

    public static boolean canBreak(ItemStack item) {
        return searchRule(item) != null;
    }

    public static BreakAble searchRule(@NotNull List<String> lores) {
        List<BreakAble> rules = getAllRule();
        BreakAble[] result = {null};
        rules.forEach((rule) -> {
            String ruleLore = rule.getLore();
            lores.forEach((lore) -> {
                if (lore.contains(ruleLore)) result[0] = rule;
            });
        });
        return result[0];
    }

    public static BreakAble searchRule(@NotNull ItemStack item) {
        if (item.getType().equals(Material.AIR)) return null;
        if (!item.getItemMeta().hasLore()) return null;
        return searchRule(item.getItemMeta().getLore());
    }
}
