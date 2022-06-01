package com._0myun.eventmsg.minecraft.debuffwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class DeBuffWhiteList extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    Bukkit.getOnlinePlayers().forEach((p) -> {
                        if (!DeBuffWhiteList.this.getConfig().getStringList("player").contains(p.getName())) return;
                        List<PotionEffect> ids = new ArrayList<>();
                        p.getActivePotionEffects().forEach((po) -> {
                            if (DeBuffWhiteList.this.getConfig().getIntegerList("debuff").contains(po.getType().getId()))
                                ids.add(po);
                        });
                        ids.forEach((po) -> {
                            p.removePotionEffect(po.getType());
                        });
                    });
                } finally {

                }
            }
        }.runTaskTimer(this, 1, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("reload ok");
        return true;
    }
}
