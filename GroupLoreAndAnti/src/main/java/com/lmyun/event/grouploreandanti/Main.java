package com.lmyun.event.grouploreandanti;

import lombok.Getter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Main extends JavaPlugin {
    @Getter
    HashMap<String, FileConfiguration> pluginConfig = new HashMap<>();
    public static Permission permission = null;

    @Override
    public void onEnable() {
        this.setupPermissions();
        this.loadConfig();
        LangUtil.lang = this.getPluginConfig().get("Lang");
        new AntiListener(this).runTaskTimerAsynchronously(this, 24, 24);
        Bukkit.getPluginManager().registerEvents(new GroupLoreListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.loadConfig();
        sender.sendMessage(LangUtil.getLang("lang2"));
        return true;
    }

    public void loadConfig() {
        saveResource("DisAnti.yml", false);
        this.pluginConfig.put("DisAnti", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/DisAnti.yml")));
        saveResource("GroupLore.yml", false);
        this.pluginConfig.put("GroupLore", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/GroupLore.yml")));
        saveResource("Lang.yml", false);
        this.pluginConfig.put("Lang", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Lang.yml")));
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public boolean isAnti(String player) {
        return !this.pluginConfig.get("DisAnti").getStringList("disAnti").contains(player);
    }

    public String getGroupInLores(List<String> lores) {
        FileConfiguration groupLoreCon = this.pluginConfig.get("GroupLore");
        Iterator<String> groupLoreIter = groupLoreCon.getKeys(false).iterator();
        while (groupLoreIter.hasNext()) {
            String groupLore = groupLoreIter.next();
            Iterator<String> loreIterator = lores.iterator();
            while (loreIterator.hasNext()) {
                String lore = loreIterator.next();
                if (lore.contains(groupLore)) {
                    return groupLoreCon.getString((groupLore));
                }
            }
        }
        return null;
    }

    public String getGroupInLores(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR) || item.getItemMeta() == null || item.getItemMeta().getLore() == null) {
            return null;
        }
        ItemMeta itemMeta = item.getItemMeta();
        return this.getGroupInLores(itemMeta.getLore());
    }

    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("GroupLoreAndAnti");
    }
}
