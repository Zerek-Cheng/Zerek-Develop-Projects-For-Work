// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.HashSet;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import java.util.Collection;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.Metadatable;
import org.bukkit.entity.Player;

public abstract class cg extends bt
{
    public double h;
    public int i;
    private static final X j;
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), this.h(), 1.0f, 1.0f);
            final Projectile projectile = player.launchProjectile((Class)this.g());
            if (this.i > 0) {
                aR.a((Metadatable)projectile, aR.a.RPGITEM_BOUNCE_PROJECTILE, this.i);
            }
            final a reshoot = new a(this.g(), player, new bo<Projectile>() {
                @Override
                public void a(final Projectile projectile) {
                    bF.b.a_(projectile.getEntityId(), cg.this.c.getID());
                    aR.a((Metadatable)projectile, aR.a.RPGITEM_MAKE, null);
                    if (cg.this.h > 0.0) {
                        aR.a((Metadatable)projectile, aR.a.RPGITEM_DAMAGE, cg.this.h);
                    }
                }
            });
            reshoot.c.a(projectile);
            cg.j.a(projectile.getEntityId(), reshoot);
            return 1;
        }
        return 0;
    }
    
    public static Projectile a(final Projectile entity) {
        final Object object = cg.j.k_(entity.getEntityId());
        if (object instanceof a) {
            final a reshoot = (a)object;
            final MetadataValue o = aR.c((Metadatable)entity, aR.a.RPGITEM_BOUNCE_PROJECTILE);
            if (o != null) {
                try {
                    int count = o.asInt();
                    if (count > 0) {
                        --count;
                        final Collection<LivingEntity> les = (Collection<LivingEntity>)entity.getWorld().getLivingEntities();
                        les.removeAll(reshoot.d);
                        final LivingEntity le = bf.a((Entity)entity, les, 10.0);
                        if (le != null) {
                            reshoot.d.add(le);
                            Location from = entity.getLocation();
                            final Location target = le.getLocation();
                            final Vector vector = target.toVector().subtract(from.toVector()).normalize();
                            vector.setY(vector.getY() + 0.35);
                            from = bg.a(from.clone(), vector);
                            final Projectile newEntity = reshoot.a(from);
                            cg.j.a(newEntity.getEntityId(), reshoot);
                            if (count > 0) {
                                aR.a((Metadatable)newEntity, aR.a.RPGITEM_BOUNCE_PROJECTILE, count);
                            }
                            newEntity.setVelocity(vector);
                            return newEntity;
                        }
                    }
                }
                catch (Exception ex) {}
                aR.a((Metadatable)entity, aR.a.RPGITEM_BOUNCE_PROJECTILE);
            }
        }
        return null;
    }
    
    abstract Class<? extends Projectile> g();
    
    abstract Sound h();
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power." + this.e(), locale), this.g / 20.0) + ((this.i > 0) ? String.format(aO.a("power.projectile.bounce", locale), this.i) : "");
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getDouble("damage");
        this.i = s.getInt("bounceCount");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("damage", (Object)this.h);
        s.set("bounceCount", (Object)this.i);
    }
    
    static {
        j = new X();
    }
    
    public static class a
    {
        final Class<? extends Projectile> a;
        final Player b;
        final bo<Projectile> c;
        private Collection<Entity> d;
        
        public a(final Class<? extends Projectile> projectileClass, final Player shooter, final bo<Projectile> callback) {
            this.d = new HashSet<Entity>();
            this.a = projectileClass;
            this.b = shooter;
            this.c = callback;
            this.d.add((Entity)shooter);
        }
        
        public Projectile a(final Location from) {
            final Projectile entity = (Projectile)from.getWorld().spawn(from, (Class)this.a);
            bg.a(entity, this.b);
            this.c.a(entity);
            return entity;
        }
    }
}
