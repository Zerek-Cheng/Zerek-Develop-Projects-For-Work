/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package su.nightexpress.divineitems.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.utils.Utils;

public class ParticleUtils {
    private static DivineItems plugin = DivineItems.instance;

    public static void wave(Location location) {
        new BukkitRunnable(){
            int i = 0;

            public void run() {
                if (++this.i == 20) {
                    this.cancel();
                }
                int n = this.i;
                double d = (0.5 + (double)n * 0.15) % 3.0;
                int n2 = 0;
                while ((double)n2 < d * 10.0) {
                    double d2 = 6.283185307179586 / (d * 10.0) * (double)n2;
                    Utils.playEffect("REDSTONE", 0.10000000149011612, 0.10000000149011612, 0.10000000149011612, 0.0, 2, Utils.getPointOnCircle(location, false, d2, d, 1.0));
                    if (n < 15) {
                        Utils.playEffect("CRIT_MAGIC", 0.10000000149011612, 0.10000000149011612, 0.10000000149011612, 0.0, 1, Utils.getPointOnCircle(location, false, d2, d, 1.0));
                    }
                    ++n2;
                }
            }
        }.runTaskTimerAsynchronously((Plugin)plugin, 0L, 1L);
    }

    public static void doParticle(Entity entity, final String string, final String string2) {
        new BukkitRunnable(){
            int i = 1;
            int k = 0;
            World localWorld;
            double d3;
            double d5;
            double d7;
            {
                this.localWorld = entity.getWorld();
                this.d3 = entity.getLocation().getX();
                this.d5 = entity.getLocation().getY();
                this.d7 = entity.getLocation().getZ();
            }

            public void run() {
                if (this.k == 3) {
                    this.cancel();
                }
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3, this.d5 + 1.8, this.d7));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3, this.d5 + 1.5, this.d7));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 + 0.6, this.d5 + 1.8, this.d7));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 + 0.6, this.d5 + 1.5, this.d7));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 + 0.6, this.d5 + 1.8, this.d7 + 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 + 0.6, this.d5 + 1.5, this.d7 + 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 - 0.6, this.d5 + 1.8, this.d7));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 - 0.6, this.d5 + 1.5, this.d7));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 - 0.6, this.d5 + 1.8, this.d7 + 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 - 0.6, this.d5 + 1.5, this.d7 + 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3, this.d5 + 1.8, this.d7 + 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 - 0.6, this.d5 + 1.5, this.d7 + 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 + 0.6, this.d5 + 1.8, this.d7 - 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 + 0.6, this.d5 + 1.5, this.d7 - 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3, this.d5 + 1.8, this.d7 - 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3, this.d5 + 1.5, this.d7 - 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 - 0.6, this.d5 + 1.8, this.d7 - 0.6));
                Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, this.i, new Location(this.localWorld, this.d3 - 0.6, this.d5 + 1.5, this.d7 - 0.6));
                Utils.playEffect(string2, 0.0, 0.0, 0.0, 0.0, 2, new Location(this.localWorld, this.d3, this.d5 + 1.0, this.d7));
                ++this.k;
            }
        }.runTaskTimer((Plugin)plugin, 0L, 5L);
    }

    public static void repairEffect(Location location) {
        new BukkitRunnable(){
            int i = 0;

            public void run() {
                Location loc=location;
                if (++this.i == 72) {
                    this.cancel();
                }
                int n = this.i;
                double d = 0.3141592653589793 * (double)n;
                double d2 = (double)n * 0.1 % 2.5;
                double d3 = 0.45;
                Location location = Utils.getPointOnCircle(Utils.getCenter(loc), true, d, 0.45, d2);
                Location location2 = Utils.getPointOnCircle(Utils.getCenter(loc), true, d - 3.141592653589793, 0.45, d2);
                Utils.playEffect("FLAME", 0.0, 0.0, 0.0, 0.0, 1, location);
                Utils.playEffect("FLAME", 0.0, 0.0, 0.0, 0.0, 1, location2);
                Utils.playEffect("SPELL_WITCH", 0.20000000298023224, 0.0, 0.20000000298023224, 0.0, 5, Utils.getCenter(loc).add(0.0, 0.5, 0.0));
            }
        }.runTaskTimerAsynchronously((Plugin)plugin, 0L, 1L);
    }

    public static void drawParticleLine(LivingEntity livingEntity, LivingEntity livingEntity2, String string, float f, int n) {
        Location location = livingEntity2.getLocation();
        Location location2 = livingEntity.getLocation();
        Vector vector = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()).toVector();
        location2.setDirection(vector.subtract(location2.toVector()));
        Vector vector2 = location2.getDirection();
        int n2 = 0;
        while ((double)n2 < livingEntity.getLocation().distance(location)) {
            Location location3 = location2.add(vector2);
            Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, 5, location3);
            ++n2;
        }
    }

}

