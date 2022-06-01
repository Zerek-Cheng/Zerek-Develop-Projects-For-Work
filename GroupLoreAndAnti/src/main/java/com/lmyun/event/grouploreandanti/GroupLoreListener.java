package com.lmyun.event.grouploreandanti;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class GroupLoreListener implements Listener {
    private Main plugin;

    public GroupLoreListener(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        HumanEntity whoClicked = e.getWhoClicked();
        if (!(whoClicked instanceof Player)) {
            return;
        }
        Player p = (Player) whoClicked;
        if (p.isOp()) {
            return;
        }
        String group = Main.permission.getPlayerGroups(p)[0];
        ItemStack item = e.getCurrentItem();
        String needGroup = this.plugin.getGroupInLores(item);
        boolean banned = (needGroup != null && !needGroup.equalsIgnoreCase(group));
        if (banned) {
            p.sendMessage(LangUtil.getLang("lang1"));
        }else{
            e.setCancelled(banned);
        }
    }

    @EventHandler
    public void onPickItem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) {
            return;
        }
        String group = Main.permission.getPlayerGroups(p)[0];
        ItemStack item = e.getItem().getItemStack();
        String needGroup = this.plugin.getGroupInLores(item);
        boolean banned = needGroup != null && !needGroup.equalsIgnoreCase(group);
        if (banned) {
            p.sendMessage(LangUtil.getLang("lang1"));
        }else{
            e.setCancelled(banned);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (!(damager instanceof Player)) {
            return;
        }
        Player p = (Player) damager;
        if (p.isOp()) {
            return;
        }
        String group = Main.permission.getPlayerGroups(p)[0];
        ItemStack item = p.getItemInHand();
        String needGroup = this.plugin.getGroupInLores(item);
        boolean banned = needGroup != null && !needGroup.equalsIgnoreCase(group);
        if (banned) {
            p.sendMessage(LangUtil.getLang("lang1"));
        }else {
            e.setCancelled(banned);
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        this.onInvClick(e);
    }
}
