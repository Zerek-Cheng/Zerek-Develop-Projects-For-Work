/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.hanging.HangingBreakByEntityEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryOpenEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.inventory.PrepareItemCraftEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.CraftingInventory
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package think.rpgitems.listeners;

import gnu.trove.map.hash.TIntByteHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import think.rpgitems.Plugin;
import think.rpgitems.data.Locale;
import think.rpgitems.data.RMetadata;
import think.rpgitems.data.RPGMetadata;
import think.rpgitems.handler.Util;
import think.rpgitems.item.ItemManager;
import think.rpgitems.item.LocaleInventory;
import think.rpgitems.item.RPGItem;
import think.rpgitems.support.IPluginSupport;
import think.rpgitems.support.SupportManager;

public class Events
        implements Listener {
    public static TIntByteHashMap removeArrows;
    public static TIntIntHashMap rpgProjectiles;
    public static TObjectIntHashMap<String> recipeWindows;
    public static HashMap<String, Set<Integer>> drops;
    public static boolean useLocaleInv;
    private HashSet<LocaleInventory> localeInventories = new HashSet();
    private Random random = new Random();

    protected static void init() {
        removeArrows = new TIntByteHashMap();
        rpgProjectiles = new TIntIntHashMap();
        recipeWindows = new TObjectIntHashMap();
        drops = new HashMap();
        useLocaleInv = false;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();
        RPGItem rItem = ItemManager.toRPGItem(item);
        if (rItem != null) {
            RPGMetadata meta = RPGItem.getMetadata(item);
            if (rItem.getMaxDurability() != -1) {
                int durability;
                int n = durability = meta.containsKey(0) ? ((Number) meta.get(0)).intValue() : rItem.getMaxDurability();
                if (--durability <= 0) {
                    player.setItemInHand(null);
                }
                meta.put(0, durability);
            }
            RPGItem.updateItem(item, Locale.getPlayerLocale(player), meta);
            player.updateInventory();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent e) {
        String type = e.getEntity().getType().toString();
        Random random = new Random();
        if (drops.containsKey(type)) {
            Set<Integer> items = drops.get(type);
            Iterator<Integer> it = items.iterator();
            while (it.hasNext()) {
                int id = it.next();
                RPGItem item = ItemManager.getItemById(id);
                if (item == null) {
                    it.remove();
                    continue;
                }
                double chance = item.dropChances.get(type);
                if (random.nextDouble() >= chance / 100.0) continue;
                e.getDrops().add(item.toItemStack());
            }
        }
        if (e instanceof PlayerDeathEvent) {
            int i;
            RPGItem rItem;
            PlayerDeathEvent ev = (PlayerDeathEvent) e;
            ItemStack[] items = ev.getEntity().getInventory().getContents();
            for (i = 0; i < items.length; ++i) {
                rItem = ItemManager.toRPGItem(items[i]);
                if (rItem == null || rItem.disappearChance <= 0 || random.nextInt(rItem.disappearChance) != 0) continue;
                e.getDrops().remove((Object) items[i]);
                items[i] = null;
            }
            ev.getEntity().getInventory().setContents(items);
            items = ev.getEntity().getInventory().getArmorContents();
            for (i = 0; i < items.length; ++i) {
                rItem = ItemManager.toRPGItem(items[i]);
                if (rItem == null || rItem.disappearChance <= 0 || random.nextInt(rItem.disappearChance) != 0) continue;
                e.getDrops().remove((Object) items[i]);
                items[i] = null;
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        final Projectile entity = e.getEntity();
        if (removeArrows.contains(entity.getEntityId())) {
            entity.remove();
            removeArrows.remove(entity.getEntityId());
        } else if (rpgProjectiles.contains(entity.getEntityId())) {
            RPGItem item = ItemManager.getItemById(rpgProjectiles.get(entity.getEntityId()));
            new BukkitRunnable() {

                public void run() {
                    Events.rpgProjectiles.remove(entity.getEntityId());
                }
            }.runTask((org.bukkit.plugin.Plugin) Plugin.plugin);
            if (item == null) {
                return;
            }
            Object shooter = Util.getShooter(entity);
            if (shooter != null && shooter instanceof Player) {
                item.projectileHit((Player) shooter, entity);
            }
        }
    }

    private static boolean cantUseInPVP(Player player, Location loc, RPGItem rItem) {
        Collection<IPluginSupport> ipss = SupportManager.getRegisteredInstances();
        for (IPluginSupport ips : ipss) {
            if (ips.canPvP(player, loc) || rItem.isIgnorePlugin(ips.getIClass())) continue;
            return true;
        }
        return false;
    }

    @EventHandler
    public void onProjectileFire(ProjectileLaunchEvent e) {
        Object shooter = Util.getShooter(e.getEntity());
        if (shooter != null && shooter instanceof Player) {
            Player player = (Player) shooter;
            ItemStack item = player.getItemInHand();
            RPGItem rItem = ItemManager.toRPGItem(item);
            if (rItem == null) {
                return;
            }
            if (Events.cantUseInPVP(player, player.getLocation(), rItem)) {
                return;
            }
            if (rItem.getHasPermission() && !player.hasPermission(rItem.getPermission())) {
                e.setCancelled(true);
                player.sendMessage((Object) ChatColor.RED + String.format(Locale.get("message.error.permission", Locale.getPlayerLocale(player)), new Object[0]));
            }
            RPGMetadata meta = RPGItem.getMetadata(item);
            if (rItem.getMaxDurability() != -1) {
                int durability;
                int n = durability = meta.containsKey(0) ? ((Number) meta.get(0)).intValue() : rItem.getMaxDurability();
                if (--durability <= 0) {
                    player.setItemInHand(null);
                }
                meta.put(0, durability);
            }
            RPGItem.updateItem(item, Locale.getPlayerLocale(player), meta);
            player.updateInventory();
            rpgProjectiles.put(e.getEntity().getEntityId(), rItem.getID());
        }
    }

    @EventHandler
    public void onPlayerAction(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.isCancelled()) {
            ItemStack item = player.getItemInHand();
            if (item.getType() == Material.BOW) {
                return;
            }
            RPGItem rItem = ItemManager.toRPGItem(item);
            if (rItem == null) {
                return;
            }
            if (item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION || item.getType() == Material.ENDER_PEARL) {
                e.setCancelled(true);
            }
            if (Events.cantUseInPVP(player, player.getLocation(), rItem)) {
                return;
            }
            if (rItem.getHasPermission() && !player.hasPermission(rItem.getPermission())) {
                e.setCancelled(true);
                player.sendMessage((Object) ChatColor.RED + String.format(Locale.get("message.error.permission", Locale.getPlayerLocale(player)), new Object[0]));
            }
            int success = rItem.rightClick(player);
            RPGMetadata meta = null;
            int reduceMode = Plugin.config.getInt("rightClickPowerReduceDurabilityMode", 1);
            if (reduceMode > 2 || reduceMode < 0) {
                reduceMode = 0;
            }
            if (success > 0 && reduceMode > 0 && rItem.getMaxDurability() != -1) {
                int durability;
                meta = RPGItem.getMetadata(item);
                int n = durability = meta.containsKey(0) ? ((Number) meta.get(0)).intValue() : rItem.getMaxDurability();
                if (durability > rItem.getMaxDurability()) {
                    durability = rItem.getMaxDurability();
                }
                int amount = item.getAmount() - 1;
                if ((durability -= reduceMode == 1 ? 1 : success) <= 0) {
                    if (amount < 0) {
                        amount = 0;
                    }
                    item.setAmount(amount);
                    if (amount == 0) {
                        item.setTypeId(0);
                    }
                } else {
                    meta.put(0, durability);
                }
            }
            if (item.getTypeId() > 0 && item.getAmount() > 0) {
                if (meta != null) {
                    RPGItem.updateItem(item, Locale.getPlayerLocale(player), meta);
                } else {
                    RPGItem.updateItem(item, Locale.getPlayerLocale(player));
                }
            } else {
                player.setItemInHand(null);
                player.updateInventory();
            }
        } else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemStack item = player.getItemInHand();
            if (item.getType() == Material.BOW || item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION) {
                return;
            }
            RPGItem rItem = ItemManager.toRPGItem(item);
            if (rItem == null) {
                return;
            }
            if (Events.cantUseInPVP(player, player.getLocation(), rItem)) {
                return;
            }
            if (rItem.getHasPermission() && !player.hasPermission(rItem.getPermission())) {
                e.setCancelled(true);
                player.sendMessage((Object) ChatColor.RED + String.format(Locale.get("message.error.permission", Locale.getPlayerLocale(player)), new Object[0]));
            }
            rItem.leftClick(player);
            RPGItem.updateItem(item, Locale.getPlayerLocale(player));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        if (item == null || item.getAmount() < 0 || item.getType() == Material.AIR) {
            e.setCancelled(true);
            return;
        }
        RPGItem rItem = ItemManager.toRPGItem(item);
        if (rItem == null) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreakHanging(HangingBreakByEntityEvent e) {
        if (e.getRemover() != null) {
            Player player;
            if (e.getRemover().hasMetadata(RMetadata.MetadataKey.RPGITEM_MAKE.getMetadataKey())) {
                e.setCancelled(true);
            } else if (e.getRemover() instanceof Player && ItemManager.toRPGItem((player = (Player) e.getRemover()).getItemInHand()) != null) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerInventory in = player.getInventory();
        String locale = Locale.getPlayerLocale(player);
        for (int i = 0; i < in.getSize(); ++i) {
            ItemStack item = in.getItem(i);
            if (ItemManager.toRPGItem(item) == null) continue;
            RPGItem.updateItem(item, locale);
        }
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (ItemManager.toRPGItem(item) == null) continue;
            RPGItem.updateItem(item, locale);
        }
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();
        String locale = Locale.getPlayerLocale(e.getPlayer());
        if (ItemManager.toRPGItem(item) != null) {
            RPGItem.updateItem(item, locale);
            e.getItem().setItemStack(item);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (recipeWindows.containsKey(e.getPlayer().getName())) {
            int id = recipeWindows.remove(e.getPlayer().getName());
            RPGItem item = ItemManager.getItemById(id);
            if (item.recipe == null) {
                item.recipe = new ArrayList<ItemStack>();
            }
            item.recipe.clear();
            for (int y = 0; y < 3; ++y) {
                for (int x = 0; x < 3; ++x) {
                    int i = x + y * 9;
                    ItemStack it = e.getInventory().getItem(i);
                    item.recipe.add(it);
                }
            }
            item.hasRecipe = true;
            item.resetRecipe(true);
            ItemManager.save(Plugin.plugin);
            ((Player) e.getPlayer()).sendMessage((Object) ChatColor.AQUA + "Recipe set for " + item.getName());
        } else if (useLocaleInv && e.getView() instanceof LocaleInventory) {
            this.localeInventories.remove((Object) e.getView());
            ((LocaleInventory) e.getView()).getView().close();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        if (useLocaleInv && e.getView() instanceof LocaleInventory) {
            LocaleInventory inv = (LocaleInventory) e.getView();
            InventoryClickEvent clickEvent = new InventoryClickEvent(inv.getView(), e.getSlotType(), e.getRawSlot(), e.isRightClick(), e.isShiftClick());
            Bukkit.getServer().getPluginManager().callEvent((Event) clickEvent);
            if (clickEvent.isCancelled()) {
                e.setCancelled(true);
            } else {
                switch (clickEvent.getResult()) {
                    case DEFAULT:
                    case ALLOW: {
                        System.out.println("ok...");
                        System.out.println((Object) inv.getView().getItem(e.getRawSlot()));
                        inv.getView().setItem(e.getRawSlot(), clickEvent.getCursor());
                        System.out.println((Object) inv.getView().getItem(e.getRawSlot()));
                        break;
                    }
                }
            }
            for (LocaleInventory localeInv : this.localeInventories) {
                if (localeInv == inv) continue;
                localeInv.reload();
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getView() instanceof LocaleInventory) {
            return;
        }
        if (e.getInventory().getType() != InventoryType.CHEST || !useLocaleInv) {
            Inventory in = e.getInventory();
            ListIterator it = in.iterator();
            String locale = Locale.getPlayerLocale((Player) e.getPlayer());
            try {
                while (it.hasNext()) {
                    ItemStack item = (ItemStack) it.next();
                    if (ItemManager.toRPGItem(item) == null) continue;
                    RPGItem.updateItem(item, locale);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        } else if (useLocaleInv) {
            LocaleInventory localeInv = new LocaleInventory((Player) e.getPlayer(), e.getView());
            e.setCancelled(true);
            e.getPlayer().openInventory((InventoryView) localeInv);
            this.localeInventories.add(localeInv);
        }
    }

    private double playerDamager(EntityDamageByEntityEvent e, double damage) {
        Player player = (Player) e.getDamager();
        ItemStack item = player.getItemInHand();
        if (item.getType() == Material.BOW || item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION) {
            return damage;
        }
        RPGItem rItem = ItemManager.toRPGItem(item);
        if (rItem == null) {
            return damage;
        }
        if (Events.cantUseInPVP(player, player.getLocation(), rItem)) {
            return damage;
        }
        if (rItem.getHasPermission() && !player.hasPermission(rItem.getPermission())) {
            damage = 0.0;
            e.setCancelled(true);
            player.sendMessage((Object) ChatColor.RED + String.format(Locale.get("message.error.permission", Locale.getPlayerLocale(player)), new Object[0]));
        }
        damage = rItem.getDamageMin() != rItem.getDamageMax() ?
                (double) (rItem.getDamageMin() + this.random.nextInt(rItem.getDamageMax() - rItem.getDamageMin())) :
                (double) rItem.getDamageMin();
        damage = damage + damage * this.getPowerDamage(player);
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) e.getEntity();
            rItem.hit(player, le, e.getDamage());
        }
        RPGMetadata meta = RPGItem.getMetadata(item);
        if (rItem.getMaxDurability() != -1) {
            int durability;
            int n = durability = meta.containsKey(0) ? ((Number) meta.get(0)).intValue() : rItem.getMaxDurability();
            if (--durability <= 0) {
                player.setItemInHand(null);
            }
            meta.put(0, durability);
        }
        RPGItem.updateItem(item, Locale.getPlayerLocale(player), meta);
        player.updateInventory();
        return damage;
    }

    private double projectileDamager(EntityDamageByEntityEvent e, double damage) {
        Projectile entity = (Projectile) e.getDamager();
        if (rpgProjectiles.contains(entity.getEntityId())) {
            RPGItem rItem = ItemManager.getItemById(rpgProjectiles.get(entity.getEntityId()));
            if (rItem == null) {
                return damage;
            }
            double d = damage = rItem.getDamageMin() != rItem.getDamageMax() ? (double) (rItem.getDamageMin() + this.random.nextInt(rItem.getDamageMax() - rItem.getDamageMin())) : (double) rItem.getDamageMin();
            if (e.getEntity() instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) e.getEntity();
                Object shooter = Util.getShooter(entity);
                if (shooter != null && shooter instanceof Player) {
                    rItem.hit((Player) shooter, le, e.getDamage());
                }
            }
        }
        return damage;
    }

    private double playerHit(EntityDamageByEntityEvent e, double damage) {
        Player player = (Player) e.getEntity();
        if (e.isCancelled()) {
            return damage;
        }
        String locale = Locale.getPlayerLocale(player);
        ItemStack[] armour = player.getInventory().getArmorContents();
        ArrayList<ItemStack> returnBackpacks = new ArrayList<ItemStack>();
        boolean returnIfNoPerm = Plugin.plugin.getConfig().getBoolean("returnIfNoPerm", true);
        for (int i = 0; i < armour.length; ++i) {
            ItemStack pArmour = armour[i];
            RPGItem rItem = ItemManager.toRPGItem(pArmour);
            if (rItem == null || Events.cantUseInPVP(player, player.getLocation(), rItem)) continue;
            if (rItem.getHasPermission() && !player.hasPermission(rItem.getPermission())) {
                if (returnIfNoPerm) {
                    armour[i] = null;
                    returnBackpacks.add(pArmour);
                }
                player.sendMessage((Object) ChatColor.RED + String.format(Locale.get("message.error.permission", Locale.getPlayerLocale(player)), new Object[0]));
                continue;
            }
            if (rItem.getArmour() > 0) {
                damage -= (double) Math.round(damage * ((double) rItem.getArmour() / 100.0));
            }
            RPGMetadata meta = RPGItem.getMetadata(pArmour);
            if (rItem.getMaxDurability() != -1) {
                int durability;
                int n = durability = meta.containsKey(0) ? ((Number) meta.get(0)).intValue() : rItem.getMaxDurability();
                if (--durability <= 0) {
                    armour[i] = null;
                }
                meta.put(0, durability);
            }
            RPGItem.updateItem(pArmour, locale, meta);
        }
        player.getInventory().setArmorContents(armour);
        Util.giveItems(player, returnBackpacks);
        player.updateInventory();
        return damage;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        double damage = e.getDamage();
        if (e.getDamager() instanceof Player) {
            damage = this.playerDamager(e, damage);
        } else if (e.getDamager() instanceof Projectile) {
            damage = this.projectileDamager(e, damage);
        }
        if (e.getEntity() instanceof Player) {
            damage = this.playerHit(e, damage);
        }
        e.setDamage(damage);
    }

    public double getPowerDamage(LivingEntity p) {
        Collection<PotionEffect> es = p.getActivePotionEffects();
        for (PotionEffect pe : es) {
            if (!pe.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                continue;
            }
            int level = pe.getAmplifier();
            return (level + 1) * 1.3;
        }
        return 0;
    }

    @EventHandler(ignoreCancelled = true)
    private void onItemRename(InventoryClickEvent e) {
        RPGItem rItem;
        if (e.getSlot() != -999 && e.getInventory().getType() == InventoryType.ANVIL && e.getSlotType() == InventoryType.SlotType.RESULT && e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && (rItem = ItemManager.toRPGItem(e.getInventory().getItem(0))) != null) {
            ItemStack item = e.getCurrentItem();
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(rItem.getMCEncodedID() + rItem.getDisplay());
            item.setItemMeta(im);
            e.setCurrentItem(item);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onItemCraft(PrepareItemCraftEvent e) {
        Random random;
        if (ItemManager.toRPGItem(e.getInventory().getResult()) != null && (random = new Random()).nextInt(ItemManager.toRPGItem((ItemStack) e.getInventory().getResult()).recipechance) != 0) {
            ItemStack baseitem = new ItemStack(e.getInventory().getResult().getType());
            e.getInventory().setResult(baseitem);
        }
    }

    public void onItemDamage(PlayerItemConsumeEvent e) {
        RPGItem rpgitem;
        if (ItemManager.toRPGItem(e.getItem()) != null && (rpgitem = ItemManager.toRPGItem(e.getItem())).getMaxDurability() > 0) {
            e.getItem().setDurability((short) -1);
        }
    }

}

