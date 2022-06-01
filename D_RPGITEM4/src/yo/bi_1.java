/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.TreeType
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.enchantment.EnchantItemEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityTameEvent
 *  org.bukkit.event.entity.HorseJumpEvent
 *  org.bukkit.event.entity.PlayerLeashEntityEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.event.player.PlayerEditBookEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemBreakEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerUnleashEntityEvent
 *  org.bukkit.inventory.ItemStack
 */
package yo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.item.RPGItem;
import yo.am_0;
import yo.ao_0;
import yo.at_0;
import yo.au_0;
import yo.ay_1;
import yo.bo_0;
import yo.bv_0;
import yo.z_0;

public abstract class bi_1
implements bv_0 {
    public static HashMap<String, Class<? extends bi_1>> a = new HashMap<K, V>();
    public static z_0<String> b = new z_0<K>();
    public RPGItem c;
    public am_0 d;
    public int e = 20;
    public Random f = new Random();
    public long g = 20L;

    public boolean y(Player player) {
        return this.a(player, false);
    }

    public boolean a(Player player, boolean printWarn) {
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

    public boolean z(Player player) {
        long cooldown;
        if (!this.c() || this.g < 1L) {
            return true;
        }
        at_0 value = at_0.a(player, this.c, this.d());
        if (value == null) {
            cooldown = System.currentTimeMillis() / 50L;
            value = new at_0(player, this.c, this.d(), cooldown);
        } else {
            cooldown = value.g();
        }
        if (cooldown <= System.currentTimeMillis() / 50L) {
            value.a(System.currentTimeMillis() / 50L + this.g);
            return true;
        }
        player.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.cooldown", ao_0.a(player)), (double)(cooldown - System.currentTimeMillis() / 50L) / 20.0));
        return false;
    }

    public String d() {
        return this.e() + ".cooldown";
    }

    public int b(Player player) {
        player.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.error.power.notsupport", ao_0.a(player)), this.d.getName(), this.e()));
        return 0;
    }

    public boolean A(Player player) {
        return this.c.b((CommandSender)player);
    }

    public boolean b(Player player, boolean printWarn) {
        return this.c.a((CommandSender)player, printWarn);
    }

    @Override
    public int c(Player player) {
        return this.b(player);
    }

    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        return this.b(player);
    }

    @Override
    public int a(Player player, LivingEntity damager, ay_1 damage) {
        return this.b(player);
    }

    @Override
    public int d(Player player) {
        return this.b(player);
    }

    @Override
    public int e(Player player) {
        return this.b(player);
    }

    @Override
    public int f(Player player) {
        return this.b(player);
    }

    @Override
    public int g(Player player) {
        return this.b(player);
    }

    @Override
    public int h(Player player) {
        return this.b(player);
    }

    @Override
    public int i(Player player) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerInteractEvent event) {
        return this.b(player);
    }

    @Override
    public int b(Player player, PlayerInteractEvent event) {
        return this.b(player);
    }

    @Override
    public int c(Player player, PlayerInteractEvent event) {
        return this.b(player);
    }

    @Override
    public int d(Player player, PlayerInteractEvent event) {
        return this.b(player);
    }

    @Override
    public int e(Player player, PlayerInteractEvent event) {
        return this.b(player);
    }

    @Override
    public int f(Player player, PlayerInteractEvent event) {
        return this.b(player);
    }

    @Override
    public int g(Player player, PlayerInteractEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerInteractEntityEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, ItemStack consumeItem) {
        return this.b(player);
    }

    @Override
    public int b(Player player, LivingEntity target) {
        return this.b(player);
    }

    @Override
    public int a(Player player, LivingEntity killer) {
        return this.b(player);
    }

    @Override
    public int b(Player player, Block block) {
        return this.b(player);
    }

    @Override
    public int a(Player player, Block block) {
        return this.b(player);
    }

    @Override
    public int a(Player player, Projectile arrow) {
        return this.b(player);
    }

    @Override
    public int a(Player player, Entity target, Projectile arrow) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerFishEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, au_0 weatherType) {
        return this.b(player);
    }

    @Override
    public int j(Player player) {
        return this.b(player);
    }

    @Override
    public int k(Player player) {
        return this.b(player);
    }

    @Override
    public int a(Player player, Location location, TreeType species) {
        return this.b(player);
    }

    @Override
    public int a(Player player, int oldLevel, int newLevel) {
        return this.b(player);
    }

    @Override
    public int b(Player player, Location respawnLocation) {
        return this.b(player);
    }

    @Override
    public int l(Player player) {
        return this.b(player);
    }

    @Override
    public int m(Player player) {
        return this.b(player);
    }

    @Override
    public int n(Player player) {
        return this.b(player);
    }

    @Override
    public int o(Player player) {
        return this.b(player);
    }

    @Override
    public int p(Player player) {
        return this.b(player);
    }

    @Override
    public int q(Player player) {
        return this.b(player);
    }

    @Override
    public int r(Player player) {
        return this.b(player);
    }

    @Override
    public int s(Player player) {
        return this.b(player);
    }

    @Override
    public int t(Player player) {
        return this.b(player);
    }

    @Override
    public int u(Player player) {
        return this.b(player);
    }

    @Override
    public int v(Player player) {
        return this.b(player);
    }

    @Override
    public int w(Player player) {
        return this.b(player);
    }

    @Override
    public int x(Player player) {
        return this.b(player);
    }

    @Override
    public int c(Player player, Block bed) {
        return this.b(player);
    }

    @Override
    public int d(Player player, Block bed) {
        return this.b(player);
    }

    @Override
    public int e(Player player, Block bed) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerLeashEntityEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerUnleashEntityEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerEditBookEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, EntityRegainHealthEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, HorseJumpEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerBucketFillEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerBucketEmptyEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerItemBreakEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, PlayerTeleportEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, EntityCombustEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, EntityTameEvent event) {
        return this.b(player);
    }

    @Override
    public int a(Player player, EnchantItemEvent event) {
        return this.b(player);
    }

    public void a(ConfigurationSection s) {
        this.d = am_0.parse(s.getString("eventType"));
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

    public void b(ConfigurationSection s) {
        s.set("eventType", (Object)this.d.name());
        if (this.b()) {
            s.set("chance", (Object)this.e);
        }
        if (this.c()) {
            s.set("cooldown", (Object)this.g);
        }
        this.d(s);
    }

    abstract void c(ConfigurationSection var1);

    abstract void d(ConfigurationSection var1);

    public abstract String e();

    public am_0 f() {
        return am_0.RIGHTCLICK;
    }

    public String a(String locale) {
        return (Object)ChatColor.GREEN + ao_0.a(new StringBuilder().append("display.eventtype.").append(this.d.getName()).toString(), locale) + this.b(locale);
    }

    abstract String b(String var1);

    public static HashSet<Entity> a(Location l, double radius) {
        return bi_1.a(l, radius, null);
    }

    public static HashSet<Entity> a(Location l, double radius, Class<? extends Entity> entityClass) {
        int iRadius = (int)radius;
        int chunkRadius = iRadius < 16 ? 1 : (iRadius - iRadius % 16) / 16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; ++chX) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; ++chZ) {
                int x = (int)l.getX();
                int y = (int)l.getY();
                int z = (int)l.getZ();
                for (Entity e2 : new Location(l.getWorld(), (double)(x + chX * 16), (double)y, (double)(z + chZ * 16)).getChunk().getEntities()) {
                    Location el;
                    if (entityClass != null && !entityClass.isInstance((Object)e2) || !(el = e2.getLocation()).getWorld().equals((Object)l.getWorld()) || el.distance(l) > radius || el.getBlock() == l.getBlock()) continue;
                    radiusEntities.add(e2);
                }
            }
        }
        return radiusEntities;
    }

    public static void a(RPGItem rItem, am_0 eventType, bo_0<bi_1> power) {
        ArrayList<bi_1> powers = rItem.a(eventType);
        for (bi_1 pow : powers) {
            power.a(pow);
        }
    }
}

