/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package yo;

import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import think.rpgitems.Plugin;
import yo.aa_0;
import yo.ao_0;
import yo.bt_1;

public class by_1
extends bt_1 {
    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.FIZZ, 1.0f, 0.1f);
            final FallingBlock block = player.getWorld().spawnFallingBlock(player.getLocation().add(0.0, 1.8, 0.0), Material.ICE, (byte)0);
            block.setVelocity(player.getLocation().getDirection().multiply(2.0));
            block.setDropItem(false);
            BukkitRunnable run = new BukkitRunnable(){

                public void run() {
                    boolean hit = false;
                    World world = block.getWorld();
                    Location bLoc = block.getLocation();
                    block0 : for (int x = -1; x < 2; ++x) {
                        for (int y = -1; y < 2; ++y) {
                            for (int z = -1; z < 2; ++z) {
                                Block b2;
                                Location loc = block.getLocation().add((double)x, (double)y, (double)z);
                                if (world.getBlockTypeIdAt(loc) == Material.AIR.getId() || !(b2 = world.getBlockAt(loc)).getType().isSolid() || !by_1.this.a(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 1.0, 1.0, 1.0, bLoc.getX() - 0.5, bLoc.getY() - 0.5, bLoc.getZ() - 0.5, 1.0, 1.0, 1.0)) continue;
                                hit = true;
                                break block0;
                            }
                        }
                    }
                    if (!hit) {
                        List entities = block.getNearbyEntities(1.0, 1.0, 1.0);
                        for (Entity e2 : entities) {
                            if (e2 == player) continue;
                            hit = true;
                            break;
                        }
                    }
                    if (block.isDead() || hit) {
                        block.remove();
                        block.getLocation().getBlock().setType(Material.AIR);
                        this.cancel();
                        final aa_0<Location> changedBlocks = new aa_0<Location>();
                        for (int x = -1; x < 2; ++x) {
                            for (int y = -1; y < 3; ++y) {
                                for (int z = -1; z < 2; ++z) {
                                    Location loc = block.getLocation().add((double)x, (double)y, (double)z);
                                    Block b3 = world.getBlockAt(loc);
                                    if (b3.getType().isSolid()) continue;
                                    changedBlocks.a(b3.getLocation(), (long)(b3.getTypeId() | b3.getData() << 16));
                                    b3.setType(Material.ICE);
                                }
                            }
                        }
                        new BukkitRunnable(){

                            public void run() {
                                for (int i = 0; i < 4; ++i) {
                                    if (changedBlocks.b()) {
                                        this.cancel();
                                        return;
                                    }
                                    int index = by_1.this.f.nextInt(changedBlocks.c());
                                    long data = changedBlocks.I_()[index];
                                    Location position = (Location)changedBlocks.G_()[index];
                                    changedBlocks.h_((Object)position);
                                    Block c2 = position.getBlock();
                                    position.getWorld().playEffect(position, Effect.STEP_SOUND, c2.getTypeId());
                                    c2.setTypeId((int)(data & 65535L));
                                    c2.setData((byte)(data >> 16));
                                }
                            }
                        }.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.c, (long)(80 + by_1.this.f.nextInt(40)), 3L);
                    }
                }

            };
            run.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.c, 0L, 1L);
            return 1;
        }
        return 0;
    }

    private boolean a(double x1, double y1, double z1, double w1, double h1, double d1, double x2, double y2, double z2, double w2, double h2, double d2) {
        if (x1 + w1 < x2) {
            return false;
        }
        if (x2 + w2 < x1) {
            return false;
        }
        if (y1 + h1 < y2) {
            return false;
        }
        if (y2 + h2 < y1) {
            return false;
        }
        if (z1 + d1 < z2) {
            return false;
        }
        if (z2 + d2 < z1) {
            return false;
        }
        return true;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.ice", locale), (double)this.g / 20.0);
    }

    @Override
    public String e() {
        return "ice";
    }

    @Override
    public void c(ConfigurationSection s) {
    }

    @Override
    public void d(ConfigurationSection s) {
    }

}

