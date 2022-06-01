package com._0myun.minecraft.debug.eventmsg.d_stove;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    private HashMap<UUID, Location> openedLocation = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        this.openedLocation.remove(e.getPlayer().getUniqueId());
    }


    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        this.openedLocation.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onOpenInv(InventoryOpenEvent e) {
/*        try {
            Inventory inv = e.getInventory();
            Location loc = e.getInventory().getLocation();
            if (loc == null) return;
            this.openedLocation.put(e.getPlayer().getUniqueId(), loc);
        } catch (Exception ex) {

        }*/
    }

    @EventHandler
    public void onCloseInv(InventoryCloseEvent e) {
        this.openedLocation.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        try {
            Action action = e.getAction();
            if (!action.equals(Action.RIGHT_CLICK_BLOCK)) return;
            Player p = e.getPlayer();
            Block block = e.getClickedBlock();
            this.openedLocation.put(p.getUniqueId(), block.getLocation());
        } catch (Exception ex) {

        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTryBreak(PlayerInteractEvent e) {
        try {
            Action action = e.getAction();
            if (!action.equals(Action.LEFT_CLICK_BLOCK)) return;
            Player p = e.getPlayer();
            Block block = e.getClickedBlock();
            Location loc = block.getLocation();
            this.openedLocation.entrySet().iterator().forEachRemaining(entry -> {
                UUID openedUuid = entry.getKey();
                Location openedLoc = entry.getValue();
                if (loc.equals(openedLoc)) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(openedUuid);
                    if (!offlinePlayer.isOnline()) return;
                    Player openedP = offlinePlayer.getPlayer();
                    openedP.closeInventory();
                    //openedP.sendMessage("有玩家尝试破坏你现在打开或者刚刚打开过的GUI...为了防止BUG....关闭现在的GUI.....");
                    this.openedLocation.remove(openedP.getUniqueId());
                }
            });
        } catch (Exception ex) {

        }
    }
}

