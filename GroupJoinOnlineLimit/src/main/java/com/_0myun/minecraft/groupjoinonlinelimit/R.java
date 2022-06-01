package com._0myun.minecraft.groupjoinonlinelimit;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class R extends JavaPlugin implements Listener {
    public static Permission permission = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        permission = permissionProvider.getProvider();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        reloadConfig();
        sender.sendMessage("ok");
        return true;
    }

    @EventHandler
    public void onJoin(PlayerPreLoginEvent e) {
        try {

            UUID uuid = e.getUniqueId();
            String group = permission.getPrimaryGroup("World", Bukkit.getOfflinePlayer(uuid));
            if (!getConfig().isSet("limit." + group)) return;
            int limit = getConfig().getInt("limit." + group);
            if (Bukkit.getOnlinePlayers().size() < limit) return;
            e.setResult(PlayerPreLoginEvent.Result.KICK_OTHER);
            e.setKickMessage(getConfig().getString("lang"));
        }catch (Exception ex){

        }
    }
}
