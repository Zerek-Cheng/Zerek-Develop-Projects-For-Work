/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.LivingEntity
 */
package yo;

import java.io.Closeable;
import org.bukkit.entity.LivingEntity;
import think.rpgitems.item.RPGItem;

public class bx_0 {
    private final LivingEntity a;
    private final RPGItem b;

    public bx_0(LivingEntity owner, RPGItem rpgitem) {
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
        this.a = owner;
        this.b = rpgitem;
    }

    public LivingEntity a() {
        return this.a;
    }

    public RPGItem b() {
        return this.b;
    }
}

