// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.event.inventory.PrepareItemCraftEvent;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.entity.Entity;
import org.bukkit.command.CommandSender;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.Location;
import think.rpgitems.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.metadata.Metadatable;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import java.util.Collection;
import think.rpgitems.item.RPGItem;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import java.io.Closeable;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import org.bukkit.event.Listener;

public class bF implements Listener
{
    public static V a;
    public static W b;
    public static HashMap<String, Set<Integer>> c;
    private final Random d;
    
    public bF() {
        this.d = new Random();
    }
    
    protected static void a() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        bF.a = new V();
        bF.b = new W();
        bF.c = new HashMap<String, Set<Integer>>();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void a(final EntityDeathEvent e) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final String type = e.getEntity().getType().toString();
        if (bF.c.containsKey(type)) {
            final Set<Integer> items = bF.c.get(type);
            final Iterator<Integer> it = items.iterator();
            while (it.hasNext()) {
                final int id = it.next();
                final RPGItem item = by.a(id);
                if (item == null) {
                    it.remove();
                }
                else {
                    final double chance = item.l.b(type);
                    if (this.d.nextDouble() >= chance / 100.0) {
                        continue;
                    }
                    e.getDrops().add(item.toItemStack());
                }
            }
        }
        if (e instanceof PlayerDeathEvent) {
            final PlayerDeathEvent ev = (PlayerDeathEvent)e;
            ItemStack[] items2 = ev.getEntity().getInventory().getContents();
            for (int i = 0; i < items2.length; ++i) {
                final RPGItem rItem = by.a(items2[i]);
                if (rItem != null && rItem.m > 0 && this.d.nextInt(rItem.m) == 0) {
                    e.getDrops().remove(items2[i]);
                    items2[i] = null;
                }
            }
            ev.getEntity().getInventory().setContents(items2);
            items2 = ev.getEntity().getInventory().getArmorContents();
            for (int i = 0; i < items2.length; ++i) {
                final RPGItem rItem = by.a(items2[i]);
                if (rItem != null && rItem.m > 0 && this.d.nextInt(rItem.m) == 0) {
                    e.getDrops().remove(items2[i]);
                    items2[i] = null;
                }
            }
        }
        final EntityDamageEvent ede = e.getEntity().getLastDamageCause();
        LivingEntity damager = null;
        if (ede != null) {
            final Object oo = bg.a(ede);
            if (oo != null && oo instanceof LivingEntity && oo instanceof LivingEntity) {
                damager = (LivingEntity)oo;
            }
        }
        if (e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            final LivingEntity eDamager = damager;
            bz.a(bg.a(player), player, new bo<RPGItem>() {
                @Override
                public void a(final RPGItem param) {
                    final Collection<bI> powers = param.a(aM.KILLED);
                    for (final bI pow : powers) {
                        pow.a(player, eDamager);
                    }
                }
            });
        }
        if (damager != null && damager instanceof Player) {
            final Player player = (Player)damager;
            bz.a(bg.a(player), player, new bo<RPGItem>() {
                @Override
                public void a(final RPGItem param) {
                    final Collection<bI> powers = param.a(aM.KILL);
                    for (final bI pow : powers) {
                        pow.b(player, e.getEntity());
                    }
                }
            });
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void a(final PotionSplashEvent e) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        MetadataValue object = aR.c((Metadatable)e.getPotion(), aR.a.POTION_EFFECT);
        if (object != null) {
            final PotionEffect pe = (PotionEffect)object.value();
            final Collection<LivingEntity> ces = (Collection<LivingEntity>)e.getAffectedEntities();
            for (final LivingEntity ce : ces) {
                ce.addPotionEffect(pe, true);
            }
        }
        object = aR.c((Metadatable)e.getPotion(), aR.a.POTION_PURGE_EFFECT);
        if (object != null) {
            final bd purgeObj = (bd)object.value();
            final Collection cps = bg.a(purgeObj.a());
            final Collection<LivingEntity> ces2 = (Collection<LivingEntity>)e.getAffectedEntities();
            for (final LivingEntity ce2 : ces2) {
                final Collection<PotionEffect> pes = (Collection<PotionEffect>)ce2.getActivePotionEffects();
                for (final PotionEffect pe2 : pes) {
                    if (cps.contains(pe2.getType())) {
                        ce2.removePotionEffect(pe2.getType());
                    }
                }
                aR.a((Metadatable)ce2, aR.a.POTION_PURGE_EFFECT, purgeObj);
            }
        }
        else {
            final Collection<LivingEntity> ces3 = (Collection<LivingEntity>)e.getAffectedEntities();
            for (final LivingEntity ce3 : ces3) {
                object = aR.c((Metadatable)ce3, aR.a.POTION_PURGE_EFFECT);
                if (object == null) {
                    continue;
                }
                final bd purgeObj2 = (bd)object.value();
                final Collection cps2 = bg.a(purgeObj2.a());
                final Collection<PotionEffect> pes = (Collection<PotionEffect>)ce3.getActivePotionEffects();
                for (final PotionEffect pe2 : pes) {
                    if (cps2.contains(pe2.getType())) {
                        ce3.removePotionEffect(pe2.getType());
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void a(final ProjectileHitEvent e) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final Projectile entity = e.getEntity();
        if (bF.a.g(entity.getEntityId())) {
            entity.remove();
            bF.a.b_(entity.getEntityId());
        }
        else if (bF.b.g(entity.getEntityId())) {
            final RPGItem item = by.a(bF.b.e_(entity.getEntityId()));
            if (item == null) {
                this.a(entity);
                return;
            }
            final Object shooter = bg.a(entity);
            if (shooter != null && shooter instanceof Player) {
                item.a((Player)shooter, entity);
            }
            if (aR.b((Metadatable)entity, aR.a.RPGITEM_BOUNCE_PROJECTILE)) {
                cg.a(entity);
            }
        }
    }
    
    private void a(final Projectile entity) {
        new BukkitRunnable() {
            public void run() {
                bF.b.f_(entity.getEntityId());
            }
        }.runTask((org.bukkit.plugin.Plugin)Plugin.c);
    }
    
    private static boolean a(final Player player, final Location loc, final RPGItem rItem) {
        final Collection<cv> ipss = cD.e();
        for (final cv ips : ipss) {
            if (!ips.c(player, loc) && !rItem.a(ips.f())) {
                return true;
            }
        }
        return false;
    }
    
    @EventHandler
    public void a(final ProjectileLaunchEvent e) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final Object shooter = bg.a(e.getEntity());
        if (shooter != null && shooter instanceof Player) {
            final Player player = (Player)shooter;
            final ItemStack item = player.getItemInHand();
            final RPGItem rItem = by.a(item);
            if (rItem == null) {
                return;
            }
            if (!rItem.b(player)) {
                e.setCancelled(true);
                return;
            }
            final aS meta = RPGItem.b(item);
            if (rItem.q() != -1) {
                int durability = meta.c(0) ? ((X<Number>)meta).j_(0).intValue() : rItem.q();
                if (--durability <= 0) {
                    player.setItemInHand((ItemStack)null);
                }
                ((X<Integer>)meta).a(0, Integer.valueOf(durability));
            }
            RPGItem.a(item, aO.a(player), meta);
            player.updateInventory();
            bF.b.a_(e.getEntity().getEntityId(), rItem.getID());
        }
    }
    
    @EventHandler
    public void a(final PlayerInteractEvent e) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || (e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.isCancelled())) {
            final ItemStack item = player.getItemInHand();
            if (item.getType() == Material.BOW) {
                return;
            }
            final RPGItem rItem = by.a(item);
            if (rItem == null) {
                return;
            }
            if (item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION || item.getType() == Material.ENDER_PEARL) {
                e.setCancelled(true);
            }
            if (!rItem.b(player)) {
                e.setCancelled(true);
                return;
            }
            if (!rItem.a(player)) {
                return;
            }
            int success = rItem.a(player, e);
            success += rItem.b(player, e);
            switch (bF$4.a[e.getAction().ordinal()]) {
                case 1: {
                    success += rItem.d(player, e);
                    break;
                }
                case 2: {
                    success += rItem.f(player, e);
                    break;
                }
            }
            final aS meta = RPGItem.b(item);
            final int reduceMode = bg.a(Plugin.d.getInt("rightClickPowerReduceDurabilityMode", 1), 0, 2);
            if (success > 0 && reduceMode > 0 && rItem.q() != -1) {
                int durability = meta.c(0) ? ((X<Number>)meta).j_(0).intValue() : rItem.q();
                if (durability > rItem.q()) {
                    durability = rItem.q();
                }
                durability -= ((reduceMode == 1) ? 1 : success);
                int amount = item.getAmount() - 1;
                if (durability <= 0) {
                    if (amount < 0) {
                        amount = 0;
                    }
                    item.setAmount(amount);
                    if (amount == 0) {
                        item.setTypeId(0);
                    }
                }
                else {
                    ((X<Integer>)meta).a(0, Integer.valueOf(durability));
                }
                if (item.getTypeId() > 0 && item.getAmount() > 0) {
                    RPGItem.a(item, aO.a(player), meta);
                }
                else {
                    player.setItemInHand((ItemStack)null);
                }
                player.updateInventory();
            }
        }
        else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            final ItemStack item = player.getItemInHand();
            if (item.getType() == Material.BOW || item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION) {
                return;
            }
            final RPGItem rItem = by.a(item);
            if (rItem == null) {
                return;
            }
            if (!rItem.b(player)) {
                e.setCancelled(true);
                return;
            }
            if (!rItem.a(player)) {
                return;
            }
            int success = rItem.a(player, e);
            success += rItem.c(player, e);
            switch (bF$4.a[e.getAction().ordinal()]) {
                case 3: {
                    success += rItem.e(player, e);
                    break;
                }
                case 4: {
                    success += rItem.g(player, e);
                    break;
                }
            }
            RPGItem.a(item, player);
        }
    }
    
