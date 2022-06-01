/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.Item;
import com.pedrojm96.referralsystem.LocalData;
import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralAPI;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.Data;
import com.pedrojm96.referralsystem.Util;
import com.pedrojm96.referralsystem.menuList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitScheduler;

public class EvenListener
implements Listener {
    @EventHandler
    public void InventoryClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().getName().equals(Util.rColor(Messages.MenuList()))) {
            inventoryClickEvent.setCancelled(true);
        }
        if (inventoryClickEvent.getInventory().getName().equals(Util.rColor(ReferralSystem.claim.getString("settings-name")))) {
            inventoryClickEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void Update(PlayerJoinEvent playerJoinEvent) {
        Player player;
        if (ReferralSystem.config.getBoolean("Update-Check") && (player = playerJoinEvent.getPlayer()).isOp()) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("http://www.spigotmc.org/api/general.php").openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.getOutputStream().write("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=29709".getBytes("UTF-8"));
                String string = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine();
                if (string.length() <= 7 && !string.equalsIgnoreCase(ReferralSystem.getInstance().getDescription().getVersion())) {
                    ReferralSystem.getInstance().getServer().getScheduler().scheduleAsyncDelayedTask((Plugin)ReferralSystem.getInstance(), new Runnable(){

                        @Override
                        public void run() {
                            player.sendMessage(Messages.plugin_heade());
                            player.sendMessage("");
                            player.sendMessage(Util.rColor("&a- &7There is a resource update avaliable for &7&l&oReferral System&7. Please update to recieve latest version."));
                            player.sendMessage(Util.rColor("&a- &7https://www.spigotmc.org/resources/referralsystem.29709/"));
                            player.sendMessage("");
                            player.sendMessage(Messages.plugin_footer());
                        }
                    }, 120L);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        UUID uUID = playerJoinEvent.getPlayer().getUniqueId();
        LocalData localData = new LocalData();
        if (ReferralSystem.data.checkData(uUID.toString())) {
            localData.setPoints(ReferralSystem.data.getPoints(uUID.toString()));
            localData.setReferrals(ReferralSystem.data.getReferrals(uUID.toString()));
            localData.setPlaytime(ReferralSystem.data.getPlayTime(uUID.toString()));
            localData.setTime(System.currentTimeMillis());
        } else {
            localData.setPoints(0);
            localData.setReferrals(0);
            localData.setPlaytime(0L);
            localData.setTime(System.currentTimeMillis());
        }
        ReferralSystem.localdata.put(uUID, localData);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        this.chetQuit(playerQuitEvent.getPlayer());
    }

    public void chetQuit(final Player player) {
        final UUID uUID = player.getUniqueId();
        ReferralSystem.getInstance().getServer().getScheduler().scheduleAsyncDelayedTask((Plugin)ReferralSystem.getInstance(), new Runnable(){

            @Override
            public void run() {
                if (ReferralSystem.config.getBoolean("Requires-PlayTime")) {
                    if (ReferralSystem.data.checkData(uUID.toString())) {
                        ReferralSystem.data.setPlayTime(uUID.toString(), ReferralSystem.localdata.get(uUID).getPlaytime());
                    } else {
                        ReferralSystem.data.insert(player, Util.inserCode());
                        ReferralSystem.data.setPlayTime(uUID.toString(), ReferralSystem.localdata.get(uUID).getPlaytime());
                    }
                }
                ReferralSystem.localdata.remove(uUID);
            }
        }, 20L);
    }

    @EventHandler
    public void MenuClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().getName().equals(Util.rColor(ReferralSystem.claim.getString("settings-name")))) {
            ItemStack itemStack = inventoryClickEvent.getCurrentItem();
            Player player = (Player)inventoryClickEvent.getWhoClicked();
            List<Item> list = ReferralSystem.loadItems(player);
            for (Item item : list) {
                if (!item.like(itemStack)) continue;
                if (item.isCommand()) {
                    if (item.hasPerm(player)) {
                        int n = ReferralAPI.getPoints((OfflinePlayer)player);
                        if (n >= item.getPrice()) {
                            int n2 = n - item.getPrice();
                            ReferralAPI.setPoints((OfflinePlayer)player, n2);
                            String string = Util.rColor("&e[&7&l&oMineCraft Wew&e] " + Messages.Reward_Given());
                            player.sendMessage(string);
                            item.executeCommands(player);
                            if (ReferralSystem.config.getBoolean("Fireworks-Enable")) {
                                Util.forFirework(player);
                            }
                            if (ReferralSystem.config.getBoolean("Sounds-Enable")) {
                                boolean bl = Bukkit.getBukkitVersion().split("-")[0].contains("1.8");
                                boolean bl2 = Bukkit.getBukkitVersion().split("-")[0].contains("1.7");
                                if (bl || bl2) {
                                    player.playSound(player.getLocation(), Sound.valueOf((String)"CLICK"), 2.0f, 2.0f);
                                } else {
                                    player.playSound(player.getLocation(), Sound.valueOf((String)"UI_BUTTON_CLICK"), 2.0f, 2.0f);
                                }
                            }
                        } else {
                            String string = Util.rColor("&c\u2716 " + Messages.Claim_No_Points());
                            player.sendMessage(string);
                        }
                    } else {
                        String string = Util.rColor("&c\u2716 " + Messages.Claim_No_Permission());
                        player.sendMessage(string);
                    }
                }
                if (item.kOpen()) continue;
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void MenuListClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().getName().equals(Util.rColor(Messages.MenuList()))) {
            ItemStack itemStack = inventoryClickEvent.getCurrentItem();
            Player player = (Player)inventoryClickEvent.getWhoClicked();
            if (itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().getDisplayName() != null) {
                if (inventoryClickEvent.getSlot() == 53) {
                    menuList.open(player, Integer.parseInt(inventoryClickEvent.getInventory().getItem(52).getItemMeta().getDisplayName()) + 1);
                }
                if (inventoryClickEvent.getSlot() == 51) {
                    menuList.open(player, Integer.parseInt(inventoryClickEvent.getInventory().getItem(52).getItemMeta().getDisplayName()) - 1);
                }
            }
        }
    }

}

