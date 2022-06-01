package com._0myun.minecraft.chatlimit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public final class R extends JavaPlugin implements Listener {
    HashMap<UUID, Integer> chats = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) return true;
        sender.sendMessage("ok");
        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        if (msg.startsWith("/")) return;
        synchronized (p) {
            Integer chat = chats.getOrDefault(p.getUniqueId(), 0);
            if (chat >= getConfig().getInt("chat")) {
                e.setMessage("");
                e.setCancelled(true);
                p.sendMessage(getConfig().getString("lang"));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("cmd").replace("<player>", p.getName()));
                return;
            }
            chat++;
            chats.put(p.getUniqueId(), chat);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                synchronized (p) {
                    Integer chat = chats.getOrDefault(p.getUniqueId(), 0);
                    chat--;
                    chats.put(p.getUniqueId(), chat);
                }
            }
        }.runTaskLaterAsynchronously(this, getConfig().getInt("time") * 20);
    }
}
