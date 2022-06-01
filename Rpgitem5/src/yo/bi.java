// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.Iterator;
import java.util.Collection;
import java.util.HashSet;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.TreeType;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.Random;
import think.rpgitems.item.RPGItem;
import java.util.HashMap;

public abstract class bI implements bv
{
    public static HashMap<String, Class<? extends bI>> a;
    public static Z<String> b;
    public RPGItem c;
    public aM d;
    public int e;
    public Random f;
    public long g;
    
    public bI() {
        this.e = 20;
        this.f = new Random();
        this.g = 20L;
    }
    
    public boolean y(final Player player) {
        return this.a(player, false);
    }
    
    public boolean a(final Player player, final boolean printWarn) {
        return this.b(player, printWarn) && this.z(player) && this.a();
    }
    
    public boolean a() {
        return this.e < 1 || !this.b() || this.f.nextInt(this.e) == 0;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean z(final Player player) {
        if (!this.c() || this.g < 1L) {
            return true;
        }
        aT value = aT.a(player, this.c, this.d());
        long cooldown;
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new aT(player, this.c, this.d(), cooldown);
        }
        else {
            cooldown = value.g();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.a(System.currentTimeMillis() / 50L + this.g);
            return true;
        }
        player.sendMessage(ChatColor.AQUA + String.format(aO.a("message.cooldown", aO.a(player)), (cooldown - System.currentTimeMillis() / 50L) / 20.0));
        return false;
    }
    
    public String d() {
        return this.e() + ".cooldown";
    }
    
    public int b(final Player player) {
        player.sendMessage(ChatColor.RED + String.format(aO.a("message.error.power.notsupport", aO.a(player)), this.d.getName(), this.e()));
        return 0;
    }
    
    public boolean A(final Player player) {
        return this.c.b((CommandSender)player);
    }
    
    public boolean b(final Player player, final boolean printWarn) {
        return this.c.a((CommandSender)player, printWarn);
    }
    
    @Override
    public int c(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int b(final Player player, final LivingEntity target, final aY damage) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final LivingEntity damager, final aY damage) {
        return this.b(player);
    }
    
    @Override
    public int d(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int e(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int f(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int g(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int h(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int i(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerInteractEvent event) {
        return this.b(player);
    }
    
    @Override
    public int b(final Player player, final PlayerInteractEvent event) {
        return this.b(player);
    }
    
    @Override
    public int c(final Player player, final PlayerInteractEvent event) {
        return this.b(player);
    }
    
    @Override
    public int d(final Player player, final PlayerInteractEvent event) {
        return this.b(player);
    }
    
    @Override
    public int e(final Player player, final PlayerInteractEvent event) {
        return this.b(player);
    }
    
    @Override
    public int f(final Player player, final PlayerInteractEvent event) {
        return this.b(player);
    }
    
    @Override
    public int g(final Player player, final PlayerInteractEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerInteractEntityEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final ItemStack consumeItem) {
        return this.b(player);
    }
    
    @Override
    public int b(final Player player, final LivingEntity target) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final LivingEntity killer) {
        return this.b(player);
    }
    
    @Override
    public int b(final Player player, final Block block) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final Block block) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final Projectile arrow) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final Entity target, final Projectile arrow) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerFishEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final aU weatherType) {
        return this.b(player);
    }
    
    @Override
    public int j(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int k(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final Location location, final TreeType species) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final int oldLevel, final int newLevel) {
        return this.b(player);
    }
    
    @Override
    public int b(final Player player, final Location respawnLocation) {
        return this.b(player);
    }
    
    @Override
    public int l(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int m(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int n(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int o(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int p(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int q(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int r(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int s(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int t(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int u(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int v(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int w(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int x(final Player player) {
        return this.b(player);
    }
    
    @Override
    public int c(final Player player, final Block bed) {
        return this.b(player);
    }
    
    @Override
    public int d(final Player player, final Block bed) {
        return this.b(player);
    }
    
    @Override
    public int e(final Player player, final Block bed) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerLeashEntityEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerUnleashEntityEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerEditBookEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final EntityRegainHealthEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final HorseJumpEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerBucketFillEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerBucketEmptyEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerItemBreakEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final PlayerTeleportEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final EntityCombustEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final EntityTameEvent event) {
        return this.b(player);
    }
    
    @Override
    public int a(final Player player, final EnchantItemEvent event) {
        return this.b(player);
    }
    
    public void a(final ConfigurationSection s) {
        this.d = aM.parse(s.getString("eventType"));
        if (this.d == null) {
            this.d = this.f();
        }
        if (this.b()) {
            this.e = s.getInt("chance");
        }
        if (this.c()) {
            this.g = s.getLong("cooldown");
        }
        this.c(s);
    }
    
    public void b(final ConfigurationSection s) {
        s.set("eventType", (Object)this.d.name());
        if (this.b()) {
            s.set("chance", (Object)this.e);
        }
        if (this.c()) {
            s.set("cooldown", (Object)this.g);
        }
        this.d(s);
    }
    
    abstract void c(final ConfigurationSection p0);
    
    abstract void d(final ConfigurationSection p0);
    
    public abstract String e();
    
    public aM f() {
        return aM.RIGHTCLICK;
    }
    
    public String a(final String locale) {
        return ChatColor.GREEN + aO.a("display.eventtype." + this.d.getName(), locale) + this.b(locale);
    }
    
    abstract String b(final String p0);
    
    public static HashSet<Entity> a(final Location l, final double radius) {
        return a(l, radius, null);
    }
    
    public static HashSet<Entity> a(final Location l, final double radius, final Class<? extends Entity> entityClass) {
        final int iRadius = (int)radius;
        final int chunkRadius = (iRadius < 16) ? 1 : ((iRadius - iRadius % 16) / 16);
        final HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; ++chX) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; ++chZ) {
                final int x = (int)l.getX();
                final int y = (int)l.getY();
                final int z = (int)l.getZ();
                for (final Entity e : new Location(l.getWorld(), (double)(x + chX * 16), (double)y, (double)(z + chZ * 16)).getChunk().getEntities()) {
                    if (entityClass == null || entityClass.isInstance(e)) {
                        final Location el = e.getLocation();
                        if (el.getWorld().equals(l.getWorld()) && el.distance(l) <= radius && el.getBlock() != l.getBlock()) {
                            radiusEntities.add(e);
                        }
                    }
                }
            }
        }
        return radiusEntities;
    }
    
    public static void a(final RPGItem rItem, final aM eventType, final bo<bI> power) {
        final Collection<bI> powers = rItem.a(eventType);
        for (final bI pow : powers) {
            power.a(pow);
        }
    }
    
    static {
        bI.a = new HashMap<String, Class<? extends bI>>();
        bI.b = new Z<String>();
    }
}
