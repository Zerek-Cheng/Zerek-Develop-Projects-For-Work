package com._0myun.minecraft.interactthreshold;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    private HashMap<Long, HashMap<UUID, Integer>> amount = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    public Long now() {
        return Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
    }

    public HashMap<UUID, Integer> getNowAmount() {
        Long now = now();
        HashMap<UUID, Integer> nowAmount = this.amount.get(now);
        nowAmount = nowAmount == null ? new HashMap<>() : nowAmount;
        if (!this.amount.containsKey(now)) this.amount.put(now, nowAmount);
        return nowAmount;
    }

    public int getNowPlayer(UUID p) {
        Integer amount = getNowAmount().get(p);
        return amount == null ? 0 : amount;
    }

    public void addNow(UUID p) {
        HashMap<UUID, Integer> nowAmount = getNowAmount();
        Integer amount = nowAmount.get(p);
        amount = amount == null ? 0 : amount;
        nowAmount.put(p, amount + 1);
        if (amount > getConfig().getInt("interact")) {
            Bukkit.getPlayer(p).kickPlayer(getConfig().getString("lang"));
        }
    }

    public void takeNow(UUID p) {
        HashMap<UUID, Integer> nowAmount = getNowAmount();
        Integer amount = nowAmount.get(p);
        amount = amount == null ? 0 : amount;
        nowAmount.put(p, amount - 1);
        if (amount > getConfig().getInt("interact")) {
            Bukkit.getPlayer(p).kickPlayer(getConfig().getString("lang"));
        }
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent e) {
        Action action = e.getAction();
        Player p = e.getPlayer();
        this.addNow(p.getUniqueId());
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        this.addNow(p.getUniqueId());
    }

    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        this.takeNow(p.getUniqueId());
    }

    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onBlockDestory(BlockBreakEvent e) {
        Player p = e.getPlayer();
        this.takeNow(p.getUniqueId());
    }
}
