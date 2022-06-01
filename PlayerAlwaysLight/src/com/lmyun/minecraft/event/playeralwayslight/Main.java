package com.lmyun.minecraft.event.playeralwayslight;

import javafx.scene.paint.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

import java.util.Collection;

public class Main extends JavaPlugin implements Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        p.addPotionEffect(new PotionEffect(
                PotionEffectType.getById(24),
                24000000,
                5
        ));
        return super.onCommand(sender, command, label, args);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String color = getConfig().getString("color");
        new BukkitRunnable() {
            @Override
            public void run() {
                Player p = e.getPlayer();
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for (Player pt : players) {
                    if (p.getUniqueId().equals(pt.getUniqueId())) {
                        continue;
                    }
                    GlowAPI.setGlowing(p, GlowAPI.Color.WHITE, pt);
                }
                GlowAPI.setGlowing(p, false, p);
            }
        }.runTaskTimerAsynchronously(this, 24, 24);
    }

}
