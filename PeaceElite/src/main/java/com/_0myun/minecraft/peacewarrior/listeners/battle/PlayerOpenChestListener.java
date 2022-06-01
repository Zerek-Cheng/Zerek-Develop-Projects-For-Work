package com._0myun.minecraft.peacewarrior.listeners.battle;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PlayerOpenChestListener implements Listener {
    static List<String> opened = new LinkedList<>();
/*
    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        Inventory inv = e.getInventory();
        try {
            Player p = (Player) e.getPlayer();
            PlayerData pD = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
            if (!pD.getStat().equals(PlayerData.Stat.PLAY)) return;
            BattleMap map = MapManager.getMapByName(pD.getMap());
            if (!(inv.getHolder() instanceof Chest)) return;
            Location loc = inv.getLocation();
            if (loc == null) return;
            if (opened.contains(pD.getMap() + "-" + loc.toString())) return;
            inv.clear();
            opened.add(pD.getMap() + "-" + loc.toString());
            p.sendMessage(R.INSTANCE.lang("lang22"));//初始化
            BattleManager.randChest(map, inv);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/


    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block == null || !block.getType().equals(Material.CHEST)) return;
        Chest chest = (Chest) block.getState();
        Inventory inv = chest.getInventory();
        try {
            Player p = (Player) e.getPlayer();
            PlayerData pD = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
            if (!pD.getStat().equals(PlayerData.Stat.PLAY)) return;
            BattleMap map = MapManager.getMapByName(pD.getMap());
            if (!(inv.getHolder() instanceof Chest)) return;
            Location loc = block.getLocation();
            if (loc == null) return;
            if (opened.contains(pD.getMap() + "-" + loc.toString())) return;
            inv.clear();
            opened.add(pD.getMap() + "-" + loc.toString());
            p.sendMessage(R.INSTANCE.lang("lang22"));//初始化
            BattleManager.randChest(map, inv);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void clear(String map) {
        Iterator<String> iter = opened.iterator();
        while (iter.hasNext()) {
            String next = iter.next();
            if (next.startsWith(map)) iter.remove();
        }
    }
}
