package com._0myun.minecraft.eventmsg.debug.plotme.debugplotme;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    public PlotAPI api;

    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getServer().getPluginManager();
        final Plugin plotsquared = manager.getPlugin("PlotSquared");
        api = new PlotAPI();
        Bukkit.getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (!api.isPlotWorld(p.getWorld())) {
            return;
        }
        Location invLoc = null;
        if (invLoc == null) {
            invLoc = p.getLocation();
        }
        Plot plot = api.getPlot(invLoc);
        if (plot == null) {
            e.setCancelled(true);
            p.sendMessage("目标区域不存在地皮..无法操作...");
            return;
        }
        if (plot.isOwner(p.getUniqueId()) || plot.isAdded(p.getUniqueId())) {
            return;
        }
        e.setCancelled(true);
        p.sendMessage("你不是箱子所在区域的主人或者共享者");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDrag(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();
        Location invLoc = null;
        if (invLoc == null) {
            invLoc = p.getLocation();
        }
        if (!api.isPlotWorld(p.getWorld())) {
            return;
        }
        Plot plot = api.getPlot(invLoc);
        if (plot == null) {
            e.setCancelled(true);
            p.sendMessage("目标区域不存在地皮..无法操作...");
            return;
        }
        if (plot.isOwner(p.getUniqueId()) || plot.isAdded(p.getUniqueId())) {
            return;
        }
        e.setCancelled(true);
        p.sendMessage("你不是箱子所在区域的主人或者共享者");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onOpen(InventoryOpenEvent e) {
        HumanEntity p = e.getPlayer();
        Location invLoc = null;
        if (invLoc == null) {
            invLoc = p.getLocation();
        }
        if (!api.isPlotWorld(p.getWorld())) {
            return;
        }
        Plot plot = api.getPlot(invLoc);
        if (plot == null) {
            e.setCancelled(true);
            p.sendMessage("目标区域不存在地皮..无法操作...");
            return;
        }
        if (plot.isOwner(p.getUniqueId()) || plot.isAdded(p.getUniqueId())) {
            return;
        }
        e.setCancelled(true);
        p.sendMessage("你不是箱子所在区域的主人或者共享者");
    }

}
