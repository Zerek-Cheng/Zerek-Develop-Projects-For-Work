/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package su.nightexpress.divineitems.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import su.nightexpress.divineitems.nms.NBTAttribute;

public interface NMS {
    public ItemStack setNBTAtt(ItemStack var1, NBTAttribute var2, double var3);

    public void sendActionBar(Player var1, String var2);

    public void sendTitles(Player var1, String var2, String var3, int var4, int var5, int var6);
}

