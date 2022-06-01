/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.TreeType
 *  org.bukkit.block.Block
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

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
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
import yo.au_0;
import yo.ay_1;

public interface bv_0 {
    public int c(Player var1);

    public int b(Player var1, LivingEntity var2, ay_1 var3);

    public int a(Player var1, LivingEntity var2, ay_1 var3);

    public int d(Player var1);

    public int e(Player var1);

    public int f(Player var1);

    public int g(Player var1);

    public int h(Player var1);

    public int i(Player var1);

    public int a(Player var1, PlayerInteractEvent var2);

    public int b(Player var1, PlayerInteractEvent var2);

    public int c(Player var1, PlayerInteractEvent var2);

    public int d(Player var1, PlayerInteractEvent var2);

    public int e(Player var1, PlayerInteractEvent var2);

    public int f(Player var1, PlayerInteractEvent var2);

    public int g(Player var1, PlayerInteractEvent var2);

    public int a(Player var1, PlayerInteractEntityEvent var2);

    public int a(Player var1, ItemStack var2);

    public int b(Player var1, LivingEntity var2);

    public int a(Player var1, LivingEntity var2);

    public int b(Player var1, Block var2);

    public int a(Player var1, Block var2);

    public int a(Player var1, Projectile var2);

    public int a(Player var1, Entity var2, Projectile var3);

    public int a(Player var1, PlayerFishEvent var2);

    public int a(Player var1, au_0 var2);

    public int j(Player var1);

    public int k(Player var1);

    public int a(Player var1, Location var2, TreeType var3);

    public int a(Player var1, int var2, int var3);

    public int b(Player var1, Location var2);

    public int l(Player var1);

    public int m(Player var1);

    public int n(Player var1);

    public int o(Player var1);

    public int p(Player var1);

    public int q(Player var1);

    public int r(Player var1);

    public int s(Player var1);

    public int t(Player var1);

    public int u(Player var1);

    public int v(Player var1);

    public int w(Player var1);

    public int x(Player var1);

    public int c(Player var1, Block var2);

    public int d(Player var1, Block var2);

    public int e(Player var1, Block var2);

    public int a(Player var1, PlayerLeashEntityEvent var2);

    public int a(Player var1, PlayerUnleashEntityEvent var2);

    public int a(Player var1, PlayerEditBookEvent var2);

    public int a(Player var1, EntityRegainHealthEvent var2);

    public int a(Player var1, HorseJumpEvent var2);

    public int a(Player var1, PlayerBucketFillEvent var2);

    public int a(Player var1, PlayerBucketEmptyEvent var2);

    public int a(Player var1, PlayerItemBreakEvent var2);

    public int a(Player var1, PlayerTeleportEvent var2);

    public int a(Player var1, EntityCombustEvent var2);

    public int a(Player var1, EntityTameEvent var2);

    public int a(Player var1, EnchantItemEvent var2);
}

