package com._0myun.minecraft.enchantbreakblockrules;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("reload ok");
        return true;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        ItemStack itemInHand = p.getItemInHand();
        int typeId = b.getTypeId();
        byte data = b.getData();
        Map<Enchantment, Integer> enchantments = itemInHand.getEnchantments();
        enchantments.forEach((em, level) -> {
            String name = em.getName();
            List<String> rule = getConfig().getStringList("rules." + name);
            rule.forEach(ruleStr -> {
                if (!ruleStr.contains(":")) ruleStr += ":*";
                String[] ids = ruleStr.split(":");
                if (ids.length == 2 && ids[1].equalsIgnoreCase("*")) ids[1] = String.valueOf(data);
                if (typeId == Integer.valueOf(ids[0]) && String.valueOf(data).equalsIgnoreCase(ids[1])) {
                    e.setCancelled(true);
                    p.sendMessage(String.format(getConfig().getString("lang1"), name));
                }
            });
        });
    }
}
