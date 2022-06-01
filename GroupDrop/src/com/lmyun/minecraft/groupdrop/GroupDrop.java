package com.lmyun.minecraft.groupdrop;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class GroupDrop extends JavaPlugin implements Listener {
    FileConfiguration config;
    public static Permission permission = null;
    private HashMap<UUID, UUID> lastKiller = new HashMap();

    @Override
    public void onEnable() {
        setupPermissions();
        saveConfig();
        config = getConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDeath(PlayerDeathEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getEntity().getKiller() instanceof Player)) {
            return;
        }
        Player p = e.getEntity();
        Player pD = p.getKiller();
        if (this.lastKiller.get(p.getUniqueId()) != null && this.lastKiller.get(p.getUniqueId()).equals(pD.getUniqueId())) {
            pD.sendMessage("该玩家上次也是被你杀死的，故不掉落证明");
            return;
        }
        this.lastKiller.put(p.getUniqueId(), pD.getUniqueId());
        String group = permission.getPrimaryGroup(p);
        ConfigurationSection groupItemConfig = this.config.getConfigurationSection(group);
        //System.out.println(group);
        if (groupItemConfig == null) {
            return;
        }
        ItemStack groupItem = new ItemStack(groupItemConfig.getInt("id"));
        groupItem.setDurability(Short.valueOf(String.valueOf(groupItemConfig.getInt("sonId"))));
        ItemMeta itemMeta = groupItem.getItemMeta();
        itemMeta.setDisplayName(groupItemConfig.getString("name"));
        itemMeta.setLore(groupItemConfig.getStringList("lore"));
        groupItem.setItemMeta(itemMeta);
        p.getLocation().getWorld().dropItem(p.getLocation(), groupItem);

    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            config = getConfig();
            sender.sendMessage("重载成功");
        }
        return true;
    }
}


