// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.api;

import yo.by;
import think.rpgitems.item.RPGItem;
import org.bukkit.inventory.ItemStack;
import yo.a;

@a
public class RPGItems
{
    public static RPGItem toRPGItem(final ItemStack itemstack) {
        return by.a(itemstack);
    }
    
    public static RPGItem getRPGItemByName(final String name) {
        return by.c(name);
    }
    
    public static RPGItem getRPGItemById(final int id) {
        return by.a(id);
    }
}
