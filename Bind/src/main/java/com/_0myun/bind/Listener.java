package com._0myun.bind;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static com._0myun.bind.Utils.*;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (!hasBind(item)) return;
        if (p.getUniqueId().equals(UUID.fromString(getOwner(item)))) return;
        if (R.INSTANCE.checkMeta(item, true) && Utils.getOwner(item) == null) setOwner(item, p.getUniqueId());
        e.setCancelled(true);
        p.sendMessage(R.INSTANCE.lang("lang2"));
    }


    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        Item item = e.getItem();

        ItemStack itemStack = item.getItemStack();
        if (hasBind(itemStack)) return;
        if (!R.INSTANCE.checkMeta(itemStack, true)) return;
        setOwner(itemStack, p.getUniqueId());

        item.setItemStack(itemStack);
        p.sendMessage(R.INSTANCE.lang("lang3"));
    }

    @EventHandler
    public void onPickUp(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        Item item = e.getItemDrop();

        ItemStack itemStack = item.getItemStack();
        if (hasBind(itemStack)) {
            e.setCancelled(true);
            return;
        }
        if (!R.INSTANCE.checkMeta(itemStack, true)) return;
        setOwner(itemStack, p.getUniqueId());

        item.setItemStack(itemStack);
        p.sendMessage(R.INSTANCE.lang("lang3"));
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickUpProtect(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        Item item = e.getItem();

        ItemStack itemStack = item.getItemStack();

        if (!hasBind(itemStack)) return;
        if (getOwner(itemStack).equalsIgnoreCase(p.getUniqueId().toString())) return;
        item.setItemStack(null);
        e.getItem().getWorld().dropItem(e.getItem().getLocation(), itemStack);
        p.sendMessage(R.INSTANCE.lang("lang4"));
        e.setCancelled(true);
    }
}
