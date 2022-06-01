//
// Decompiled by Procyon v0.5.30
//

package com.mlgqcraft.fixarcaneworkbench;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class a extends JavaPlugin implements Listener {
    private static String a;
    private static Location ab;
    private Map<String, Location> map;
    private int abc;

    public a() {
        this.map = new HashMap<String, Location>();
        this.abc = 0;
    }

    public final void onEnable() {
        this.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
        this.getLogger().info("\u4fee\u590d\u5965\u672f\u5de5\u4f5c\u53f0\u63d2\u4ef6\u5df2\u52a0\u8f7d~~ by:AeXiaohu");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void a(final PlayerInteractEvent evt) {
        try {
            if (!evt.getPlayer().isOp() && evt.getClickedBlock().getType().name().equals("THAUMCRAFT_BLOCKTABLE") && (evt.getClickedBlock().getData() == 15 || evt.getClickedBlock().getData() == 0 || evt.getClickedBlock().getData() == 1) && evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!this.map.containsValue(evt.getClickedBlock().getLocation())) {
                    com.mlgqcraft.fixarcaneworkbench.a.a = evt.getPlayer().getName();
                    com.mlgqcraft.fixarcaneworkbench.a.ab = evt.getClickedBlock().getLocation();
                    this.abc = 1;
                    return;
                }
                if (evt.getClickedBlock().getType().name().equals("THAUMCRAFT_BLOCKTABLE") && evt.getClickedBlock().getData() == 15 && evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    evt.setCancelled(true);
                    evt.getPlayer().sendMessage("§c\u4f60\u65e0\u6cd5\u6253\u5f00\u6b63\u5728\u4f7f\u7528\u7684\u5965\u672f\u5de5\u4f5c\u53f0");
                }
            }
        } catch (Exception ex) {

        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void a(final InventoryOpenEvent evt) {
        if (!evt.getPlayer().isOp() && this.abc == 1 && evt.getPlayer().getName() == com.mlgqcraft.fixarcaneworkbench.a.a) {
            this.map.put(com.mlgqcraft.fixarcaneworkbench.a.a, com.mlgqcraft.fixarcaneworkbench.a.ab);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void a(final InventoryCloseEvent evt) {
        if (!evt.getPlayer().isOp() && this.map.containsKey(evt.getPlayer().getName())) {
            this.map.remove(evt.getPlayer().getName());
            this.abc = 0;
        }
    }

    public final void onDisable() {
        this.getLogger().info("\u4fee\u590d\u5965\u672f\u5de5\u4f5c\u53f0\u63d2\u4ef6\u5df2\u5378\u8f7d~~ by:AeXiaohu");
    }
}
