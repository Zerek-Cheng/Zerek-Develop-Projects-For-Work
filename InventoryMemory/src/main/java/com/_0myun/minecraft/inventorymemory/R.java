package com._0myun.minecraft.inventorymemory;

import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if (!sender.isOp()) return true;
        if (!(sender instanceof Player)) {
            sender.sendMessage("控制台不能用");
            return true;
        }
        Player p = (Player) sender;
        PlayerInventory inv = p.getInventory();
        List<String> list = getConfig().getStringList(p.getUniqueId().toString());
        if (args.length < 1) return true;
        if (args[0].equalsIgnoreCase("save")) {
            saveInv(p.getUniqueId().toString(), inv);
            p.updateInventory();
            p.sendMessage("§b背包保存成功");
        }
        if (args[0].equalsIgnoreCase("load")) {
            loadInv(p.getUniqueId().toString(), inv);
            p.updateInventory();
            p.sendMessage("§b背包恢复成功");
        }
        return true;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChangeLoad(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        World from = e.getFrom();
        World to = p.getLocation().getWorld();
        if (from.getName().equalsIgnoreCase(to.getName())) return;
        List<String> list = getConfig().getStringList("worlds");
        if (list.contains(from.getName())) {
            loadInv(p.getUniqueId().toString() + "-world", p.getInventory());
            p.updateInventory();
            p.sendMessage("§b背包恢复成功");
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onChangeSave(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        World from = e.getFrom();
        World to = p.getLocation().getWorld();
        if (from.getName().equalsIgnoreCase(to.getName())) return;
        List<String> list = getConfig().getStringList("worlds");
        if (list.contains(to.getName())) {
            saveInv(p.getUniqueId().toString() + "-world", p.getInventory());
            p.updateInventory();
            p.sendMessage("§b背包保存成功");
        }
        saveConfig();
    }

    public void saveInv(String key, PlayerInventory inv) {
        saveArmor(key, inv);
        List<String> list = getConfig().getStringList(key);
        inv.forEach(itemStack -> {
            if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
            try {
                list.add(StreamSerializer.getDefault().serializeItemStack(itemStack));
            } catch (IOException e) {

            }
        });
        getConfig().set(key, list);
        inv.clear();
    }

    public void loadInv(String key, PlayerInventory inv) {
        loadArmor(key, inv);
        List<String> list = getConfig().getStringList(key);
        if (list.isEmpty()) return;
        inv.clear();
        list.forEach(itemStr -> {
            try {
                inv.addItem(StreamSerializer.getDefault().deserializeItemStack(itemStr));
            } catch (Exception ex) {

            }
        });
        getConfig().set(key, new ArrayList<>());
    }


    public void saveArmor(String key, PlayerInventory inv) {
        List<String> list = getConfig().getStringList(key + "_armors");
        ItemStack[] armors = inv.getArmorContents();
        inv.setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
        for (ItemStack itemStack : armors) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
            try {
                list.add(StreamSerializer.getDefault().serializeItemStack(itemStack));
            } catch (IOException e) {

            }
        }

        getConfig().set(key + "_armors", list);
    }

    public void loadArmor(String key, PlayerInventory inv) {
        List<String> list = getConfig().getStringList(key + "_armors");
        List<ItemStack> armors = new ArrayList<>();
        if (list.isEmpty()) return;
        list.forEach(itemStr -> {
            try {
                armors.add(StreamSerializer.getDefault().deserializeItemStack(itemStr));
            } catch (Exception ex) {

            }
        });
        inv.setArmorContents(armors.toArray(new ItemStack[0]));
        getConfig().set(key + "_armors", new ArrayList<>());
    }
}
