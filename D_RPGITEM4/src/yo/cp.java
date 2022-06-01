/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.Metadatable
 *  yo.aR
 *  yo.bg
 */
package yo;

import java.util.HashSet;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;
import think.rpgitems.item.RPGItem;
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.ax_1;
import yo.bg;
import yo.bg_1;
import yo.bi_1;
import yo.bt_1;
import yo.bx_0;

public class cp
extends bt_1 {
    public long h = 3000L;
    public float i = 1.0f;
    public boolean j = false;
    public boolean k = false;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.BURP, 1.0f, 1.0f);
            a autoExplode = new a(this, this.c, (LivingEntity)player, player.getLocation(), bg_1.a((LivingEntity)player, null, 30).getLocation(), this.h, this.i, this.j, this.k);
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.throw", locale), (double)this.g / 20.0);
    }

    @Override
    public String e() {
        return "throw";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getLong("delay", 3000L);
        this.i = (float)s.getDouble("cooldown", 1.0);
        this.j = s.getBoolean("setFire");
        this.k = s.getBoolean("breakBlocks");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("delay", (Object)this.h);
        s.set("power", (Object)Float.valueOf(this.i));
        s.set("setFire", (Object)this.j);
        s.set("breakBlocks", (Object)this.k);
    }

    public static class a {
        private final RPGItem a;
        private final LivingEntity b;
        private final Location c;
        private final long d;
        private final float e;
        private final boolean f;
        private final boolean g;

        public a(final cp powerThrow, final RPGItem rpgitem, final LivingEntity shooter, Location from, Location to, long delay, float power, boolean setFire, boolean breakBlocks) {
            this.a = rpgitem;
            this.b = shooter;
            this.c = from;
            this.d = delay;
            this.e = power;
            this.f = setFire;
            this.g = breakBlocks;
            final bg_1.b ham = bg.a((LivingEntity)shooter, (Object)rpgitem.toItemStack(), (Location)from, (Location)to, (float)1.0f);
            aR.a((Metadatable)ham.a().b(), (ar_0.a)ar_0.a.CANT_PICKUP, null);
            final long startTime = System.currentTimeMillis();
            ham.a(ax_1.OVER, new Runnable(){

                @Override
                public void run() {
                    Location loc = ham.a().b().getLocation();
                    HashSet<Entity> ents = bi_1.a(ham.a().b().getLocation(), (double)powerThrow.i * 1.5);
                    for (Entity ent : ents) {
                        aR.a((Metadatable)ent, (ar_0.a)ar_0.a.POWER_THROW, (Object)new bx_0(shooter, rpgitem), (long)30L, (boolean)false);
                    }
                    ham.a().b().remove();
                    a.this.a(loc);
                }
            });
            ham.a(ax_1.LOOP, new Runnable(){

                @Override
                public void run() {
                    if (System.currentTimeMillis() - startTime >= a.this.d) {
                        ham.b();
                    } else {
                        HashSet<Entity> ent = bi_1.a(ham.a().b().getLocation(), 0.5);
                        if (!ent.isEmpty()) {
                            if (ent.size() == 1 && ent.contains((Object)a.this.b)) {
                                return;
                            }
                            ham.b();
                        }
                    }
                }
            });
        }

        private void a(Location loc) {
            loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), this.e, this.f, this.g);
        }

        public RPGItem a() {
            return this.a;
        }

        public Entity b() {
            return this.b;
        }

        public Location c() {
            return this.c;
        }

        public long d() {
            return this.d;
        }

        public float e() {
            return this.e;
        }

        public boolean f() {
            return this.f;
        }

        public boolean g() {
            return this.g;
        }

    }

}

