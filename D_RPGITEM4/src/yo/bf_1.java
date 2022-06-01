/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.entity.PotionSplashEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.inventory.PrepareItemCraftEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.CraftingInventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  yo.aR
 */
package yo;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.aR;
import yo.am_0;
import yo.ao_0;
import yo.aq_0;
import yo.ar_0;
import yo.as_0;
import yo.ay_1;
import yo.ba_0;
import yo.bc_0;
import yo.bd_1;
import yo.bg_1;
import yo.bi_1;
import yo.bo_0;
import yo.bx_0;
import yo.by_0;
import yo.bz_1;
import yo.cd_0;
import yo.cg_1;
import yo.cv;
import yo.v_1;
import yo.w_0;
import yo.y_0;

public class bf_1
implements Listener {
    public static v_1 a;
    public static w_0 b;
    public static HashMap<String, Set<Integer>> c;
    private final Random d = new Random();

    protected static void a() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        a = new v_1();
        b = new w_0();
        c = new HashMap();
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void a(final EntityDeathEvent e2) {
        Entity oo;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        String type = e2.getEntity().getType().toString();
        if (c.containsKey(type)) {
            Set<Integer> items = c.get(type);
            Iterator<Integer> it = items.iterator();
            while (it.hasNext()) {
                int id = it.next();
                RPGItem item = by_0.a(id);
                if (item == null) {
                    it.remove();
                    continue;
                }
                double chance = item.l.b(type);
                if (this.d.nextDouble() >= chance / 100.0) continue;
                e2.getDrops().add(item.toItemStack());
            }
        }
        if (e2 instanceof PlayerDeathEvent) {
            int i;
            RPGItem rItem;
            PlayerDeathEvent ev = (PlayerDeathEvent)e2;
            ItemStack[] items = ev.getEntity().getInventory().getContents();
            for (i = 0; i < items.length; ++i) {
                rItem = by_0.a(items[i]);
                if (rItem == null || rItem.m <= 0 || this.d.nextInt(rItem.m) != 0) continue;
                e2.getDrops().remove((Object)items[i]);
                items[i] = null;
            }
            ev.getEntity().getInventory().setContents(items);
            items = ev.getEntity().getInventory().getArmorContents();
            for (i = 0; i < items.length; ++i) {
                rItem = by_0.a(items[i]);
                if (rItem == null || rItem.m <= 0 || this.d.nextInt(rItem.m) != 0) continue;
                e2.getDrops().remove((Object)items[i]);
                items[i] = null;
            }
        }
        EntityDamageEvent ede = e2.getEntity().getLastDamageCause();
        LivingEntity damager = null;
        if (ede != null && (oo = bg_1.a(ede)) != null && oo instanceof LivingEntity && oo instanceof LivingEntity) {
            damager = (LivingEntity)oo;
        }
        if (e2.getEntity() instanceof Player) {
            final Player player = (Player)e2.getEntity();
            final LivingEntity eDamager = damager;
            bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem param) {
                    ArrayList<bi_1> powers = param.a(am_0.KILLED);
                    for (bi_1 pow : powers) {
                        pow.a(player, eDamager);
                    }
                }
            });
        }
        if (damager != null && damager instanceof Player) {
            final Player player = (Player)damager;
            bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem param) {
                    ArrayList<bi_1> powers = param.a(am_0.KILL);
                    for (bi_1 pow : powers) {
                        pow.b(player, e2.getEntity());
                    }
                }
            });
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void a(PotionSplashEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        MetadataValue object = aR.c((Metadatable)e2.getPotion(), (ar_0.a)ar_0.a.POTION_EFFECT);
        if (object != null) {
            PotionEffect pe = (PotionEffect)object.value();
            Collection ces = e2.getAffectedEntities();
            for (LivingEntity ce : ces) {
                ce.addPotionEffect(pe, true);
            }
        }
        if ((object = aR.c((Metadatable)e2.getPotion(), (ar_0.a)ar_0.a.POTION_PURGE_EFFECT)) != null) {
            bd_1 purgeObj = (bd_1)object.value();
            bc_0 cps = bg_1.a(purgeObj.a());
            Collection ces = e2.getAffectedEntities();
            for (LivingEntity ce : ces) {
                Collection pes = ce.getActivePotionEffects();
                for (PotionEffect pe : pes) {
                    if (!cps.contains((Object)pe.getType())) continue;
                    ce.removePotionEffect(pe.getType());
                }
                aR.a((Metadatable)ce, (ar_0.a)ar_0.a.POTION_PURGE_EFFECT, (Object)purgeObj);
            }
        } else {
            Collection ces = e2.getAffectedEntities();
            for (LivingEntity ce : ces) {
                object = aR.c((Metadatable)ce, (ar_0.a)ar_0.a.POTION_PURGE_EFFECT);
                if (object == null) continue;
                bd_1 purgeObj = (bd_1)object.value();
                bc_0 cps = bg_1.a(purgeObj.a());
                Collection pes = ce.getActivePotionEffects();
                for (PotionEffect pe : pes) {
                    if (!cps.contains((Object)pe.getType())) continue;
                    ce.removePotionEffect(pe.getType());
                }
            }
        }
    }

    @EventHandler
    public void a(ProjectileHitEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Projectile entity = e2.getEntity();
        if (a.g(entity.getEntityId())) {
            entity.remove();
            a.b_(entity.getEntityId());
        } else if (b.g(entity.getEntityId())) {
            RPGItem item = by_0.a(b.e_(entity.getEntityId()));
            if (item == null) {
                this.a(entity);
                return;
            }
            Object shooter = bg_1.a(entity);
            if (shooter != null && shooter instanceof Player) {
                item.a((Player)shooter, entity);
            }
            if (aR.b((Metadatable)entity, (ar_0.a)ar_0.a.RPGITEM_BOUNCE_PROJECTILE)) {
                cg_1.a(entity);
            }
        }
    }

    private void a(final Projectile entity) {
        new BukkitRunnable(){

            public void run() {
                bf_1.b.f_(entity.getEntityId());
            }
        }.runTask((org.bukkit.plugin.Plugin)Plugin.c);
    }

    private static boolean a(Player player, Location loc, RPGItem rItem) {
        Collection<cv> ipss = cd_0.e();
        for (cv ips : ipss) {
            if (ips.c(player, loc) || rItem.a(ips.f())) continue;
            return true;
        }
        return false;
    }

    @EventHandler
    public void a(ProjectileLaunchEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Object shooter = bg_1.a(e2.getEntity());
        if (shooter != null && shooter instanceof Player) {
            Player player = (Player)shooter;
            ItemStack item = player.getItemInHand();
            RPGItem rItem = by_0.a(item);
            if (rItem == null) {
                return;
            }
            if (!rItem.b(player)) {
                e2.setCancelled(true);
                return;
            }
            as_0 meta = RPGItem.b(item);
            if (rItem.q() != -1) {
                int durability;
                int n = durability = meta.c(0) ? ((Number)meta.j_(0)).intValue() : rItem.q();
                if (--durability <= 0) {
                    player.setItemInHand(null);
                }
                meta.a(0, Integer.valueOf(durability));
            }
            RPGItem.a(item, ao_0.a(player), meta);
            player.updateInventory();
            b.a_(e2.getEntity().getEntityId(), rItem.getID());
        }
    }

    @EventHandler
    public void a(PlayerInteractEvent e2) {
        ItemStack item;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Player player = e2.getPlayer();
        if (e2.getAction() == Action.RIGHT_CLICK_AIR || e2.getAction() == Action.RIGHT_CLICK_BLOCK && !e2.isCancelled()) {
            item = player.getItemInHand();
            if (item.getType() == Material.BOW) {
                return;
            }
            RPGItem rItem = by_0.a(item);
            if (rItem == null) {
                return;
            }
            if (item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION || item.getType() == Material.ENDER_PEARL) {
                e2.setCancelled(true);
            }
            if (!rItem.b(player)) {
                e2.setCancelled(true);
                return;
            }
            if (!rItem.a(player)) {
                return;
            }
            int success = rItem.a(player, e2);
            success += rItem.b(player, e2);
            switch (e2.getAction()) {
                case RIGHT_CLICK_AIR: {
                    success += rItem.d(player, e2);
                    break;
                }
                case RIGHT_CLICK_BLOCK: {
                    success += rItem.f(player, e2);
                }
            }
            as_0 meta = RPGItem.b(item);
            int reduceMode = bg_1.a(Plugin.d.getInt("rightClickPowerReduceDurabilityMode", 1), 0, 2);
            if (success > 0 && reduceMode > 0 && rItem.q() != -1) {
                int durability;
                int n = durability = meta.c(0) ? ((Number)meta.j_(0)).intValue() : rItem.q();
                if (durability > rItem.q()) {
                    durability = rItem.q();
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
                    meta.a(0, Integer.valueOf(durability));
                }
                if (item.getTypeId() > 0 && item.getAmount() > 0) {
                    RPGItem.a(item, ao_0.a(player), meta);
                } else {
                    player.setItemInHand(null);
                }
                player.updateInventory();
            }
        } else if (e2.getAction() == Action.LEFT_CLICK_AIR || e2.getAction() == Action.LEFT_CLICK_BLOCK) {
            item = player.getItemInHand();
            if (item.getType() == Material.BOW || item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION) {
                return;
            }
            RPGItem rItem = by_0.a(item);
            if (rItem == null) {
                return;
            }
            if (!rItem.b(player)) {
                e2.setCancelled(true);
                return;
            }
            if (!rItem.a(player)) {
                return;
            }
            int success = rItem.a(player, e2);
            success += rItem.c(player, e2);
            switch (e2.getAction()) {
                case LEFT_CLICK_AIR: {
                    success += rItem.e(player, e2);
                    break;
                }
                case LEFT_CLICK_BLOCK: {
                    success += rItem.g(player, e2);
                }
            }
            RPGItem.a(item, player);
        }
    }

    private void a(RPGItem rItem, ay_1 damage) {
        damage.a((rItem.a ? 0.0 : damage.a()) + (double)(rItem.f() != rItem.g() ? rItem.f() + this.d.nextInt(rItem.g() - rItem.f()) : rItem.f()));
    }

    private void a(EntityDamageByEntityEvent e2, ay_1 damage) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Player player = (Player)e2.getDamager();
        ItemStack item = player.getItemInHand();
        if (item.getType() == Material.BOW || item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION) {
            return;
        }
        RPGItem rItem = by_0.a(item);
        if (rItem == null) {
            return;
        }
        if (bf_1.a(player, player.getLocation(), rItem)) {
            return;
        }
        if (!rItem.a((CommandSender)player, true)) {
            e2.setCancelled(true);
        }
        this.a(rItem, damage);
        if (e2.getEntity() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity)e2.getEntity();
            rItem.b(player, le, damage);
        }
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

    private void b(EntityDamageByEntityEvent e2, ay_1 damage) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Projectile entity = (Projectile)e2.getDamager();
        if (b.g(entity.getEntityId())) {
            RPGItem rItem = by_0.a(b.e_(entity.getEntityId()));
            if (rItem == null) {
                return;
            }
            this.a(rItem, damage);
            if (e2.getEntity() instanceof LivingEntity) {
                LivingEntity le = (LivingEntity)e2.getEntity();
                Object shooter = bg_1.a(entity);
                if (shooter != null && shooter instanceof Player) {
                    rItem.b((Player)shooter, le, damage);
                    rItem.a((Player)shooter, (Entity)le, entity);
                }
            }
        }
    }

    private void c(EntityDamageByEntityEvent e2, ay_1 damage) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Player player = (Player)e2.getEntity();
        if (e2.isCancelled()) {
            return;
        }
        String locale = ao_0.a(player);
        ItemStack[] armour = player.getInventory().getArmorContents();
        ArrayList<ItemStack> returnBackpacks = new ArrayList<ItemStack>();
        ArrayList<ItemStack> availableItems = new ArrayList<ItemStack>();
        boolean returnIfNoPerm = Plugin.c.c().getBoolean("returnIfNoPerm", true);
        Entity damagerEntity = bg_1.a((EntityDamageEvent)e2);
        LivingEntity damager = null;
        if (damagerEntity instanceof LivingEntity) {
            damager = (LivingEntity)damagerEntity;
        }
        for (int i = 0; i < armour.length; ++i) {
            ItemStack pArmour = armour[i];
            RPGItem rItem = by_0.a(pArmour);
            if (rItem == null || bf_1.a(player, player.getLocation(), rItem)) continue;
            if (!rItem.a((CommandSender)player, true) || player.getLevel() < rItem.c) {
                if (!returnIfNoPerm) continue;
                armour[i] = null;
                returnBackpacks.add(pArmour);
                continue;
            }
            availableItems.add(pArmour);
            as_0 meta = RPGItem.b(pArmour);
            if (rItem.q() != -1) {
                int durability;
                int n = durability = meta.c(0) ? ((Number)meta.j_(0)).intValue() : rItem.q();
                if (--durability <= 0) {
                    armour[i] = null;
                }
                meta.a(0, Integer.valueOf(durability));
            }
            RPGItem.a(pArmour, locale, meta);
        }
        player.getInventory().setArmorContents(armour);
        bg_1.a(player, returnBackpacks);
        ba_0 itemSetObj = bz_1.a(player, availableItems);
        for (RPGItem rItem : itemSetObj.e) {
            if (rItem.l() > 0) {
                damage.b(rItem.l());
            }
            if (rItem.k() <= 0) continue;
            damage.b(Math.round(damage.a() * ((double)rItem.k() / 100.0)));
        }
        for (RPGItem rItem : itemSetObj.e) {
            rItem.a(player, damager, damage);
        }
        player.updateInventory();
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void a(EntityDamageEvent e2) {
        MetadataValue obj;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (e2 instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent)e2;
            ay_1 damage = new ay_1(ee.getDamage());
            MetadataValue metadataValue = aR.c((Metadatable)ee.getDamager(), (ar_0.a)ar_0.a.RPGITEM_DAMAGE);
            if (metadataValue != null) {
                damage.c(metadataValue.asDouble());
            }
            if (ee.getDamager() instanceof Player) {
                this.a(ee, damage);
            } else if (ee.getDamager() instanceof Projectile) {
                this.b(ee, damage);
            }
            if (ee.getEntity() instanceof Player) {
                this.c(ee, damage);
            }
            if (damage.a() < 0.0) {
                damage.a(0.0);
            }
            ee.setDamage(damage.a());
        } else if (e2.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && (obj = aR.c((Metadatable)e2.getEntity(), (ar_0.a)ar_0.a.POWER_THROW)) != null) {
            bx_0 ih = (bx_0)obj.value();
            RPGItem rItem = ih.b();
            ay_1 damage = new ay_1();
            this.a(rItem, damage);
            if (damage.a() < 0.0) {
                damage.a(0.0);
            }
            e2.setDamage(damage.a());
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void a(PrepareItemCraftEvent e2) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (by_0.a(e2.getInventory().getResult()) != null && this.d.nextInt(by_0.a((ItemStack)e2.getInventory().getResult()).i) != 0) {
            ItemStack baseitem = new ItemStack(e2.getInventory().getResult().getType());
            e2.getInventory().setResult(baseitem);
        }
    }

}

