package com._0myun.minecraft.dentallaboratories.listener;

import com._0myun.minecraft.dentallaboratories.Main;
import com._0myun.minecraft.dentallaboratories.bin.Event;
import com._0myun.minecraft.dentallaboratories.bin.Item;
import com._0myun.minecraft.dentallaboratories.events.PlayerItemLevelUpEvent;
import com._0myun.minecraft.dentallaboratories.events.PlayerItemLevelUpSuccessEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class LevelUpListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onLevelUp(PlayerItemLevelUpEvent e) {
        Item item = e.getItemType();
        int from = e.getFrom();
        HashMap<Integer, Double> chances = item.getChances();
        Double rand = chances.get(from);
        if (rand == null) rand = chances.get(0);
        if (rand == null) rand = 0d;
        e.setCancelled(!(Math.random() < rand));
        if (e.isCancelled()) {
            e.getPlayer().sendMessage(Main.plugin.getLangManager().get("lang8"));
            return;
        }
        e.getPlayer().sendMessage(Main.plugin.getLangManager().get("lang7"));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onLeveUpSuccess(PlayerItemLevelUpSuccessEvent e) {
        Player p = e.getPlayer();
        int to = e.getTo();
        ItemStack item = e.getItem();
        Item itemType = e.getItemType();

        Event event = itemType.getEvents().get(to);
        if (event == null) return;
        String type = event.getType();
        String[] data = event.getData().split(":");
        if (type.equalsIgnoreCase("change")) {
            item.setTypeId(Integer.valueOf(data[0]));
            if (data.length >= 2) item.setDurability(Short.valueOf(data[1]));
            p.sendMessage(Main.plugin.getLangManager().get("lang9"));
        }
    }
}
