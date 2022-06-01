// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import think.rpgitems.Plugin;
import org.bukkit.block.Block;
import java.util.Iterator;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.metadata.Metadatable;
import org.bukkit.util.Vector;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import java.util.ArrayList;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ch extends bt
{
    public int h;
    
    public ch() {
        this.h = 5;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            final ArrayList<FallingBlock> blocks = new ArrayList<FallingBlock>();
            for (int i = 0; i < this.h; ++i) {
                final FallingBlock block = player.getWorld().spawnFallingBlock(player.getLocation().add(0.0, 1.8, 0.0), Material.WOOL, (byte)this.f.nextInt(16));
                block.setVelocity(player.getLocation().getDirection().multiply(new Vector(this.f.nextDouble() * 2.0 + 0.5, this.f.nextDouble() * 2.0 + 0.5, this.f.nextDouble() * 2.0 + 0.5)));
                block.setDropItem(false);
                aR.a((Metadatable)block, aR.a.RPGITEM_MAKE, null);
                blocks.add(block);
            }
            new BukkitRunnable() {
                ArrayList<Location> a = new ArrayList<Location>();
                
                public void run() {
                    final Iterator<Location> l = this.a.iterator();
                    while (l.hasNext()) {
                        final Location loc = l.next();
                        if (ch.this.f.nextBoolean()) {
                            final Block b = loc.getBlock();
                            if (b.getType() == Material.WOOL) {
                                loc.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.WOOL.getId(), (int)b.getData());
                                b.setType(Material.AIR);
                            }
                            l.remove();
                        }
                        if (ch.this.f.nextInt(5) == 0) {
                            break;
                        }
                    }
                    final Iterator<FallingBlock> it = blocks.iterator();
                    while (it.hasNext()) {
                        final FallingBlock block = it.next();
                        if (block.isDead()) {
                            this.a.add(block.getLocation());
                            it.remove();
                        }
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
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.rainbow", locale), this.h, this.g / 20.0);
    }
    
    @Override
    public String e() {
        return "rainbow";
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getInt("count", 5);
    }
    
    public void d(final ConfigurationSection s) {
        s.set("count", (Object)this.h);
    }
}
