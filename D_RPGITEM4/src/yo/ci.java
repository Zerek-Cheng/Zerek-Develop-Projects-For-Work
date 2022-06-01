/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Hanging
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package yo;

import java.util.HashSet;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import yo.ao_0;
import yo.bi_1;
import yo.bt_1;

public class ci
extends bt_1 {
    public int h = 2;
    public int i = 15;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            final Location location = player.getLocation().add(0.0, -0.2, 0.0);
            final Vector direction = player.getLocation().getDirection();
            direction.setY(0);
            direction.normalize();
            BukkitRunnable task = new BukkitRunnable(){
                private int e = 0;

                public void run() {
                    Location above = location.clone().add(0.0, 1.0, 0.0);
                    if (above.getBlock().getType().isSolid() || !location.getBlock().getType().isSolid()) {
                        this.cancel();
                        return;
                    }
                    Location temp = location.clone();
                    for (int x = -2; x <= 2; ++x) {
                        for (int z = -2; z <= 2; ++z) {
                            temp.setX((double)(x + location.getBlockX()));
                            temp.setZ((double)(z + location.getBlockZ()));
                            Block block = temp.getBlock();
                            temp.getWorld().playEffect(temp, Effect.STEP_SOUND, block.getTypeId());
                        }
                    }
                    HashSet<Entity> near = bi_1.a(location, 1.5);
                    boolean hit = false;
                    for (Entity e2 : near) {
                        if (e2 == player) continue;
                        hit = true;
                        break;
                    }
                    if (hit) {
                        location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), (float)ci.this.h, false, false);
                        near = bi_1.a(location, 2.5);
                        for (Entity e2 : near) {
                            if (e2 == player || e2 instanceof Hanging) continue;
                            e2.setVelocity(new Vector(ci.this.f.nextGaussian() / 4.0, 1.0 + ci.this.f.nextDouble() * (double)ci.this.h, ci.this.f.nextGaussian() / 4.0));
                        }
                        this.cancel();
                        return;
                    }
                    location.add(direction);
                    if (this.e >= ci.this.i) {
                        this.cancel();
                    }
                    ++this.e;
                }
            };
            task.runTaskTimerAsynchronously((Plugin)think.rpgitems.Plugin.c, 0L, 3L);
            return 1;
        }
        return 0;
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("power", 2);
        this.i = s.getInt("distance", 15);
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("power", (Object)this.h);
        s.set("distance", (Object)this.i);
    }

    @Override
    public String e() {
        return "rumble";
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.rumble", locale), (double)this.g / 20.0);
    }

}

