package com._0myun.minecraft.playersignature;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.UUID;

public final class R extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("参数不足");
            return true;
        }
        Player p = (Player) sender;
        if (args[0].equalsIgnoreCase("open")) {
            p.openInventory(getInv());
            return true;
        }
        set(p.getUniqueId(), args[0]);
        p.sendMessage("设置成功咯");
        return true;
    }

    public void set(UUID uuid, String sign) {
        sign = sign.replace("&", "§");
        if (!getConfig().isSet("data")) getConfig().createSection("data");
        ConfigurationSection data = getConfig().getConfigurationSection("data");
        if (!getConfig().isSet("data")) data = getConfig().createSection("data");
        data.set(uuid.toString(), sign);
        getConfig().set("data", data);
    }

    public String get(UUID uuid) {
        if (!getConfig().isSet("data." + uuid.toString())) return null;
        return getConfig().getString("data." + uuid.toString());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof Holder) e.setCancelled(true);
    }

    public Inventory getInv() {
        Holder holder = new Holder();
        Inventory inventory = Bukkit.createInventory(holder, 6 * 9, "个性签名");
        holder.setInventory(inventory);
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (get(p.getUniqueId()) == null) return;
            ItemStack itemStack = new ItemStack(getConfig().getInt("item"));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(p.getDisplayName());
            itemMeta.setLore(Arrays.asList(get(p.getUniqueId())));
            itemStack.setItemMeta(itemMeta);
            inventory.addItem(itemStack);
        });
        return inventory;
    }

}
