/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  io.lumine.xikage.mythicmobs.MythicMobs
 *  io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper
 *  io.lumine.xikage.mythicmobs.drops.DropManager
 *  io.lumine.xikage.mythicmobs.mobs.ActiveMob
 *  io.lumine.xikage.mythicmobs.mobs.MobManager
 *  io.lumine.xikage.mythicmobs.mobs.MythicMob
 *  org.bukkit.entity.Entity
 */
package su.nightexpress.divineitems.hooks.external;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.drops.DropManager;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.entity.Entity;

public class MythicMobsHook {
    public boolean isMythicMob(Entity entity) {
        return MythicMobs.inst().getAPIHelper().isMythicMob(entity);
    }

    public String getMythicNameByEntity(Entity entity) {
        return MythicMobs.inst().getAPIHelper().getMythicMobInstance(entity).getType().getInternalName();
    }

    public MythicMob getMythicInstance(Entity entity) {
        return MythicMobs.inst().getAPIHelper().getMythicMobInstance(entity).getType();
    }

    public boolean isDropTable(String string) {
        if (MythicMobs.inst().getDropManager().getDropTable(string) != null && MythicMobs.inst().getDropManager().getDropTable(string).isPresent()) {
            return true;
        }
        return false;
    }

    public int getLevel(Entity entity) {
        return MythicMobs.inst().getAPIHelper().getMythicMobInstance(entity).getLevel();
    }

    public List<String> getTableDrops(String string) {
        try {
            Class<?> class_ = Class.forName("io.lumine.xikage.mythicmobs.drops.DropTable");
            Method method = class_.getMethod("getDropTable", String.class);
            Object object = method.invoke(string, new Object[0]);
            Field field = object.getClass().getField("strDropItems");
            field.setAccessible(true);
            List list = (List)field.get(object);
            return list;
        }
        catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException exception) {
            System.out.println("Error while getting MM Drops. It only works for MythicMobs 4.3.x or lower.");
            return new ArrayList<String>();
        }
    }

    public void setSkillDamage(Entity entity, double d) {
        if (!this.isMythicMob(entity)) {
            return;
        }
        ActiveMob activeMob = MythicMobs.inst().getMobManager().getMythicMobInstance(entity);
        activeMob.setLastDamageSkillAmount(d);
    }
}

