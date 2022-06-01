package com.lmyun.event.banandbuff;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BanListener implements Listener {
    private Main plugin;

    public BanListener(Main main) {
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
        boolean banned = this.plugin.isBanned(group, item);
        if (banned) {
            e.setCancelled(banned);
            if (this.needDelete(item.getTypeId())) {
                item.setTypeId(0);
                e.setCurrentItem(item);
            }
            p.sendMessage(LangUtil.getLang("lang1"));
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
        boolean banned = this.plugin.isBanned(group, item);
        if (banned) {
            p.sendMessage(LangUtil.getLang("lang1"));
            e.setCancelled(true);
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
        boolean banned = this.plugin.isBanned(group, item);
        if (banned) {
            e.setCancelled(banned);
            if (this.needDelete(item.getTypeId())) {
                item.setTypeId(0);
                p.setItemInHand(item);
            }
            p.sendMessage(LangUtil.getLang("lang1"));
        }
    }

    @EventHandler
    public void onClickBlock(PlayerInteractEvent e) {
        Action action = e.getAction();
        if (!action.equals(Action.LEFT_CLICK_BLOCK) || !action.equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = e.getClickedBlock();
        String group = Main.permission.getPlayerGroups(e.getPlayer())[0];
        boolean banned = this.plugin.isBanned(group, new ItemStack(block.getType(), 1));
        if (banned) {
            e.setCancelled(banned);
            e.getPlayer().sendMessage(LangUtil.getLang("lang1"));
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        this.onInvClick(e);
    }

    public boolean needDelete(int id) {
        ArrayList<Integer> list = new ArrayList<>();
        /*list.add(4280);
        list.add(4281);
        list.add(4282);
        list.add(4283);
        list.add(4284);
        list.add(4285);
        list.add(4286);
        list.add(5115);
        list.add(5116);
        list.add(5117);
        list.add(5118);
        list.add(5119);
        list.add(5120);
        list.add(5995);
        list.add(7006);
        list.add(7037);
        list.add(7038);
        list.add(7039);
        list.add(7040);*/
        return list.contains(id);
    }
}
