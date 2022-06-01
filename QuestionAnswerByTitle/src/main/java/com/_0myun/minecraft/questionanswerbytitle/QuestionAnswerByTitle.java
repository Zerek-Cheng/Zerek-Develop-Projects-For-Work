package com._0myun.minecraft.questionanswerbytitle;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class QuestionAnswerByTitle extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        if (args.length == 2) {
            getConfig().set("wait." + sender.getName() + "." + args[0], args[1]);
            sender.sendMessage("提交审核成功...");
            saveConfig();
        }
        return true;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        ConfigurationSection config = getConfig().getConfigurationSection("list");
        for (String key : config.getKeys(false)) {
            if (!message.contains(key)) continue;
            new BukkitRunnable() {
                @Override
                public void run() {

                    p.sendMessage(config.getString(key));
                }
            }.runTaskLaterAsynchronously(this, 5);
            return;
        }
    }
}
