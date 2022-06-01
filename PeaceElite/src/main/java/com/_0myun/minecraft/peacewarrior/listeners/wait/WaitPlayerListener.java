package com._0myun.minecraft.peacewarrior.listeners.wait;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class WaitPlayerListener implements Listener {
    @EventHandler
    public void onClickInv(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.isOp()) return;
        try {
            if (DBManager.playerDataDao.queryForUUID(p.getUniqueId()).getStat().equals(PlayerData.Stat.WAIT))
                e.setCancelled(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) return;
        try {
            if (DBManager.playerDataDao.queryForUUID(p.getUniqueId()).getStat().equals(PlayerData.Stat.WAIT))
                e.setCancelled(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onPick(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) return;
        try {
            if (DBManager.playerDataDao.queryForUUID(p.getUniqueId()).getStat().equals(PlayerData.Stat.WAIT))
                e.setCancelled(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) return;
        try {
            if (DBManager.playerDataDao.queryForUUID(p.getUniqueId()).getStat().equals(PlayerData.Stat.WAIT))
                e.setCancelled(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onPlace(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) return;
        try {
            if (DBManager.playerDataDao.queryForUUID(p.getUniqueId()).getStat().equals(PlayerData.Stat.WAIT))
                e.setCancelled(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        Player p = e.getPlayer();
        try {
            if (!DBManager.playerDataDao.queryForUUID(p.getUniqueId()).getStat().equals(PlayerData.Stat.WAIT)) return;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (!p.isOp())e.setCancelled(true);
        ItemStack itemStack = e.getItem();
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(itemStack));
        boolean op = p.isOp();
        try {
            String cmd = nbt.getString("com._0myun.minecraft.peacewarrior.R.command");
            if (cmd == null || cmd.isEmpty()) return;
            if (!op) p.setOp(true);
            p.performCommand(cmd.replace("!player!", p.getName()));
        } catch (Exception ex) {

        } finally {
            if (!op) p.setOp(false);
        }
    }
}
