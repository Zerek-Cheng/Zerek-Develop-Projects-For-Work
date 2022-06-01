package com._0myun.minecraft.randattributelores;

import lombok.var;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin {

    public static Main INSTANCE;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;

        new MathPlaceHolder().hook();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        Player p = (Player) sender;
        ItemStack itemInHand = p.getItemInHand();
        boolean result = randItemLores(p, itemInHand);
        if (!result) {
            p.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        p.sendMessage(getConfig().getString("lang2"));
        return true;
    }

    public Map getConfig(int id) {
        for (var map : getConfig().getMapList("rules")) {
            List ids = (List) map.get("items");
            if (!ids.contains(id) && !ids.contains(String.valueOf(id))) continue;
            return map;
        }
        return null;
    }

    public boolean canRand(int id) {
        return getConfig(id) != null;
    }

    public boolean randItemLores(Player p, ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || itemStack.getItemMeta().hasLore())
            return false;
        if (!canRand(itemStack.getTypeId())) return false;
        Map config = getConfig(itemStack.getTypeId());
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lores = new ArrayList<>();
        lores.addAll((List<String>) config.get("static"));
        List<Map> randLore = (List<Map>) config.get("rand");
        randLore.forEach(map -> {
            String lore = (String) map.get("lore");
            double rand = Double.valueOf(String.valueOf(map.get("rand")));
            if (Math.random() > rand) return;
            lores.add(lore);
        });

        itemMeta.setLore(PlaceholderAPI.setPlaceholders(p, lores));
        itemStack.setItemMeta(itemMeta);
        p.updateInventory();
        return true;
    }
}
