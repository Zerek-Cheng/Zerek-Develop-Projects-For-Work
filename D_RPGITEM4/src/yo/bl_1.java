/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 *  yo.aR
 *  yo.bL
 */
package yo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.Metadatable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import think.rpgitems.Plugin;
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.ay_1;
import yo.bL;
import yo.bf_0;
import yo.bf_1;
import yo.bg_1;
import yo.bt_1;
import yo.v_1;

public class bl_1
extends bt_1 {
    public int h = 20;
    public double i = 10.0;
    public double j;
    public List<a> k = new ArrayList<a>();
    public String l = "10:15|14:20";

    public static List<a> c(String string) {
        String[] top;
        ArrayList<a> list = new ArrayList<a>();
        for (String next : top = string.split("\\|")) {
            String[] sp = next.split(":");
            if (sp.length != 2) continue;
            try {
                long intervalTick = Long.parseLong(sp[0]);
                int amount = Integer.parseInt(sp[1]);
                if (intervalTick < 0L || amount < 1) continue;
                list.add(new a(intervalTick, amount));
            }
            catch (Exception intervalTick) {
                // empty catch block
            }
        }
        return list;
    }

    @Override
    public int a(Player player) {
        return this.a(player, bf_0.a((LivingEntity)player, 6));
    }

    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        return this.a(player, target.getLocation());
    }

    @Override
    public int a(Player player, LivingEntity damager, ay_1 damage) {
        return this.a(player, damager.getLocation());
    }

    @Override
    public int a(Player player, Projectile arrow) {
        return this.a(player, arrow.getLocation());
    }

    @Override
    public int a(Player player, LivingEntity killer) {
        return this.a(player, killer != null ? killer.getLocation() : player.getLocation());
    }

    private int a(Player player, Location loc) {
        if (this.a(player, true)) {
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            Block skyBlock = loc.getBlock().getRelative(BlockFace.UP);
            for (int height = 0; skyBlock.getType() == Material.AIR && height < this.h; ++height) {
                skyBlock = skyBlock.getRelative(BlockFace.UP);
            }
            ArrayList<a> tempWaves = new ArrayList<a>(this.k);
            this.a(tempWaves.iterator(), skyBlock, loc, player);
            return 1;
        }
        return 0;
    }

    private void a(final Iterator<a> it, final Block skyBlock, final Location landLoc, final Player player) {
        if (it.hasNext()) {
            final a wave = it.next();
            Bukkit.getScheduler().runTaskLater((org.bukkit.plugin.Plugin)Plugin.c, new Runnable(){

                @Override
                public void run() {
                    List skyLocs = bL.a((bl_1)bl_1.this, (Location)skyBlock.getLocation(), (int)wave.b);
                    List landLocs = bL.a((bl_1)bl_1.this, (Location)landLoc, (int)wave.b);
                    for (int i = 0; i < wave.b; ++i) {
                        Vector v = ((Location)skyLocs.get(i)).add(0.0, 0.75, 0.0).toVector().subtract(((Location)landLocs.get(i)).toVector()).normalize();
                        Arrow arrow = skyBlock.getWorld().spawnArrow((Location)skyLocs.get(i), v, 1.0f, 12.0f);
                        bg_1.a((Projectile)arrow, (Object)player);
                        if (bl_1.this.j > 0.0) {
                            aR.a((Metadatable)arrow, (ar_0.a)ar_0.a.RPGITEM_DAMAGE, (Object)bl_1.this.j);
                        }
                        aR.a((Metadatable)arrow, (ar_0.a)ar_0.a.RPGITEM_MAKE, null);
                        bf_1.a.a(arrow.getEntityId(), (byte)1);
                    }
                    bL.a((bl_1)bl_1.this, (Iterator)it, (Block)skyBlock, (Location)landLoc, (Player)player);
                }
            }, wave.a);
        }
    }

    private List<Location> a(Location source, int amount) {
        ArrayList<Location> targetLocs = new ArrayList<Location>();
        for (int i = 0; i < amount; ++i) {
            targetLocs.add(this.a(source));
        }
        return targetLocs;
    }

    private Location a(Location source) {
        return new Location(source.getWorld(), bg_1.a(source.getX() - this.i, source.getX() + this.i), source.getY(), bg_1.a(source.getZ() - this.i, source.getZ() + this.i));
    }

    @Override
    public boolean b() {
        return true;
    }

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.arrowrain", locale), (int)(1.0 / (double)this.e * 100.0), (double)this.g / 20.0);
    }

    @Override
    public String e() {
        return "arrowrain";
    }

    @Override
    public void c(ConfigurationSection s) {
        String temp = s.getString("waves");
        if (temp != null && !temp.isEmpty()) {
            this.l = temp;
        }
        this.k = bl_1.c(this.l);
        this.h = s.getInt("height");
        this.i = s.getDouble("radius");
        this.j = s.getDouble("damage");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("waves", (Object)this.l);
        s.set("height", (Object)this.h);
        s.set("radius", (Object)this.i);
        s.set("damage", (Object)this.j);
    }

    static /* synthetic */ List a(bl_1 x0, Location x1, int x2) {
        return x0.a(x1, x2);
    }

    static /* synthetic */ void a(bl_1 x0, Iterator x1, Block x2, Location x3, Player x4) {
        x0.a(x1, x2, x3, x4);
    }

    public static class a {
        public long a;
        public int b;

        public a(long intervalTick, int amount) {
            this.a = intervalTick;
            this.b = amount;
        }
    }

}

