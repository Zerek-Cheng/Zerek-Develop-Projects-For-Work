/*
 * Decompiled with CFR 0_133.
 *
 * Could not onEnbale_Trash the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package com.oraman.drd.listener;

import com._0myun.minecraft.trashanddrop.TrashAndDrop;
import com.oraman.drd.ExpFix;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DeathListener
        implements Listener {
    FileConfiguration fc;
    YamlConfiguration conf;
    Economy economy;

    public DeathListener(FileConfiguration fc, YamlConfiguration conf, Economy economy) {
        this.fc = fc;
        this.conf = conf;
        this.economy = economy;
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasPermission("DeathRandomDrop.item") || player.isOp()) {
            event.setKeepInventory(true);
        }
        List<String> noPluginWorlds = this.fc.getStringList("disable-worlds");
        String playerWorld = player.getWorld().getName();
        for (String string : noPluginWorlds) {
            if (!string.equalsIgnoreCase(playerWorld)) continue;
            return;
        }
        if (this.fc.getBoolean("death-item.enable") && !player.hasPermission("DeathRandomDrop.item") && !player.isOp()) {
            event.setKeepInventory(true);
            int origindropnum = event.getDrops().size();
            int randomdropnum = this.fc.getInt("death-item.item-num");
            if (randomdropnum > 0 && origindropnum > 0) {
                List<ItemStack> dropItems = new ArrayList();
                if (randomdropnum >= origindropnum) {
                    dropItems = event.getDrops();
                }
                while (dropItems.size() != origindropnum && dropItems.size() != randomdropnum) {
                    ItemStack dropItem;
                    int randomNumber = (int) Math.round(Math.random() * (double) origindropnum - 1.0);
                    if (randomNumber >= origindropnum) {
                        randomNumber = origindropnum - 1;
                    }
                    if (randomNumber < 0) {
                        randomNumber = 0;
                    }
                    if (dropItems.contains((dropItem = event.getDrops().get(randomNumber))))
                        continue;
                    dropItems.add(dropItem);
                }
                if (dropItems.contains(player.getInventory().getLeggings())) {
                    player.getInventory().setLeggings(null);
                }
                if (dropItems.contains(player.getInventory().getBoots())) {
                    player.getInventory().setBoots(null);
                }
                if (dropItems.contains(player.getInventory().getHelmet())) {
                    player.getInventory().setHelmet(null);
                }
                if (dropItems.contains(player.getInventory().getChestplate())) {
                    player.getInventory().setChestplate(null);
                }
                if ((Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11")) && dropItems.contains((Object) player.getInventory().getItemInOffHand())) {
                    player.getInventory().setItemInOffHand(null);
                }
                player.getInventory().removeItem(dropItems.toArray(new ItemStack[dropItems.size()]));
                if (dropItems != null && dropItems.size() > 0) {
                    String itemdropmessage = "§c§l你因为死亡丢失了物品 : ";
                    for (ItemStack itemStack : dropItems) {
                        TrashAndDrop.plugin.dropItem(itemStack);
                        String displayName = this.conf.getString(itemStack.getType().name()) != null ? this.conf.getString(itemStack.getType().name()) : itemStack.getType().name();
                        int amount = itemStack.getAmount();
                        itemdropmessage = String.valueOf(itemdropmessage) + "§b" + displayName + "§f × §4" + amount + "§f,";
                    }
                    player.sendMessage(itemdropmessage.substring(0, itemdropmessage.length() - 1));
                }
            }
        }
        if (this.fc.getBoolean("death-money.enable") && !player.hasPermission("DeathRandomDrop.money") && !player.isOp()) {
            double originbalance = this.economy.getBalance((OfflinePlayer) player);
            double dropbalance = 0.0;
            if (originbalance > 0.0) {
                if (this.fc.getString("death-money.money-mode").equalsIgnoreCase("percent")) {
                    dropbalance = originbalance / 100.0 * this.fc.getDouble("death-money.money-num");
                } else if (this.fc.getString("death-money.money-mode").equalsIgnoreCase("fix")) {
                    dropbalance = this.fc.getDouble("death-money.money-num");
                }
                if (dropbalance > 0.0) {
                    this.economy.withdrawPlayer((OfflinePlayer) player, dropbalance);
                    player.sendMessage("§c§l你因为死亡损失了金钱 : §b￥§4" + new DecimalFormat().format(dropbalance));
                }
            }
        }
        if (this.fc.getBoolean("death-xp.enable") && !player.hasPermission("DeathRandomDrop.xp") && !player.isOp()) {
            event.setKeepLevel(true);
            event.setDroppedExp(0);
            float originxp = ExpFix.getTotalExperience(player);
            float dropxp = 0.0f;
            if (originxp > 0.0f) {
                if (this.fc.getString("death-xp.xp-mode").equalsIgnoreCase("percent")) {
                    dropxp = (float) ((double) (originxp / 100.0f) * this.fc.getDouble("death-xp.xp-num"));
                } else if (this.fc.getString("death-xp.xp-mode").equalsIgnoreCase("fix")) {
                    dropxp = (float) this.fc.getDouble("death-xp.xp-num");
                }
            }
            if ((int) dropxp > 0) {
                ExpFix.setTotalExperience(player, (int) (originxp - (float) ((int) dropxp)));
                player.sendMessage("§c§l你因为死亡损失了经验 : §4" + (int) dropxp + " §c§l点 ");
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack itemStack = e.getItemDrop().getItemStack().clone();
        e.getItemDrop().setItemStack(new ItemStack(Material.AIR));
        TrashAndDrop.plugin.dropItem(itemStack);
        p.sendMessage("§2§l【" + p.getName() + "】丢弃的物品进入公共垃圾桶，功能菜单打开垃圾桶查询。");
        //p.performCommand("trash");
    }
}

