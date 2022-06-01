/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.util.Vector
 */
package yo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class bf_0 {
    public static Location a(LivingEntity entity, int distance) {
        Location loc;
        Entity target = bf_0.a((Entity)entity);
        if (target == null) {
            loc = entity.getTargetBlock((Set)null, distance).getLocation();
            if (loc == null) {
                loc = entity.getLocation();
            }
        } else {
            loc = target.getLocation();
        }
        return loc;
    }

    public static Entity a(Entity entity) {
        LivingEntity target = bf_0.c(entity);
        if (target == null) {
            target = bf_0.b(entity);
        }
        return target;
    }

    public static Player a(Player player) {
        return (Player)bf_0.a((Entity)player, player.getWorld().getPlayers());
    }

    public static Entity b(Entity entity) {
        return bf_0.a(entity, entity.getWorld().getEntities());
    }

    public static LivingEntity c(Entity entity) {
        return (LivingEntity)bf_0.a(entity, entity.getWorld().getLivingEntities());
    }

    public static <T extends Entity> T a(Entity entity, Iterable<T> entities) {
        if (entity == null) {
            return null;
        }
        Entity target = null;
        double threshold = 1.0;
        for (Entity other : entities) {
            Vector n = other.getLocation().toVector().subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() >= threshold || n.normalize().dot(entity.getLocation().getDirection().normalize()) < 0.0 || target != null && target.getLocation().distanceSquared(entity.getLocation()) <= other.getLocation().distanceSquared(entity.getLocation())) continue;
            target = other;
            break;
        }
        return (T)target;
    }

    public static Location a(Player player, int blocks) {
        Block block = player.getTargetBlock((Set)null, blocks);
        Location targetLoc = block.getLocation();
        double distance = player.getLocation().distance(targetLoc);
        if (distance > (double)blocks || targetLoc == null) {
            return null;
        }
        return targetLoc;
    }

    public static <T extends Entity> T a(Entity entity, Iterable<T> entities, double distance) {
        TreeMap<Double, Entity> map = new TreeMap<Double, Entity>();
        for (Entity other : entities) {
            double v = entity.getLocation().distance(other.getLocation());
            if (v > distance) continue;
            map.put(v, other);
        }
        if (!map.isEmpty()) {
            return (T)((Entity)map.firstEntry().getValue());
        }
        return null;
    }

    public static <T extends Entity> List<T> a(Entity entity, Iterable<T> entities, double distance, int listRange) {
        TreeMap<Double, Entity> map = new TreeMap<Double, Entity>();
        ArrayList list = new ArrayList();
        for (Entity other : entities) {
            double v = entity.getLocation().distance(other.getLocation());
            if (v > distance) continue;
            map.put(v, other);
        }
        int i = 0;
        for (Map.Entry entry : map.entrySet()) {
            if (i == listRange) {
                return list;
            }
            list.add(entry.getValue());
            ++i;
        }
        return null;
    }
}

