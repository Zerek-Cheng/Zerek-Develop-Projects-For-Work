package com._0myun.minecraft.betonquestpapiv2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        new Variable().hook();
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("error");
            return true;
        }
        Player p = (Player) sender;
        this.set(p.getUniqueId(), args[0]);
        //sender.sendMessage("ok");
        this.saveConfig();
        return super.onCommand(sender, command, label, args);
    }

    public void set(UUID p, String str) {
        getConfig().set("data." + p.toString(), str);
    }

    public String get(UUID p) {
        String result = getConfig().getString("data." + p.toString());
        return result == null ? "暂无" : result;
    }
}
