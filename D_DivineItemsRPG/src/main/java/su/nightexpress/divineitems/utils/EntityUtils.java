/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package su.nightexpress.divineitems.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.hooks.Hook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityUtils {
    private static DivineItems plugin = DivineItems.instance;
    private static Set<Entity> set = new HashSet<Entity>();

    public static void add(Entity entity) {
        set.add(entity);
    }

    public static void remove(Entity entity) {
        set.remove((Object)entity);
    }

    public static Set<Entity> getSaved() {
        return set;
    }

    public static List<LivingEntity> getEnemies(Entity entity, double d, Player player) {
        ArrayList<LivingEntity> arrayList = new ArrayList<LivingEntity>();
        for (Entity entity2 : entity.getNearbyEntities(d, d, d)) {
            if (!(entity2 instanceof LivingEntity) || Hook.CITIZENS.isEnabled() && plugin.getHM().getCitizens().isNPC(entity2) || player.equals((Object)entity2) || Hook.WORLD_GUARD.isEnabled() && !plugin.getHM().getWorldGuard().canFights(entity2, (Entity)player)) continue;
            arrayList.add((LivingEntity)entity2);
        }
        return arrayList;
    }
}

