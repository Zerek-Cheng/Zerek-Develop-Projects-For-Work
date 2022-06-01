/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package think.rpgitems.api;

import org.bukkit.inventory.ItemStack;
import think.rpgitems.item.RPGItem;
import yo.a_0;
import yo.by_0;

@a_0
public class RPGItems {
    public static RPGItem toRPGItem(ItemStack itemstack) {
        return by_0.a(itemstack);
    }

    public static RPGItem getRPGItemByName(String name) {
        return by_0.c(name);
    }

    public static RPGItem getRPGItemById(int id) {
        return by_0.a(id);
    }
}

