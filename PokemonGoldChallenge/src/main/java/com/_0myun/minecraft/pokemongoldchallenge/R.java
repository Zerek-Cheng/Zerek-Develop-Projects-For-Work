package com._0myun.minecraft.pokemongoldchallenge;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class R extends JavaPlugin implements Listener {
    public static Economy economy = null;
    public static R INSTANCE;
    public static Queue<UUID> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();
        Bukkit.getPluginManager().registerEvents(this, this);
        new QueueChecker().runTaskTimerAsynchronously(this, 20l, 20l);
        Bukkit.getPluginManager().registerEvents(new BattleListener(), this);
    }

    @Override
    public synchronized void onDisable() {//关服统一退钱
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!queue.contains(p.getUniqueId())) continue;
            economy.depositPlayer(p, getConfig().getDouble("cost"));
            queue.remove(p.getUniqueId());
        }
    }

    @EventHandler
    public synchronized void onQuit(PlayerQuitEvent e) {//离开时退钱
        Player p = e.getPlayer();
        if (!queue.contains(p.getUniqueId())) return;
        economy.depositPlayer(p, getConfig().getDouble("cost"));
        queue.remove(e.getPlayer().getUniqueId());
    }

    public String lang(String lang) {
        return getConfig().getString("lang." + lang, lang);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==1&&args[0].equalsIgnoreCase("reload")){
            reloadConfig();
            sender.sendMessage(lang("lang11"));
        }
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
            if (queue.contains(p.getUniqueId())) {
                p.sendMessage(lang("lang12"));
                return true;
            }
            if (!economy.has(p, getConfig().getDouble("cost")) || !economy.withdrawPlayer(p, getConfig().getDouble("cost")).transactionSuccess()) {
                p.sendMessage(lang("lang14"));
                return true;
            }
            Bukkit.broadcastMessage(lang("lang1").replace("%player%", p.getName()));
            queue.offer(p.getUniqueId());
            p.sendMessage(lang("lang2"));
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("quit")) {
            if (!queue.contains(p.getUniqueId())) {
                p.sendMessage(lang("lang13"));
                return true;
            }
            queue.remove(p.getUniqueId());
            economy.depositPlayer(p, getConfig().getDouble("cost"));
            p.sendMessage(lang("lang3"));
        }
        return true;
    }

}
