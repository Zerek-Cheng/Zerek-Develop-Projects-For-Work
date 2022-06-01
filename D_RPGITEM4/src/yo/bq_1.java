/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package yo;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import yo.ar_0;
import yo.bo_1;

public class bq_1
extends bo_1 {
    @Override
    public int a(Player player, Entity target, ar_0.a metadataKey) {
        return super.a(player, target, ar_0.a.POWER_BLOCK);
    }

    @Override
    public String e() {
        return "blockrpgitem";
    }
}

