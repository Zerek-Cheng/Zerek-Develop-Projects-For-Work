package com._0myun.minecraft.playerpapi;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class PlayerPapi extends JavaPlugin {

    public static PlayerPapi INSTANCE;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
/*        PlaceholderExpansion expansion = new PlaceholderExpansion() {

            @Override
            public String getIdentifier() {
                return "ppapi";
            }

            @Override
            public String getAuthor() {
                return "0MYUN";
            }

            @Override
            public String getVersion() {
                return "RELEASE";
            }

            @Override
            public String onPlaceholderRequest(Player p, String oPapi) {
                String[] split = oPapi.split("_");
                if (split.length < 1) return "unknown";
                String pName = split[0];
                oPapi = "%"+oPapi.substring((pName + "_").length())+"%";
                if (!Bukkit.getOfflinePlayer(pName).isOnline())
                    return "unonline";
                return PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(pName), oPapi);
            }
        };*/
        PlaceholderExpansion expansion = new PlaceholderExpansion() {

            @Override
            public String getIdentifier() {
                return "ppapi";
            }

            @Override
            public String getAuthor() {
                return "0MYUN";
            }

            @Override
            public String getVersion() {
                return "RELEASE";
            }

            @Override
            public String onPlaceholderRequest(Player p, String oPapi) {
                if (oPapi.equalsIgnoreCase("player"))
                    return INSTANCE.getPlayer(p.getUniqueId());
                String[] split = oPapi.split("_");
                if (split.length < 1) return "unknown";
                if (!Bukkit.getOfflinePlayer(INSTANCE.getPlayer(p.getUniqueId())).isOnline())
                    return "unonline";
                return PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(INSTANCE.getPlayer(p.getUniqueId())), "%" + oPapi + "%");
            }
        };
        expansion.register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("参数不足");
            return true;
        }
        Player p = (Player) sender;
        getConfig().set("data." + p.getUniqueId(), args[0]);
        return true;
    }

    public String getPlayer(UUID uuid) {
        return getConfig().getString("data." + uuid, Bukkit.getOfflinePlayer(uuid).getName());
    }
}