    private void a(final RPGItem rItem, final aY damage) {
        damage.a((rItem.a ? 0.0 : damage.a()) + ((rItem.f() != rItem.g()) ? (rItem.f() + this.d.nextInt(rItem.g() - rItem.f())) : rItem.f()));
    }
    
    private void a(final EntityDamageByEntityEvent e, final aY damage) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final Player player = (Player)e.getDamager();
        final ItemStack item = player.getItemInHand();
        if (item.getType() == Material.BOW || item.getType() == Material.SNOW_BALL || item.getType() == Material.EGG || item.getType() == Material.POTION) {
            return;
        }
        final RPGItem rItem = by.a(item);
        if (rItem == null) {
            return;
        }
        if (a(player, player.getLocation(), rItem)) {
            return;
        }
        if (!rItem.a((CommandSender)player, true)) {
            e.setCancelled(true);
        }
        this.a(rItem, damage);
        if (e.getEntity() instanceof LivingEntity) {
            final LivingEntity le = (LivingEntity)e.getEntity();
            rItem.b(player, le, damage);
        }
        final aS meta = RPGItem.b(item);
        if (rItem.q() != -1) {
            int durability = meta.c(0) ? ((X<Number>)meta).j_(0).intValue() : rItem.q();
            if (--durability <= 0) {
                player.setItemInHand((ItemStack)null);
            }
            ((X<Integer>)meta).a(0, Integer.valueOf(durability));
        }
        RPGItem.a(item, player, meta);
        player.updateInventory();
    }
    
    private void b(final EntityDamageByEntityEvent e, final aY damage) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final Projectile entity = (Projectile)e.getDamager();
        if (bF.b.g(entity.getEntityId())) {
            final RPGItem rItem = by.a(bF.b.e_(entity.getEntityId()));
            if (rItem == null) {
                return;
            }
            this.a(rItem, damage);
            if (e.getEntity() instanceof LivingEntity) {
                final LivingEntity le = (LivingEntity)e.getEntity();
                final Object shooter = bg.a(entity);
                if (shooter != null && shooter instanceof Player) {
                    rItem.b((Player)shooter, le, damage);
                    rItem.a((Player)shooter, (Entity)le, entity);
                }
            }
        }
    }
    
    private void c(final EntityDamageByEntityEvent e, final aY damage) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final Player player = (Player)e.getEntity();
        if (e.isCancelled()) {
            return;
        }
        final String locale = aO.a(player);
        final ItemStack[] armour = player.getInventory().getArmorContents();
        final List<ItemStack> returnBackpacks = new ArrayList<ItemStack>();
        final List<ItemStack> availableItems = new ArrayList<ItemStack>();
        final boolean returnIfNoPerm = Plugin.c.c().getBoolean("returnIfNoPerm", true);
        final Entity damagerEntity = bg.a((EntityDamageEvent)e);
        LivingEntity damager = null;
        if (damagerEntity instanceof LivingEntity) {
            damager = (LivingEntity)damagerEntity;
        }
        for (int i = 0; i < armour.length; ++i) {
            final ItemStack pArmour = armour[i];
            final RPGItem rItem = by.a(pArmour);
            if (rItem != null) {
                if (!a(player, player.getLocation(), rItem)) {
                    if (!rItem.a((CommandSender)player, true) || player.getLevel() < rItem.c) {
                        if (returnIfNoPerm) {
                            armour[i] = null;
                            returnBackpacks.add(pArmour);
                        }
                    }
                    else {
                        availableItems.add(pArmour);
                        final aS meta = RPGItem.b(pArmour);
                        if (rItem.q() != -1) {
                            int durability = meta.c(0) ? ((X<Number>)meta).j_(0).intValue() : rItem.q();
                            if (--durability <= 0) {
                                armour[i] = null;
                            }
                            ((X<Integer>)meta).a(0, Integer.valueOf(durability));
                        }
                        RPGItem.a(pArmour, locale, meta);
                    }
                }
            }
        }
        player.getInventory().setArmorContents(armour);
        bg.a(player, returnBackpacks);
        final bA itemSetObj = bz.a(player, availableItems);
        Iterator i$ = itemSetObj.e.iterator();
        while (i$.hasNext()) {
            final RPGItem rItem = i$.next();
            if (rItem.l() > 0) {
                damage.b(rItem.l());
            }
            if (rItem.k() > 0) {
                damage.b(Math.round(damage.a() * (rItem.k() / 100.0)));
            }
        }
        i$ = itemSetObj.e.iterator();
        while (i$.hasNext()) {
            final RPGItem rItem = i$.next();
            rItem.a(player, damager, damage);
        }
        player.updateInventory();
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void a(final EntityDamageEvent e) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (e instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent)e;
            final aY damage = new aY(ee.getDamage());
            final MetadataValue metadataValue = aR.c((Metadatable)ee.getDamager(), aR.a.RPGITEM_DAMAGE);
            if (metadataValue != null) {
                damage.c(metadataValue.asDouble());
            }
            if (ee.getDamager() instanceof Player) {
                this.a(ee, damage);
            }
            else if (ee.getDamager() instanceof Projectile) {
                this.b(ee, damage);
            }
            if (ee.getEntity() instanceof Player) {
                this.c(ee, damage);
            }
            if (damage.a() < 0.0) {
                damage.a(0.0);
            }
            ee.setDamage(damage.a());
        }
        else if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            final MetadataValue obj = aR.c((Metadatable)e.getEntity(), aR.a.POWER_THROW);
            if (obj != null) {
                final bx ih = (bx)obj.value();
                final RPGItem rItem = ih.b();
                final aY damage2 = new aY();
                this.a(rItem, damage2);
                if (damage2.a() < 0.0) {
                    damage2.a(0.0);
                }
                e.setDamage(damage2.a());
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void a(final PrepareItemCraftEvent e) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (by.a(e.getInventory().getResult()) != null && this.d.nextInt(by.a(e.getInventory().getResult()).i) != 0) {
            final ItemStack baseitem = new ItemStack(e.getInventory().getResult().getType());
            e.getInventory().setResult(baseitem);
        }
    }
}
