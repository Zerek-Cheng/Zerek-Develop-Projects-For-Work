// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;
import org.bukkit.metadata.Metadatable;
import think.rpgitems.Plugin;
import org.bukkit.Bukkit;
import java.util.Iterator;
import org.bukkit.block.Block;
import java.util.Collection;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class bL extends bt
{
    public int h;
    public double i;
    public double j;
    public List<a> k;
    public String l;
    
    public bL() {
        this.h = 20;
        this.i = 10.0;
        this.k = new ArrayList<a>();
        this.l = "10:15|14:20";
    }
    
    public static List<a> c(final String string) {
        final List<a> list = new ArrayList<a>();
        final String[] arr$;
        final String[] top = arr$ = string.split("\\|");
        for (final String next : arr$) {
            final String[] sp = next.split(":");
            if (sp.length == 2) {
                try {
                    final long intervalTick = Long.parseLong(sp[0]);
                    final int amount = Integer.parseInt(sp[1]);
                    if (intervalTick >= 0L && amount >= 1) {
                        list.add(new a(intervalTick, amount));
                    }
                }
                catch (Exception ex) {}
            }
        }
        return list;
    }
    
    @Override
    public int a(final Player player) {
        return this.a(player, bf.a((LivingEntity)player, 6));
    }
    
    @Override
    public int b(final Player player, final LivingEntity target, final aY damage) {
        return this.a(player, target.getLocation());
    }
    
    @Override
    public int a(final Player player, final LivingEntity damager, final aY damage) {
        return this.a(player, damager.getLocation());
    }
    
    @Override
    public int a(final Player player, final Projectile arrow) {
        return this.a(player, arrow.getLocation());
    }
    
    @Override
    public int a(final Player player, final LivingEntity killer) {
        return this.a(player, (killer != null) ? killer.getLocation() : player.getLocation());
    }
    
    private int a(final Player player, final Location loc) {
        if (this.a(player, true)) {
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            Block skyBlock = loc.getBlock().getRelative(BlockFace.UP);
            for (int height = 0; skyBlock.getType() == Material.AIR && height < this.h; skyBlock = skyBlock.getRelative(BlockFace.UP), ++height) {}
            final List<a> tempWaves = new ArrayList<a>(this.k);
            this.a(tempWaves.iterator(), skyBlock, loc, player);
            return 1;
        }
        return 0;
    }
    
    private void a(final Iterator<a> it, final Block skyBlock, final Location landLoc, final Player player) {
        if (it.hasNext()) {
            final a wave = it.next();
            Bukkit.getScheduler().runTaskLater((org.bukkit.plugin.Plugin)Plugin.c, (Runnable)new Runnable() {
                @Override
                public void run() {
                    final List<Location> skyLocs = bL.this.a(skyBlock.getLocation(), wave.b);
                    final List<Location> landLocs = bL.this.a(landLoc, wave.b);
                    for (int i = 0; i < wave.b; ++i) {
                        final Vector v = skyLocs.get(i).add(0.0, 0.75, 0.0).toVector().subtract(landLocs.get(i).toVector()).normalize();
                        final Arrow arrow = skyBlock.getWorld().spawnArrow((Location)skyLocs.get(i), v, 1.0f, 12.0f);
                        bg.a((Projectile)arrow, player);
                        if (bL.this.j > 0.0) {
                            aR.a((Metadatable)arrow, aR.a.RPGITEM_DAMAGE, bL.this.j);
                        }
                        aR.a((Metadatable)arrow, aR.a.RPGITEM_MAKE, null);
                        bF.a.a(arrow.getEntityId(), (byte)1);
                    }
                    bL.this.a(it, skyBlock, landLoc, player);
                }
            }, wave.a);
        }
    }
    
    private List<Location> a(final Location source, final int amount) {
        final List<Location> targetLocs = new ArrayList<Location>();
        for (int i = 0; i < amount; ++i) {
            targetLocs.add(this.a(source));
        }
        return targetLocs;
    }
    
    private Location a(final Location source) {
        return new Location(source.getWorld(), bg.a(source.getX() - this.i, source.getX() + this.i), source.getY(), bg.a(source.getZ() - this.i, source.getZ() + this.i));
    }
    
    @Override
    public boolean b() {
        return true;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.arrowrain", locale), (int)(1.0 / this.e * 100.0), this.g / 20.0);
    }
    
    @Override
    public String e() {
        return "arrowrain";
    }
    
    public void c(final ConfigurationSection s) {
        final String temp = s.getString("waves");
        if (temp != null && !temp.isEmpty()) {
            this.l = temp;
        }
        this.k = c(this.l);
        this.h = s.getInt("height");
        this.i = s.getDouble("radius");
        this.j = s.getDouble("damage");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("waves", (Object)this.l);
        s.set("height", (Object)this.h);
        s.set("radius", (Object)this.i);
        s.set("damage", (Object)this.j);
    }
    
    public static class a
    {
        public long a;
        public int b;
        
        public a(final long intervalTick, final int amount) {
            this.a = intervalTick;
            this.b = amount;
        }
    }
}
