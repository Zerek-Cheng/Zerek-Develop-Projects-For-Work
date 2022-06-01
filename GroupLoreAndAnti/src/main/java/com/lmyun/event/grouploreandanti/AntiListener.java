package com.lmyun.event.grouploreandanti;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class AntiListener extends BukkitRunnable {
    Main plugin;

    public AntiListener(Main main) {
        this.plugin = main;
    }

    @Override
    public void run() {
        Player[] playerArray = Bukkit.getOnlinePlayers();
        List<Player> playersList = Arrays.asList(playerArray);
        Iterator players = playersList.iterator();
        while (players.hasNext()) {
            Player p = (Player) players.next();
            if (!p.isOnline()) {
                continue;
            }
            if (!this.plugin.isAnti(p.getName())) {
                continue;
            }
            if (p.isOp()) {
                p.setOp(false);
                this.plugin.getLogger().log(Level.WARNING, p.getName() + "异常OP取消");
            }
            if (!p.getGameMode().equals(GameMode.SURVIVAL)) {
                p.setGameMode(GameMode.SURVIVAL);
                this.plugin.getLogger().log(Level.WARNING, p.getName() + "异常模式取消");
            }
        }
    }
}
