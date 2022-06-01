/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package yo;

import java.io.Closeable;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class ar_0 {
    public static void a(Metadatable target, a key) {
        target.removeMetadata(key.getMetadataKey(), (Plugin)think.rpgitems.Plugin.c);
    }

    public static boolean b(Metadatable target, a key) {
        return target.hasMetadata(key.getMetadataKey());
    }

    public static void a(Metadatable target, a key, Object value) {
        target.setMetadata(key.getMetadataKey(), (MetadataValue)new FixedMetadataValue((Plugin)think.rpgitems.Plugin.c, value));
    }

    public static void a(final Metadatable target, final a key, Object value, long lifetime, boolean removeRepeat) {
        if (removeRepeat) {
            target.removeMetadata(key.getMetadataKey(), (Plugin)think.rpgitems.Plugin.c);
        }
        target.setMetadata(key.getMetadataKey(), (MetadataValue)new FixedMetadataValue((Plugin)think.rpgitems.Plugin.c, value));
        Bukkit.getScheduler().runTaskLater((Plugin)think.rpgitems.Plugin.c, new Runnable(){

            @Override
            public void run() {
                target.removeMetadata(key.getMetadataKey(), (Plugin)think.rpgitems.Plugin.c);
            }
        }, lifetime);
    }

    public static MetadataValue c(Metadatable target, a key) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        List list = target.getMetadata(key.getMetadataKey());
        if (list != null) {
            for (MetadataValue value : list) {
                if (!value.getOwningPlugin().getName().equals(think.rpgitems.Plugin.c.getName())) continue;
                return value;
            }
        }
        return null;
    }

    public static enum a {
        RPGITEM_MAKE("RPGITEM_MAKE"),
        POTION_EFFECT("RPGITEM_POTION_EFFECT"),
        POTION_PURGE_EFFECT("RPGITEM_POTION_PURGE_EFFECT"),
        CANT_PICKUP("CANT_PICKUP"),
        POWER_THROW("RPGITEM_POWER_THROW"),
        POWER_BLOCK("RPGITEM_POWER_BLOCK"),
        POWER_BLOCKHEAL("RPGITEM_POWER_BLOCKHEAL"),
        POWER_BLOCKBUFF("RPGITEM_POWER_BLOCKBUFF"),
        RPGITEM_DAMAGE("RPGITEM_DAMAGE"),
        RPGITEM_BOUNCE_PROJECTILE("RPGITEM_BOUNCE_PROJECTILE");
        
        private final String metadataKey;

        private a(String metadataKey) {
            this.metadataKey = metadataKey;
        }

        public String getMetadataKey() {
            return this.metadataKey;
        }
    }

}

