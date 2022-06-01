/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.PrepareItemCraftEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.CraftingInventory
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package info.TrenTech.EasyKits.Events;

import info.TrenTech.EasyKits.Book;
import info.TrenTech.EasyKits.EasyKits;
import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Kit.KitUser;
import info.TrenTech.EasyKits.SQL.SQLPlayers;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MainListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerLoginEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        EasyKits.players.put(player.getUniqueId(), player.getName());
        if (!SQLPlayers.tableExist(player.getUniqueId().toString())) {
            SQLPlayers.createTable(player.getUniqueId().toString());
        }
        if (player.hasPlayedBefore()) {
            return;
        }
        String joinKit = Utils.getConfig().getString("Config.New-Player-Kit");
        Kit kit = new Kit(joinKit);
        if (!kit.exists()) {
            return;
        }
        KitUser kitUser = new KitUser(player, kit);
        try {
            kitUser.applyKit();
            Notifications notify = new Notifications("New-Player-Kit", null, null, 0.0, null, 0);
            player.sendMessage(notify.getMessage());
        }
        catch (Exception e) {
            Utils.getLogger().severe(e.getMessage());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerClickKitEvent(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().contains("\u00a70\u793c\u5305\u5185\u5bb9: ")) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player)event.getWhoClicked();
        if (event.getSlot() == 44) {
            String kitName = event.getInventory().getTitle().replace("\u00a70\u793c\u5305\u5185\u5bb9: ", "").toLowerCase();
            Kit kit = new Kit(kitName);
            if (!kit.exists()) {
                Notifications notify = new Notifications("Kit-Not-Exist", kit.getName(), player.getName(), 0.0, null, 0);
                player.sendMessage(notify.getMessage());
                return;
            }
            KitUser kitUser = new KitUser(player, kit);
            try {
                player.closeInventory();
                kitUser.applyKit();
            }
            catch (Exception e) {
                Utils.getLogger().severe(e.getMessage());
            }
        } else if (event.getSlot() == 43) {
            if (event.getInventory().getItem(event.getSlot()) == null) {
                return;
            }
            if (event.getInventory().getItem(event.getSlot()).getType() != Material.BOOK) {
                return;
            }
            if (!event.getInventory().getItem(event.getSlot()).hasItemMeta()) {
                return;
            }
            if (!event.getInventory().getItem(event.getSlot()).getItemMeta().hasDisplayName()) {
                return;
            }
            if (!event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase((Object)ChatColor.GREEN + "\u8fd4\u56de")) {
                return;
            }
            Book.pageOne(player);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerClickBookEvent(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().contains("\u00a70\u5168\u90e8\u793c\u5305")) {
            return;
        }
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getCurrentItem().getType() != Material.BOOK && event.getCurrentItem().hasItemMeta()) {
            return;
        }
        if (!event.getCurrentItem().hasItemMeta()) {
            return;
        }
        if (!event.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }
        if (!event.getCurrentItem().getItemMeta().getDisplayName().contains("\u793c\u5305: ")) {
            return;
        }
        Player player = (Player)event.getWhoClicked();
        String kitName = event.getCurrentItem().getItemMeta().getDisplayName().replace("\u793c\u5305: ", "").toLowerCase();
        event.setCancelled(true);
        Kit kit = new Kit(kitName);
        if (!kit.exists()) {
            Notifications notify = new Notifications("Kit-Not-Exist", kit.getName(), player.getName(), 0.0, null, 0);
            player.sendMessage(notify.getMessage());
            return;
        }
        Book.pageTwo(player, kit);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (player.getItemInHand().getType() != Material.BOOK || !player.getItemInHand().getItemMeta().hasDisplayName()) {
            return;
        }
        if (!player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("\u00a77[\u00a7a\u5168\u90e8\u793c\u5305\u00a77]")) {
            return;
        }
        Book.pageOne(player);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onKitEquipEvent(KitPlayerEquipEvent event) {
        Kit kit = event.getKit();
        Player player = event.getPlayer();
        KitUser kitUser = event.getKitUser();
        if (!player.hasPermission("EasyKits.kits." + kit.getName()) && !player.hasPermission("EasyKits.kits.*")) {
            Notifications notify = new Notifications("Permission-Denied", null, null, 0.0, null, 0);
            player.sendMessage(notify.getMessage());
            event.setCancelled(true);
            return;
        }
        if (!event.getPlayer().hasPermission("EasyKits.bypass.cooldown") && kitUser.getCooldownTimeRemaining() != null && kit.getCooldown() != null) {
            Notifications notify = new Notifications("Get-Cooldown", event.getKit().getName(), null, 0.0, kitUser.getCooldownTimeRemaining(), 0);
            player.sendMessage(notify.getMessage());
            event.setCancelled(true);
            return;
        }
        if (!event.getPlayer().hasPermission("EasyKits.bypass.limit") && kit.getLimit() != 0 && kitUser.getCurrentLimit() == 0) {
            Notifications notify = new Notifications("Get-Kit-Limit", kit.getName(), null, 0.0, null, kit.getLimit());
            player.sendMessage(notify.getMessage());
            event.setCancelled(true);
            return;
        }
        if (!event.getPlayer().hasPermission("EasyKits.bypass.price") && !kitUser.canAfford()) {
            Notifications notify = new Notifications("Insufficient-Funds", kit.getName(), null, kit.getPrice(), kitUser.getCooldownTimeRemaining(), 0);
            player.sendMessage(notify.getMessage());
            event.setCancelled(true);
            return;
        }
        ItemStack[] inv = kit.getInventory();
        ItemStack[] arm = kit.getArmor();
        Inventory tempInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, InventoryType.PLAYER);
        Inventory tempArm = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 9);
        tempInv.setContents(player.getInventory().getContents());
        tempArm.setContents(player.getInventory().getArmorContents());
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
                HashMap matchItem = player.getInventory().all(item);
                int size = matchItem.size();
                if (size >= item.getMaxStackSize()) {
                    int i = 10;
                    while (i <= 36) {
                        if (i == 36) {
                            Notifications notify = new Notifications("Inventory-Space", kit.getName(), player.getName(), 0.0, null, 0);
                            player.sendMessage(notify.getMessage());
                            event.setCancelled(true);
                            return;
                        }
                        if ((size -= item.getMaxStackSize()) < item.getMaxStackSize() && amount <= size) {
                            tempInv.setItem(index, item);
                        }
                        ++i;
                    }
                    Notifications notify2 = new Notifications("Inventory-Space", kit.getName(), player.getName(), 0.0, null, 0);
                    player.sendMessage(notify2.getMessage());
                    event.setCancelled(true);
                    return;
                }
                size = item.getMaxStackSize() - size;
                if (amount > size) {
                    Notifications notify2 = new Notifications("Inventory-Space", kit.getName(), player.getName(), 0.0, null, 0);
                    player.sendMessage(notify2.getMessage());
                    event.setCancelled(true);
                    return;
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
                        notify3 = new Notifications("Inventory-Space", kit.getName(), player.getName(), 0.0, null, 0);
                        player.sendMessage(notify3.getMessage());
                        event.setCancelled(true);
                        return;
                    }
                    tempInv.addItem(new ItemStack[]{item});
                }
            }
            if (index == 1 && item != null) {
                if (tempArm.getItem(1) == null) {
                    tempArm.setItem(1, item);
                } else {
                    if (tempInv.firstEmpty() <= -1) {
                        notify3 = new Notifications("Inventory-Space", kit.getName(), player.getName(), 0.0, null, 0);
                        player.sendMessage(notify3.getMessage());
                        event.setCancelled(true);
                        return;
                    }
                    tempInv.addItem(new ItemStack[]{item});
                }
            }
            if (index == 2 && item != null) {
                if (tempArm.getItem(2) == null) {
                    tempArm.setItem(2, item);
                } else {
                    if (tempInv.firstEmpty() <= -1) {
                        notify3 = new Notifications("Inventory-Space", kit.getName(), player.getName(), 0.0, null, 0);
                        player.sendMessage(notify3.getMessage());
                        event.setCancelled(true);
                        return;
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
                notify3 = new Notifications("Inventory-Space", kit.getName(), player.getName(), 0.0, null, 0);
                player.sendMessage(notify3.getMessage());
                event.setCancelled(true);
                return;
            }
            ++index;
            ++k;
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateObtained = dateFormat.format(date).toString();
        kitUser.setDateObtained(dateObtained);
        kitUser.setCurrentLimit(kitUser.getCurrentLimit() - 1);
        kitUser.chargeUser();
        Notifications notify2 = new Notifications("Kit-Obtained", kit.getName(), null, 0.0, null, 0);
        player.sendMessage(notify2.getMessage());
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onKitCooldownEvent(KitPlayerCooldownEvent event) {
        if (event.getPlayer().hasPermission("EasyKits.bypass.cooldown")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onKitLimitEvent(KitPlayerLimitEvent event) {
        if (event.getPlayer().hasPermission("EasyKits.bypass.limit")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onWithdrawMoneyEvent(WithdrawMoneyEvent event) {
        if (event.getPlayer().hasPermission("EasyKits.bypass.price")) {
            event.setCancelled(true);
            return;
        }
        if (!Utils.getPluginContainer().econSupport) {
            event.setCancelled(true);
            return;
        }
        if (event.getKit().getPrice() == 0.0) {
            event.setCancelled(true);
            return;
        }
        Notifications notify = new Notifications("Get-Price", event.getKit().getName(), null, event.getKit().getPrice(), event.getKitUser().getCooldownTimeRemaining(), 0);
        event.getPlayer().sendMessage(notify.getMessage());
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPrepareItem(PrepareItemCraftEvent event) {
        if (!event.getInventory().contains(Book.getBook("\u00a77[\u00a7a\u5168\u90e8\u793c\u5305\u00a77]"))) {
            return;
        }
        String msg = (Object)ChatColor.GREEN + "[EasyKits]: " + (Object)ChatColor.GOLD + ((HumanEntity)event.getViewers().get(0)).getName() + " is a book cheater!";
        Utils.getPluginContainer().getServer().broadcast(msg, "EasyKits.cmd.book");
        event.getInventory().setResult(new ItemStack(Material.AIR));
    }
}

