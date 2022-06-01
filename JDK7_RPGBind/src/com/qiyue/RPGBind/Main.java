// 
// Decompiled by Procyon v0.5.30
// 

package com.qiyue.RPGBind;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import java.io.File;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.configuration.file.FileConfiguration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    public static Main instance;
    public static Economy economy;
    public static FileConfiguration UUID;
    
    static {
        Main.UUID = null;
    }
    
    @EventHandler
    public void PlayerInteract(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        for (int i = 0; i < p.getInventory().getSize(); ++i) {
            final ItemStack item = p.getInventory().getItem(i);
            if (Main.instance.getUUID(item) != null && !Main.instance.getUUIDEnable(Main.instance.getUUID(item))) {
                p.getInventory().setItem(i, new ItemStack(Material.AIR));
                p.closeInventory();
                p.updateInventory();
            }
        }
    }
    
    @EventHandler
    public void InventoryCloses(final InventoryCloseEvent e) {
        final Player p = (Player)e.getPlayer();
        for (int i = 0; i < p.getInventory().getSize(); ++i) {
            final ItemStack item = p.getInventory().getItem(i);
            if (Main.instance.getUUID(item) != null && !Main.instance.getUUIDEnable(Main.instance.getUUID(item))) {
                p.getInventory().setItem(i, new ItemStack(Material.AIR));
                p.closeInventory();
                p.updateInventory();
            }
        }
    }
    
    public void start() {
        Bukkit.getScheduler().runTaskTimer((Plugin)this, (Runnable)new Runnable() {
            @Override
            public void run() {
                OfflinePlayer[] offlinePlayers;
                for (int length = (offlinePlayers = Bukkit.getServer().getOfflinePlayers()).length, j = 0; j < length; ++j) {
                    final OfflinePlayer ps = offlinePlayers[j];
                    final Player p = ps.getPlayer();
                    for (int i = 0; i < p.getInventory().getSize(); ++i) {
                        final ItemStack item = p.getInventory().getItem(i);
                        if (Main.instance.getUUID(item) != null && !Main.instance.getUUIDEnable(Main.instance.getUUID(item))) {
                            p.getInventory().setItem(i, new ItemStack(Material.AIR));
                            p.closeInventory();
                            p.updateInventory();
                        }
                    }
                }
            }
        }, 80L, 1200L);
    }
    
    public void onEnable() {
        (Main.instance = this).start();
        Main.instance.reloadPlugin();
        Main.instance.getCommand("rgb").setExecutor((CommandExecutor)new Commands(this));
        Main.instance.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        Bukkit.getConsoleSender().sendMessage("¡ìaRPGBind\u5df2\u52a0\u8f7d \u4f5c\u8005\u4e03\u6708 \u7981\u6b62\u5012\u5356!!");
    }
    
    public void reloadPlugin() {
        Main.instance.createConfig();
        Main.instance.createUUID();
        this.loadVault();
    }
    
    public void loadVault() {
        final RegisteredServiceProvider<Economy> economyProvider = (RegisteredServiceProvider<Economy>)this.getServer().getServicesManager().getRegistration((Class)Economy.class);
        Main.economy = (Economy)economyProvider.getProvider();
    }
    
    public void createConfig() {
        if (this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
            this.reloadConfig();
        }
        final File config = new File(this.getDataFolder(), "config.yml");
        if (!config.exists()) {
            this.saveDefaultConfig();
        }
    }
    
    public void createUUID() {
        final File f = new File(this.getDataFolder(), "UUID.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        Main.UUID = (FileConfiguration)(f.exists() ? YamlConfiguration.loadConfiguration(f) : new YamlConfiguration());
    }
    
    public boolean hasUUID(final String uuid) {
        for (final String node : Main.UUID.getConfigurationSection("").getKeys(false)) {
            for (final String nodes : Main.UUID.getConfigurationSection(node).getKeys(false)) {
                if (uuid.equals(Main.UUID.getString(String.valueOf(node) + "." + nodes + ".uuid"))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void saveUUID() {
        final File f = new File(this.getDataFolder(), "UUID.yml");
        try {
            Main.UUID.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getRandomAz() {
        final String chars = "abcdefghijklmnopqrstuvwxyz";
        return String.valueOf(chars.charAt((int)(Math.random() * 26.0)));
    }
    
    public String getRandomMath() {
        final String chars = "1234567890";
        return String.valueOf(chars.charAt((int)(Math.random() * 10.0)));
    }
    
    public boolean getRandomBoolean() {
        final int i = (int)(1.0 + Math.random() * 2.0);
        return i == 1;
    }
    
    public String madeMath(final int a) {
        String math = "";
        for (int i = 0; i < a; ++i) {
            if (Main.instance.getRandomBoolean()) {
                math = String.valueOf(math) + Main.instance.getRandomMath();
            }
            else {
                math = String.valueOf(math) + Main.instance.getRandomAz();
            }
        }
        return math;
    }
    
    public String madeUUID() {
        String uuid;
        for (uuid = String.valueOf(this.madeMath(8)) + "-" + this.madeMath(4) + "-" + this.madeMath(4) + "-" + this.madeMath(4) + "-" + this.madeMath(12); Main.instance.hasUUID(uuid); uuid = String.valueOf(this.madeMath(8)) + "-" + this.madeMath(4) + "-" + this.madeMath(4) + "-" + this.madeMath(4) + "-" + this.madeMath(12)) {}
        return uuid;
    }
    
    public void addUUID(final String uuid, final String name, final ItemStack item) {
        if (Main.UUID.contains(name)) {
            final int id = Main.UUID.getConfigurationSection(name).getKeys(false).size() + 1;
            Main.UUID.set(String.valueOf(name) + "." + id + ".uuid", (Object)uuid);
            Main.UUID.set(String.valueOf(name) + "." + id + ".item", (Object)item);
            Main.UUID.set(String.valueOf(name) + "." + id + ".enable", (Object)true);
        }
        else {
            Main.UUID.set(String.valueOf(name) + ".0.uuid", (Object)uuid);
            Main.UUID.set(String.valueOf(name) + ".0.item", (Object)item);
            Main.UUID.set(String.valueOf(name) + ".0.enable", (Object)true);
        }
        Main.instance.saveUUID();
        final List<String> lore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<String>();
        lore.add(String.valueOf(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("lore"))) + uuid);
        final ItemMeta im = item.getItemMeta();
        im.setLore((List)lore);
        item.setItemMeta(im);
        Bukkit.getPlayer(name).setItemInHand(item);
    }
    
    public ItemStack getItemStack(final ItemStack item) {
        final List<String> lore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<String>();
        final List<String> l = new ArrayList<String>();
        final int i = 0;
        for (final String node : lore) {
            if (node.contains(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("lore")))) {
                continue;
            }
            l.add(node);
        }
        final ItemMeta im = item.getItemMeta();
        im.setLore((List)l);
        item.setItemMeta(im);
        return item;
    }
    
    public String getUUIDID(final String uuid) {
        for (final String node : Main.UUID.getConfigurationSection("").getKeys(false)) {
            for (final String nodes : Main.UUID.getConfigurationSection(node).getKeys(false)) {
                if (uuid.equals(Main.UUID.getString(String.valueOf(node) + "." + nodes + ".uuid"))) {
                    return nodes;
                }
            }
        }
        return null;
    }
    
    public String getIDUUID(final String name, final String id) {
        return Main.UUID.getString(String.valueOf(name) + "." + id + ".uuid");
    }
    
    public String getUUIDPlayer(final String uuid) {
        for (final String node : Main.UUID.getConfigurationSection("").getKeys(false)) {
            for (final String nodes : Main.UUID.getConfigurationSection(node).getKeys(false)) {
                if (uuid.equals(Main.UUID.getString(String.valueOf(node) + "." + nodes + ".uuid"))) {
                    return node;
                }
            }
        }
        return null;
    }
    
    public ItemStack getUUIDItemStack(final String uuid) {
        for (final String node : Main.UUID.getConfigurationSection("").getKeys(false)) {
            for (final String nodes : Main.UUID.getConfigurationSection(node).getKeys(false)) {
                if (uuid.equals(Main.UUID.getString(String.valueOf(node) + "." + nodes + ".uuid"))) {
                    return Main.UUID.getItemStack(String.valueOf(node) + "." + nodes + ".item");
                }
            }
        }
        return null;
    }
    
    public boolean isEnable(final String id) {
        final List<String> a = (List<String>)Main.instance.getConfig().getStringList("NotBdItems");
        for (final String b : a) {
            if (b.equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean getUUIDEnable(final String uuid) {
        for (final String node : Main.UUID.getConfigurationSection("").getKeys(false)) {
            for (final String nodes : Main.UUID.getConfigurationSection(node).getKeys(false)) {
                if (uuid.equals(Main.UUID.getString(String.valueOf(node) + "." + nodes + ".uuid"))) {
                    return Main.UUID.getBoolean(String.valueOf(node) + "." + nodes + ".enable");
                }
            }
        }
        return true;
    }
    
    public void remove(final String uuid) {
        Main.UUID.set(String.valueOf(Main.instance.getUUIDPlayer(uuid)) + "." + Main.instance.getUUIDID(uuid) + ".enable", (Object)false);
        Main.instance.saveUUID();
    }
    
    public List<String> getUUIDList(final String name) {
        final List<String> list = new ArrayList<String>();
        if (!Main.UUID.contains(name)) {
            return list;
        }
        for (final String node : Main.UUID.getConfigurationSection(name).getKeys(false)) {
            if (!Main.instance.getUUIDEnable(Main.UUID.getString(String.valueOf(name) + "." + node + ".uuid"))) {
                continue;
            }
            final String id = node;
            final String uuid = Main.UUID.getString(String.valueOf(name) + "." + node + ".uuid");
            final String Itname = Main.instance.getUUIDItemStack(uuid).getItemMeta().getDisplayName().toString();
            list.add(String.valueOf(id) + "," + uuid + "," + Itname);
        }
        return list;
    }
    
    public String getUUID(final ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore() || !item.getItemMeta().hasDisplayName()) {
            return null;
        }
        final List<String> lore = (List<String>)item.getItemMeta().getLore();
        for (final String node : lore) {
            if (node.contains(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("lore")))) {
                return node.replace(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("lore")), "");
            }
        }
        return null;
    }
    
    public boolean isRPG(final ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains("¡ì");
    }
    
    public Economy getVault() {
        return Main.economy;
    }
    
    public void sendMessage(final Player p, final String m) {
        p.sendMessage(m.replace("&", "¡ì"));
    }
    
    public void sendMessageList(final Player p, final List<String> m) {
        for (final String s : m) {
            p.sendMessage(s.replace("&", "¡ì"));
        }
    }
}
