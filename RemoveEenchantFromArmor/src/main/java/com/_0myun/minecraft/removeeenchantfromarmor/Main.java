package com._0myun.minecraft.removeeenchantfromarmor;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin {

    static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();
        new PlayerChecker().runTaskTimer(this, 5, 5);
    }


    public boolean checkAndRemove(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return false;
        List<String> enchant = getConfig().getStringList("ecnchant");
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.putAll(itemStack.getEnchantments());

        List<Enchantment> list = new ArrayList<>();
        boolean[] result = {false};
        enchantments.forEach((type, level) -> {
            if (enchant.contains(type.getName().toUpperCase())) {
                result[0] = true;
                list.add(type);
            }
        });

        for (Enchantment enchantment : list) {
            itemStack.removeEnchantment(enchantment);
        }
        return result[0];
    }

}
