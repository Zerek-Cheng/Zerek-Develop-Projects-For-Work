// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import java.util.Iterator;
import java.util.List;
import org.bukkit.World;
import think.rpgitems.Plugin;
import org.bukkit.block.Block;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class bY extends bt
{
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
            final BukkitRunnable run = new BukkitRunnable() {
                public void run() {
                    boolean hit = false;
                    final World world = block.getWorld();
                    final Location bLoc = block.getLocation();
                Label_0193:
                    for (int x = -1; x < 2; ++x) {
                        for (int y = -1; y < 2; ++y) {
                            for (int z = -1; z < 2; ++z) {
                                final Location loc = block.getLocation().add((double)x, (double)y, (double)z);
                                if (world.getBlockTypeIdAt(loc) != Material.AIR.getId()) {
                                    final Block b = world.getBlockAt(loc);
                                    if (b.getType().isSolid() && bY.this.a(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 1.0, 1.0, 1.0, bLoc.getX() - 0.5, bLoc.getY() - 0.5, bLoc.getZ() - 0.5, 1.0, 1.0, 1.0)) {
                                        hit = true;
                                        break Label_0193;
                                    }
                                }
                            }
                        }
                    }
                    if (!hit) {
                        final List<Entity> entities = (List<Entity>)block.getNearbyEntities(1.0, 1.0, 1.0);
                        for (final Entity e : entities) {
                            if (e != player) {
                                hit = true;
                                break;
                            }
                        }
                    }
                    if (block.isDead() || hit) {
                        block.remove();
                        block.getLocation().getBlock().setType(Material.AIR);
                        this.cancel();
                        final aa<Location> changedBlocks = new aa<Location>();
                        for (int x2 = -1; x2 < 2; ++x2) {
                            for (int y2 = -1; y2 < 3; ++y2) {
                                for (int z2 = -1; z2 < 2; ++z2) {
                                    final Location loc2 = block.getLocation().add((double)x2, (double)y2, (double)z2);
                                    final Block b2 = world.getBlockAt(loc2);
                                    if (!b2.getType().isSolid()) {
                                        changedBlocks.a(b2.getLocation(), b2.getTypeId() | b2.getData() << 16);
                                        b2.setType(Material.ICE);
                                    }
                                }
                            }
                        }
                        new BukkitRunnable() {
                            public void run() {
                                for (int i = 0; i < 4; ++i) {
                                    if (changedBlocks.b()) {
                                        this.cancel();
                                        return;
                                    }
                                    final int index = bY.this.f.nextInt(changedBlocks.c());
                                    final long data = changedBlocks.I_()[index];
                                    final Location position = (Location)changedBlocks.G_()[index];
                                    changedBlocks.h_(position);
                                    final Block c = position.getBlock();
                                    position.getWorld().playEffect(position, Effect.STEP_SOUND, c.getTypeId());
                                    c.setTypeId((int)(data & 0xFFFFL));
                                    c.setData((byte)(data >> 16));
                                }
                            }
                        }.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.c, (long)(80 + bY.this.f.nextInt(40)), 3L);
                    }
                }
            };
            run.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.c, 0L, 1L);
            return 1;
        }
        return 0;
    }
    
    private boolean a(final double x1, final double y1, final double z1, final double w1, final double h1, final double d1, final double x2, final double y2, final double z2, final double w2, final double h2, final double d2) {
        return x1 + w1 >= x2 && x2 + w2 >= x1 && y1 + h1 >= y2 && y2 + h2 >= y1 && z1 + d1 >= z2 && z2 + d2 >= z1;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.ice", locale), this.g / 20.0);
    }
    
    @Override
    public String e() {
        return "ice";
    }
    
    public void c(final ConfigurationSection s) {
    }
    
    public void d(final ConfigurationSection s) {
    }
}
