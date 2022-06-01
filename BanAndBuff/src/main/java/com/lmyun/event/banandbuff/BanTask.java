package com.lmyun.event.banandbuff;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class BanTask extends BukkitRunnable {
    Main plugin;

    public BanTask(Main main) {
        this.plugin = main;
    }

    @Override
    public void run() {
/*        Collection<? extends Player> playersArray = Bukkit.getServer().getOnlinePlayers();
        playersArray.forEach((p) -> {
            if (!p.isOnline() || p.isOp()) {
                return;
            }
            String group = Main.permission.getPlayerGroups(p)[0];
            PlayerInventory inv = p.getInventory();
            ListIterator<ItemStack> itemIter = inv.iterator();
            while (itemIter.hasNext()) {
                ItemStack item = itemIter.next();
                if (!this.plugin.isBanned(group, item)) {
                    continue;
                }
                item.setType(Material.AIR);
                itemIter.set(item);
                p.sendMessage(LangUtil.getLang("lang1"));
            }
        });*/
        List<Player> playersList = Arrays.asList(Bukkit.getOnlinePlayers());
        Iterator playerIter = playersList.iterator();
        while (playerIter.hasNext()) {
            Player p = (Player) playerIter.next();
            if (!p.isOnline() || p.isOp()) {
                continue;
            }
            String group = Main.permission.getPlayerGroups(p)[0];
            PlayerInventory inv = p.getInventory();
            ListIterator<ItemStack> itemIter = inv.iterator();
            while (itemIter.hasNext()) {
                ItemStack item = itemIter.next();
                if (!this.plugin.isBanned(group, item)) {
                    continue;
                }
                item.setType(Material.AIR);
                itemIter.set(item);
                p.sendMessage(LangUtil.getLang("lang1"));
            }
        }
    }
}
