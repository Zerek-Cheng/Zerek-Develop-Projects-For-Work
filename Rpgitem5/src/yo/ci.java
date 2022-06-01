// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import think.rpgitems.Plugin;
import java.util.Iterator;
import java.util.Collection;
import org.bukkit.block.Block;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Entity;
import org.bukkit.Effect;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;

public class ci extends bt
{
    public int h;
    public int i;
    
    public ci() {
        this.h = 2;
        this.i = 15;
    }
    
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
            final BukkitRunnable task = new BukkitRunnable() {
                private int e = 0;
                
                public void run() {
                    final Location above = location.clone().add(0.0, 1.0, 0.0);
                    if (above.getBlock().getType().isSolid() || !location.getBlock().getType().isSolid()) {
                        this.cancel();
                        return;
                    }
                    final Location temp = location.clone();
                    for (int x = -2; x <= 2; ++x) {
                        for (int z = -2; z <= 2; ++z) {
                            temp.setX((double)(x + location.getBlockX()));
                            temp.setZ((double)(z + location.getBlockZ()));
                            final Block block = temp.getBlock();
                            temp.getWorld().playEffect(temp, Effect.STEP_SOUND, block.getTypeId());
                        }
                    }
                    Collection<Entity> near = bI.a(location, 1.5);
                    boolean hit = false;
                    for (final Entity e : near) {
                        if (e != player) {
                            hit = true;
                            break;
                        }
                    }
                    if (hit) {
                        location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), (float)ci.this.h, false, false);
                        near = bI.a(location, 2.5);
                        for (final Entity e : near) {
                            if (e != player && !(e instanceof Hanging)) {
                                e.setVelocity(new Vector(ci.this.f.nextGaussian() / 4.0, 1.0 + ci.this.f.nextDouble() * ci.this.h, ci.this.f.nextGaussian() / 4.0));
                            }
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
            task.runTaskTimerAsynchronously((org.bukkit.plugin.Plugin)Plugin.c, 0L, 3L);
            return 1;
        }
        return 0;
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getInt("power", 2);
        this.i = s.getInt("distance", 15);
    }
    
    public void d(final ConfigurationSection s) {
        s.set("power", (Object)this.h);
        s.set("distance", (Object)this.i);
    }
    
    @Override
    public String e() {
        return "rumble";
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.rumble", locale), this.g / 20.0);
    }
}
