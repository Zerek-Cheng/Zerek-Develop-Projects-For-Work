/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.PluginManager
 */
package info.TrenTech.EasyKits.Kit;

import info.TrenTech.EasyKits.Events.KitPlayerCooldownEvent;
import info.TrenTech.EasyKits.Events.KitPlayerEquipEvent;
import info.TrenTech.EasyKits.Events.KitPlayerLimitEvent;
import info.TrenTech.EasyKits.Events.WithdrawMoneyEvent;
import info.TrenTech.EasyKits.SQL.SQLPlayers;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class KitUser {
    private Player user;
    private Kit kit;
    private String userUUID;
    private int limit;
    private String dateObtained;

    public KitUser(Player user, Kit kit) {
        this.user = user;
        this.kit = kit;
        this.userUUID = user.getUniqueId().toString();
        if (!SQLPlayers.kitExist(this.userUUID, kit.getName())) {
            SQLPlayers.addKit(this.userUUID, kit.getName(), "2000-1-1 12:00:00", kit.getLimit(), "FALSE");
        }
        if (!SQLPlayers.tableExist("limit")) {
            SQLPlayers.createLimitTable();
        }

        this.limit = SQLPlayers.getLimit(this.userUUID, kit.getName());
        if (this.limit == -1) {
            this.limit = kit.getLimit();
        }
        this.dateObtained = SQLPlayers.getDateObtained(this.userUUID, kit.getName());
    }

    public int getCurrentLimit() {
        return this.limit;
    }

    public String getCooldownTimeRemaining() {
        if (!this.hasObtainedBefore()) {
            return null;
        }
        if (this.kit.getCooldown().equalsIgnoreCase("0")) {
            return null;
        }
        Date date = new Date();
        Date dateObtained = null;
        if (this.getDateObtained().equalsIgnoreCase("0")) {
            return null;
        }
        try {
            dateObtained = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.getDateObtained());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeSince = TimeUnit.MILLISECONDS.toSeconds(date.getTime() - dateObtained.getTime());
        int waitTime = Utils.getTimeInSeconds(this.kit.getCooldown());
        if ((long) waitTime - timeSince <= 0L) {
            return null;
        }
        return Utils.getReadableTime((int) ((long) waitTime - timeSince));
    }

    public String getDateObtained() {
        return this.dateObtained;
    }

    public boolean setDateObtained(String dateObtained) {
        KitPlayerCooldownEvent event = new KitPlayerCooldownEvent(this.user, this.kit);
        Bukkit.getServer().getPluginManager().callEvent((Event) event);
        if (!event.isCancelled()) {
            SQLPlayers.setDateObtained(this.userUUID, this.kit.getName(), dateObtained);
            this.dateObtained = dateObtained;
            return true;
        }
        return false;
    }

    public boolean setCurrentLimit(int limit) {
        KitPlayerLimitEvent event = new KitPlayerLimitEvent(this.user, this.kit);
        Bukkit.getServer().getPluginManager().callEvent((Event) event);
        if (!event.isCancelled()) {
            if (this.kit.getLimit() > 0) {
                SQLPlayers.setLimit(this.userUUID, this.kit.getName(), limit);
                this.limit = limit;
            }
            return true;
        }
        return false;
    }

    public boolean chargeUser() {
        WithdrawMoneyEvent event = new WithdrawMoneyEvent(this.user, this.kit, this);
        Bukkit.getServer().getPluginManager().callEvent((Event) event);
        if (event.isCancelled() || this.kit.getPrice() == 0.0) {
            return false;
        }
        if (Utils.getPluginContainer().economy.getBalance((OfflinePlayer) this.user) < this.kit.getPrice()) {
            return false;
        }
        Utils.getPluginContainer().economy.withdrawPlayer((OfflinePlayer) this.user, this.kit.getPrice());
        return true;
    }

    public boolean hasObtainedBefore() {
        return SQLPlayers.getObtained(this.userUUID, this.kit.getName()).equalsIgnoreCase("TRUE");
    }

    public boolean canAfford() {
        double price = this.kit.getPrice();
        double balance = Utils.getPluginContainer().economy.getBalance((OfflinePlayer) this.user);
        if (balance >= price) {
            return true;
        }
        return false;
    }

    public boolean applyKit() throws Exception {
        KitPlayerEquipEvent event = new KitPlayerEquipEvent(this.user, this.kit, this);
        Bukkit.getServer().getPluginManager().callEvent((Event) event);
        if (!event.isCancelled()) {
            ItemStack[] inv = this.kit.getInventory();
            ItemStack[] arm = this.kit.getArmor();
            Inventory tempInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder) this.user, InventoryType.PLAYER);
            Inventory tempArm = Utils.getPluginContainer().getServer().createInventory((InventoryHolder) this.user, 9);
            tempInv.setContents(this.user.getInventory().getContents());
            tempArm.setContents(this.user.getInventory().getArmorContents());
            int index = 0;
            ItemStack[] array = inv;
            int length = array.length;
            int j = 0;
            while (j < length) {
                ItemStack item = array[j];
                if (item == null) {
                    item = new ItemStack(Material.AIR);
                }
                if (tempInv.getItem(index) == null) {
                    tempInv.setItem(index, item);
                } else if (tempInv.firstEmpty() > -1) {
                    tempInv.addItem(new ItemStack[]{item});
                } else {
                    int amount = item.getAmount();
                    HashMap matchItem = this.user.getInventory().all(item);
                    int size = matchItem.size();
                    if (size >= item.getMaxStackSize()) {
                        int i = 10;
                        while (i <= 36) {
                            if (i == 36) {
                                Notifications notify = new Notifications("Inventory-Space", this.kit.getName(), this.user.getName(), 0.0, null, 0);
                                this.user.sendMessage(notify.getMessage());
                                return false;
                            }
                            if ((size -= item.getMaxStackSize()) < item.getMaxStackSize() && amount <= size) {
                                tempInv.setItem(index, item);
                            }
                            ++i;
                        }
                        Notifications notify2 = new Notifications("Inventory-Space", this.kit.getName(), this.user.getName(), 0.0, null, 0);
                        this.user.sendMessage(notify2.getMessage());
                        return false;
                    }
                    size = item.getMaxStackSize() - size;
                    if (amount > size) {
                        Notifications notify2 = new Notifications("Inventory-Space", this.kit.getName(), this.user.getName(), 0.0, null, 0);
                        this.user.sendMessage(notify2.getMessage());
                        return false;
                    }
                    tempInv.setItem(index, item);
                }
                ++index;
                ++j;
            }
            index = 0;
            ItemStack[] array2 = arm;
            int length2 = array2.length;
            int k = 0;
            while (k < length2) {
                Notifications notify3;
                ItemStack item = array2[k];
                if (index == 0 && item != null) {
                    if (tempArm.getItem(0) == null) {
                        tempArm.setItem(0, item);
                    } else {
                        if (tempInv.firstEmpty() <= -1) {
                            notify3 = new Notifications("Inventory-Space", this.kit.getName(), this.user.getName(), 0.0, null, 0);
                            this.user.sendMessage(notify3.getMessage());
                            return false;
                        }
                        tempInv.addItem(new ItemStack[]{item});
                    }
                }
                if (index == 1 && item != null) {
                    if (tempArm.getItem(1) == null) {
                        tempArm.setItem(1, item);
                    } else {
                        if (tempInv.firstEmpty() <= -1) {
                            notify3 = new Notifications("Inventory-Space", this.kit.getName(), this.user.getName(), 0.0, null, 0);
                            this.user.sendMessage(notify3.getMessage());
                            return false;
                        }
                        tempInv.addItem(new ItemStack[]{item});
                    }
                }
                if (index == 2 && item != null) {
                    if (tempArm.getItem(2) == null) {
                        tempArm.setItem(2, item);
                    } else {
                        if (tempInv.firstEmpty() <= -1) {
                            notify3 = new Notifications("Inventory-Space", this.kit.getName(), this.user.getName(), 0.0, null, 0);
                            this.user.sendMessage(notify3.getMessage());
                            return false;
                        }
                        tempInv.addItem(new ItemStack[]{item});
                    }
                }
                if (index == 3) {
                    if (item == null) break;
                    if (tempArm.getItem(3) == null) {
                        tempArm.setItem(3, item);
                        break;
                    }
                    if (tempInv.firstEmpty() > -1) {
                        tempInv.addItem(new ItemStack[]{item});
                        break;
                    }
                    notify3 = new Notifications("Inventory-Space", this.kit.getName(), this.user.getName(), 0.0, null, 0);
                    this.user.sendMessage(notify3.getMessage());
                    return false;
                }
                ++index;
                ++k;
            }
            this.user.getInventory().setContents(tempInv.getContents());
            if (tempArm.getItem(0) != null) {
                this.user.getInventory().setBoots(tempArm.getItem(0));
            }
            if (tempArm.getItem(1) != null) {
                this.user.getInventory().setLeggings(tempArm.getItem(1));
            }
            if (tempArm.getItem(2) != null) {
                this.user.getInventory().setChestplate(tempArm.getItem(2));
            }
            if (tempArm.getItem(3) != null) {
                this.user.getInventory().setHelmet(tempArm.getItem(3));
            }
            SQLPlayers.setObtained(this.userUUID, this.kit.getName(), "TRUE");
            return true;
        }
        return false;
    }
}

