/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package yo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.item.RPGItem;
import yo.bz_1;

public class ba_0 {
    public final Collection<ItemStack> a;
    public final HashMap<RPGItem, ItemStack> b = new HashMap();
    public final Collection<RPGItem> c = new ArrayList<RPGItem>();
    public final Collection<RPGItem> d = new ArrayList<RPGItem>();
    public final Collection<RPGItem> e = new ArrayList<RPGItem>();
    public final HashMap<bz_1, Collection<RPGItem>> f = new HashMap();

    public ba_0(Collection<ItemStack> originalItems) {
        this.a = originalItems;
    }
}

