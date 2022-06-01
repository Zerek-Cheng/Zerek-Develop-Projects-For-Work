package com._0myun.minecraft.banimodtemer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGet(PlayerPickupItemEvent e) {
        ItemStack itemStack = e.getItem().getItemStack();
        if (!Main.INSTANCE.isBan(itemStack)) return;
        itemStack.setType(Material.AIR);
        itemStack.setAmount(0);
        e.setCancelled(true);
        e.getPlayer().sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        if (!Main.INSTANCE.isBan(itemStack)) return;
        itemStack.setType(Material.AIR);
        itemStack.setAmount(0);
        e.setCancelled(true);
        ((Player) e.getWhoClicked()).sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack itemStack = inv.getItem(i);
            if (!Main.INSTANCE.isBan(itemStack)) continue;
            inv.setItem(i, new ItemStack(Material.AIR, 0));
            p.sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if (!Main.INSTANCE.isBan(block)) return;
        e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
        e.setCancelled(true);
        e.getPlayer().sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e){
        Block block = e.getBlock();
        if (!Main.INSTANCE.isBan(block)) return;
        block.setType(Material.AIR);
        e.setDropItems(false);
        e.setCancelled(true);
        e.getPlayer().sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRight(PlayerInteractEvent e) {
        Action action = e.getAction();
        if (!action.equals(Action.RIGHT_CLICK_BLOCK) && !action.equals(Action.LEFT_CLICK_BLOCK)) return;
        Block block = e.getClickedBlock();
        if (!Main.INSTANCE.isBan(block)) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
    }
}
