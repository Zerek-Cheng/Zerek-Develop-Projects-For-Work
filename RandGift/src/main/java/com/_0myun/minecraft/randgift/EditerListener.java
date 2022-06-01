package com._0myun.minecraft.randgift;

import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditerListener implements Listener {
    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        HumanEntity p = e.getPlayer();
        Inventory inv = e.getInventory();
        if (!(inv.getHolder() instanceof SetterHolder)) return;
        SetterHolder holder = (SetterHolder) inv.getHolder();
        String name = holder.getName();
        List<String> items = RandGift.plugin.getItems(name);
        if (items == null) return;
        items.forEach(itemStr -> {
            try {
                inv.addItem(StreamSerializer.getDefault().deserializeItemStack(itemStr));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        p.sendMessage("成功载入数据...");
    }

    @EventHandler(ignoreCancelled = false)
    public void onClose(InventoryCloseEvent e) {
        HumanEntity p = e.getPlayer();
        Inventory inv = e.getInventory();
        if (!(inv.getHolder() instanceof SetterHolder)) return;
        SetterHolder holder = (SetterHolder) inv.getHolder();
        List<String> itemStrings = new ArrayList<>();
        Arrays.asList(inv.getContents()).forEach(itemStack -> {
            if (itemStack == null || itemStack.getType().equals(Material.AIR) || itemStack.getAmount() <= 0) return;
            try {
                itemStrings.add(StreamSerializer.getDefault().serializeItemStack(itemStack));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        RandGift.plugin.setItems(holder.getName(), itemStrings);
        if (RandGift.plugin.getAmount(holder.getName()) == 0) RandGift.plugin.setAmount(holder.getName(), 1);
        RandGift.plugin.saveConfig();
        p.sendMessage("保存成功...");
    }
}
