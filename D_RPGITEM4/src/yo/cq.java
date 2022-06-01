/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package yo;

import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import think.rpgitems.item.RPGItem;
import yo.bg_1;
import yo.bo_0;
import yo.bz_1;

public class cq
extends BukkitRunnable {
    public void run() {
        Collection<? extends Player> players = bg_1.c();
        for (final Player player : players) {
            final boolean lava = cq.a(player);
            final boolean water = cq.b(player);
            bz_1.a(bg_1.a(player), player, new bo_0<RPGItem>(){

                @Override
                public void a(RPGItem rItem) {
                    rItem.c(player);
                    if (player.isOnGround()) {
                        rItem.l(player);
                    }
                    if (player.getAllowFlight() && player.isFlying()) {
                        rItem.m(player);
                    }
                    if (lava || water) {
                        rItem.n(player);
                        if (lava) {
                            rItem.p(player);
                        } else if (water) {
                            rItem.o(player);
                        }
                    }
                    if (player.isSleeping()) {
                        rItem.q(player);
                    }
                    if (player.isSneaking()) {
                        rItem.r(player);
                    }
                    if (player.isSprinting()) {
                        rItem.s(player);
                    }
                    if (player.getVehicle() != null) {
                        rItem.t(player);
                    }
                    if (player.getWorld().isThundering() || player.getWorld().hasStorm()) {
                        rItem.u(player);
                        if (player.getWorld().isThundering()) {
                            rItem.w(player);
                        }
                        if (player.getWorld().hasStorm()) {
                            rItem.x(player);
                        }
                    } else {
                        rItem.v(player);
                    }
                }
            });
        }
    }

    private static boolean a(Player player) {
        return player.getLocation().getBlock().getType() == Material.LAVA || player.getLocation().getBlock().getType() == Material.STATIONARY_LAVA;
    }

    private static boolean b(Player player) {
        return player.getLocation().getBlock().getType() == Material.WATER || player.getLocation().getBlock().getType() == Material.STATIONARY_WATER;
    }

}

