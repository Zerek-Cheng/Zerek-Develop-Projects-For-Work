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
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 *  yo.aR
 */
package yo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.metadata.Metadatable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import think.rpgitems.Plugin;
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.bt_1;

public class ch
extends bt_1 {
    public int h = 5;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            final ArrayList<FallingBlock> blocks = new ArrayList<FallingBlock>();
            for (int i = 0; i < this.h; ++i) {
                FallingBlock block = player.getWorld().spawnFallingBlock(player.getLocation().add(0.0, 1.8, 0.0), Material.WOOL, (byte)this.f.nextInt(16));
                block.setVelocity(player.getLocation().getDirection().multiply(new Vector(this.f.nextDouble() * 2.0 + 0.5, this.f.nextDouble() * 2.0 + 0.5, this.f.nextDouble() * 2.0 + 0.5)));
                block.setDropItem(false);
                aR.a((Metadatable)block, (ar_0.a)ar_0.a.RPGITEM_MAKE, null);
                blocks.add(block);
            }
            new BukkitRunnable(){
                ArrayList<Location> a = new ArrayList();

                public void run() {
                    Iterator<Location> l = this.a.iterator();
                    while (l.hasNext()) {
                        Location loc = l.next();
                        if (ch.this.f.nextBoolean()) {
                            Block b2 = loc.getBlock();
                            if (b2.getType() == Material.WOOL) {
                                loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.WOOL.getId(), (int)b2.getData());
                                b2.setType(Material.AIR);
                            }
                            l.remove();
                        }
                        if (ch.this.f.nextInt(5) != 0) continue;
                        break;
                    }
                    Iterator it = blocks.iterator();
                    while (it.hasNext()) {
                        FallingBlock block = (FallingBlock)it.next();
                        if (!block.isDead()) continue;
                        this.a.add(block.getLocation());
                        it.remove();
                    }
                    if (this.a.isEmpty() && blocks.isEmpty()) {
                        this.cancel();
                    }
                }
            }.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.c, 0L, 5L);
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.rainbow", locale), this.h, (double)this.g / 20.0);
    }

    @Override
    public String e() {
        return "rainbow";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("count", 5);
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("count", (Object)this.h);
    }

}

