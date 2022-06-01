// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.Iterator;
import java.util.Collection;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.Metadatable;
import org.bukkit.Location;
import think.rpgitems.item.RPGItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import java.util.Set;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class cp extends bt
{
    public long h;
    public float i;
    public boolean j;
    public boolean k;
    
    public cp() {
        this.h = 3000L;
        this.i = 1.0f;
        this.j = false;
        this.k = false;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.BURP, 1.0f, 1.0f);
            final a autoExplode = new a(this, this.c, (LivingEntity)player, player.getLocation(), bg.a((LivingEntity)player, null, 30).getLocation(), this.h, this.i, this.j, this.k);
            return 1;
        }
        return 0;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.throw", locale), this.g / 20.0);
    }
    
    @Override
    public String e() {
        return "throw";
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getLong("delay", 3000L);
        this.i = (float)s.getDouble("cooldown", 1.0);
        this.j = s.getBoolean("setFire");
        this.k = s.getBoolean("breakBlocks");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("delay", (Object)this.h);
        s.set("power", (Object)this.i);
        s.set("setFire", (Object)this.j);
        s.set("breakBlocks", (Object)this.k);
    }
    
    public static class a
    {
        private final RPGItem a;
        private final LivingEntity b;
        private final Location c;
        private final long d;
        private final float e;
        private final boolean f;
        private final boolean g;
        
        public a(final cp powerThrow, final RPGItem rpgitem, final LivingEntity shooter, final Location from, final Location to, final long delay, final float power, final boolean setFire, final boolean breakBlocks) {
            this.a = rpgitem;
            this.b = shooter;
            this.c = from;
            this.d = delay;
            this.e = power;
            this.f = setFire;
            this.g = breakBlocks;
            final bg.b ham = bg.a(shooter, rpgitem.toItemStack(), from, to, 1.0f);
            aR.a((Metadatable)ham.a().b(), aR.a.CANT_PICKUP, null);
            final long startTime = System.currentTimeMillis();
            ham.a(aX.OVER, new Runnable() {
                @Override
                public void run() {
                    final Location loc = ham.a().b().getLocation();
                    final Collection<Entity> ents = bI.a(ham.a().b().getLocation(), powerThrow.i * 1.5);
                    for (final Entity ent : ents) {
                        aR.a((Metadatable)ent, aR.a.POWER_THROW, new bx(shooter, rpgitem), 30L, false);
                    }
                    ham.a().b().remove();
                    cp.a.this.a(loc);
                }
            });
            ham.a(aX.LOOP, new Runnable() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - startTime >= cp.a.this.d) {
                        ham.b();
                    }
                    else {
                        final Collection<Entity> ent = bI.a(ham.a().b().getLocation(), 0.5);
                        if (!ent.isEmpty()) {
                            if (ent.size() == 1 && ent.contains(cp.a.this.b)) {
                                return;
                            }
                            ham.b();
                        }
                    }
                }
            });
        }
        
        private void a(final Location loc) {
            loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), this.e, this.f, this.g);
        }
        
        public RPGItem a() {
            return this.a;
        }
        
        public Entity b() {
            return (Entity)this.b;
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
