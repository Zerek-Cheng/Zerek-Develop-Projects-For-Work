/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.hanging.HangingBreakByEntityEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryOpenEvent
 *  org.bukkit.event.inventory.InventoryPickupItemEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.player.PlayerItemBreakEvent
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.plugin.PluginManager
 *  yo.aR
 */
package yo;

import java.io.Closeable;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.PluginManager;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.as_0;
import yo.bb_0;
import yo.by_0;
import yo.z_0;

public class bg_0
implements Listener {
    public static boolean a = false;
    public static z_0<String> b = new z_0();
    private final HashSet<bb_0> c = new HashSet();

    @EventHandler(ignoreCancelled=true)
    public void a(PlayerItemBreakEvent e2) {
        RPGItem rpgitem;
        ItemStack item;
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if ((rpgitem = by_0.a(item = e2.getBrokenItem())) != null) {
            item.setDurability((short)0);
            item.setAmount(item.getAmount() + 1);
        }
    }

    public void a(PlayerItemConsumeEvent e2) {
        RPGItem rpgitem;
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (by_0.a(e2.getItem()) != null && (rpgitem = by_0.a(e2.getItem())).q() > 0) {
            e2.getItem().setDurability((short)-1);
        }
    }

    @EventHandler(ignoreCancelled=true)
    private void b(InventoryClickEvent e2) {
        RPGItem rItem;
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (e2.getSlot() != -999 && e2.getInventory().getType() == InventoryType.ANVIL && e2.getSlotType() == InventoryType.SlotType.RESULT && e2.getCurrentItem() != null && e2.getCurrentItem().hasItemMeta() && (rItem = by_0.a(e2.getInventory().getItem(0))) != null) {
            ItemStack item = e2.getCurrentItem();
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(String.valueOf(rItem.b()) + rItem.getDisplay());
            item.setItemMeta(im);
            e2.setCurrentItem(item);
        }
    }

    @EventHandler
    public void a(InventoryCloseEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (b.e_(e2.getPlayer().getName())) {
            int id = b.f_(e2.getPlayer().getName());
            RPGItem item = by_0.a(id);
            if (item.k == null) {
                item.k = new ArrayList<ItemStack>();
            }
            item.k.clear();
            boolean isEmpty = true;
            int y = 0;
            while (y < 3) {
                int x = 0;
                while (x < 3) {
                    int i = x + y * 9;
                    ItemStack it = e2.getInventory().getItem(i);
                    item.k.add(it);
                    if (it != null && it.getType() != Material.AIR) {
                        isEmpty = false;
                    }
                    ++x;
                }
                ++y;
            }
            item.j = !isEmpty;
            item.a(true);
            by_0.c(Plugin.c);
            ((Player)e2.getPlayer()).sendMessage((Object)ChatColor.AQUA + "Recipe set for " + item.getName());
        } else if (a && e2.getView() instanceof bb_0) {
            this.c.remove((Object)e2.getView());
            ((bb_0)e2.getView()).a().close();
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void a(InventoryClickEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (a && e2.getView() instanceof bb_0) {
            bb_0 inv = (bb_0)e2.getView();
            InventoryClickEvent clickEvent = new InventoryClickEvent(inv.a(), e2.getSlotType(), e2.getRawSlot(), e2.isRightClick(), e2.isShiftClick());
            Bukkit.getServer().getPluginManager().callEvent((Event)clickEvent);
            if (clickEvent.isCancelled()) {
                e2.setCancelled(true);
            } else {
                switch (clickEvent.getResult().ordinal()) {
                    case 1: 
                    case 2: {
                        System.out.println("ok...");
                        System.out.println((Object)inv.a().getItem(e2.getRawSlot()));
                        inv.a().setItem(e2.getRawSlot(), clickEvent.getCursor());
                        System.out.println((Object)inv.a().getItem(e2.getRawSlot()));
                    }
                }
            }
            for (bb_0 localeInv : this.c) {
                if (localeInv == inv) continue;
                localeInv.b();
            }
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void a(InventoryOpenEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject1 = null;
            if (asdhqjefhusfer != null) {
                if (localObject1 != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject1).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (e2.getView() instanceof bb_0) {
            return;
        }
        if (e2.getInventory().getType() != InventoryType.CHEST || !a) {
            Inventory in = e2.getInventory();
            ListIterator it = in.iterator();
            String locale = ao_0.a((Player)e2.getPlayer());
            try {
                while (((Iterator)it).hasNext()) {
                    ItemStack item = (ItemStack)((Iterator)it).next();
                    if (by_0.a(item) == null) continue;
                    RPGItem.a(item, locale);
                }
            }
            catch (ArrayIndexOutOfBoundsException item) {}
        } else if (a) {
            bb_0 localeInv = new bb_0((Player)e2.getPlayer(), e2.getView());
            e2.setCancelled(true);
            e2.getPlayer().openInventory((InventoryView)localeInv);
            this.c.add(localeInv);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void a(PlayerPickupItemEvent e2) {
        int re;
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if ((re = e2.getRemaining()) < 1) {
            e2.getItem().remove();
            return;
        }
        e2.getItem().getItemStack().setAmount(re);
        ItemStack item = e2.getItem().getItemStack();
        String locale = ao_0.a(e2.getPlayer());
        if (by_0.a(item) != null) {
            RPGItem.a(item, locale);
            e2.getItem().setItemStack(item);
        }
        if (aR.b((Metadatable)e2.getItem(), (ar_0.a)ar_0.a.CANT_PICKUP)) {
            e2.setCancelled(true);
        }
    }

    @EventHandler
    public void a(InventoryPickupItemEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (aR.b((Metadatable)e2.getItem(), (ar_0.a)ar_0.a.CANT_PICKUP)) {
            e2.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void a(HangingBreakByEntityEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (e2.getRemover() != null) {
            Player player;
            if (e2.getRemover().hasMetadata(ar_0.a.RPGITEM_MAKE.getMetadataKey())) {
                e2.setCancelled(true);
            } else if (e2.getRemover() instanceof Player && by_0.a((player = (Player)e2.getRemover()).getItemInHand()) != null) {
                e2.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void a(BlockPlaceEvent e2) {
        ItemStack item;
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if ((item = e2.getItemInHand()) == null || item.getAmount() < 0 || item.getType() == Material.AIR) {
            e2.setCancelled(true);
            return;
        }
        RPGItem rItem = by_0.a(item);
        if (rItem == null) {
            return;
        }
        e2.setCancelled(true);
    }

    @EventHandler(ignoreCancelled=true)
    public void a(BlockBreakEvent e2) {
        Player player;
        ItemStack item;
        RPGItem rItem;
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        ((Throwable)localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if ((rItem = by_0.a(item = (player = e2.getPlayer()).getItemInHand())) != null) {
            as_0 meta = RPGItem.b(item);
            if (rItem.q() != -1) {
                int durability;
                int n = durability = meta.c(0) ? ((Number)meta.j_(0)).intValue() : rItem.q();
                if (--durability <= 0) {
                    player.setItemInHand(null);
                }
                meta.a(0, Integer.valueOf(durability));
            }
            RPGItem.a(item, player, meta);
            player.updateInventory();
        }
    }
}

