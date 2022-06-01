// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.Material;
import java.util.Iterator;
import java.util.Collection;
import think.rpgitems.item.RPGItem;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class cq extends BukkitRunnable
{
    public void run() {
        final Collection<? extends Player> players = bg.c();
        for (final Player player : players) {
            final boolean lava = a(player);
            final boolean water = b(player);
            bz.a(bg.a(player), player, new bo<RPGItem>() {
                @Override
                public void a(final RPGItem rItem) {
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
                        }
                        else if (water) {
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
                    }
                    else {
                        rItem.v(player);
                    }
                }
            });
        }
    }
    
    private static boolean a(final Player player) {
        return player.getLocation().getBlock().getType() == Material.LAVA || player.getLocation().getBlock().getType() == Material.STATIONARY_LAVA;
    }
    
    private static boolean b(final Player player) {
        return player.getLocation().getBlock().getType() == Material.WATER || player.getLocation().getBlock().getType() == Material.STATIONARY_WATER;
    }
}
