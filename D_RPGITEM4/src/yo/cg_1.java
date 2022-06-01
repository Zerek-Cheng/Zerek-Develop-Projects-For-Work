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
 *  org.bukkit.entity.Projectile
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.util.Vector
 *  yo.aR
 */
package yo;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.util.Vector;
import think.rpgitems.item.RPGItem;
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.bf_0;
import yo.bf_1;
import yo.bg_1;
import yo.bo_0;
import yo.bt_1;
import yo.w_0;
import yo.x_0;

public abstract class cg_1
extends bt_1 {
    public double h;
    public int i;
    private static final x_0 j = new x_0<V>();

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), this.h(), 1.0f, 1.0f);
            Projectile projectile = player.launchProjectile(this.g());
            if (this.i > 0) {
                aR.a((Metadatable)projectile, (ar_0.a)ar_0.a.RPGITEM_BOUNCE_PROJECTILE, (Object)this.i);
            }
            a reshoot = new a(this.g(), player, new bo_0<Projectile>(){

                @Override
                public void a(Projectile projectile) {
                    bf_1.b.a_(projectile.getEntityId(), cg_1.this.c.getID());
                    aR.a((Metadatable)projectile, (ar_0.a)ar_0.a.RPGITEM_MAKE, null);
                    if (cg_1.this.h > 0.0) {
                        aR.a((Metadatable)projectile, (ar_0.a)ar_0.a.RPGITEM_DAMAGE, (Object)cg_1.this.h);
                    }
                }
            });
            reshoot.c.a(projectile);
            j.a(projectile.getEntityId(), reshoot);
            return 1;
        }
        return 0;
    }

    public static Projectile a(Projectile entity) {
        Object object = j.k_(entity.getEntityId());
        if (object instanceof a) {
            a reshoot = (a)object;
            MetadataValue o = aR.c((Metadatable)entity, (ar_0.a)ar_0.a.RPGITEM_BOUNCE_PROJECTILE);
            if (o != null) {
                try {
                    int count = o.asInt();
                    if (count > 0) {
                        --count;
                        List les = entity.getWorld().getLivingEntities();
                        les.removeAll(reshoot.d);
                        LivingEntity le = (LivingEntity)bf_0.a((Entity)entity, les, 10.0);
                        if (le != null) {
                            reshoot.d.add(le);
                            Location from = entity.getLocation();
                            Location target = le.getLocation();
                            Vector vector = target.toVector().subtract(from.toVector()).normalize();
                            vector.setY(vector.getY() + 0.35);
                            from = bg_1.a(from.clone(), vector);
                            Projectile newEntity = reshoot.a(from);
                            j.a(newEntity.getEntityId(), reshoot);
                            if (count > 0) {
                                aR.a((Metadatable)newEntity, (ar_0.a)ar_0.a.RPGITEM_BOUNCE_PROJECTILE, (Object)count);
                            }
                            newEntity.setVelocity(vector);
                            return newEntity;
                        }
                    }
                }
                catch (Exception count) {
                    // empty catch block
                }
                aR.a((Metadatable)entity, (ar_0.a)ar_0.a.RPGITEM_BOUNCE_PROJECTILE);
            }
        }
        return null;
    }

    abstract Class<? extends Projectile> g();

    abstract Sound h();

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a(new StringBuilder().append("power.").append(this.e()).toString(), locale), (double)this.g / 20.0) + (this.i > 0 ? String.format(ao_0.a("power.projectile.bounce", locale), this.i) : "");
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getDouble("damage");
        this.i = s.getInt("bounceCount");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("damage", (Object)this.h);
        s.set("bounceCount", (Object)this.i);
    }

    public static class a {
        final Class<? extends Projectile> a;
        final Player b;
        final bo_0<Projectile> c;
        private Collection<Entity> d = new HashSet<Entity>();

        public a(Class<? extends Projectile> projectileClass, Player shooter, bo_0<Projectile> callback) {
            this.a = projectileClass;
            this.b = shooter;
            this.c = callback;
            this.d.add((Entity)shooter);
        }

        public Projectile a(Location from) {
            Projectile entity = (Projectile)from.getWorld().spawn(from, this.a);
            bg_1.a(entity, (Object)this.b);
            this.c.a(entity);
            return entity;
        }
    }

}

