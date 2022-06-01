/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.pedrojm96.referralsystem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class itemPlayer {
    List<String> lore = new ArrayList<String>();
    String name;
    int po;

    public itemPlayer(String string, int n, int n2, boolean bl, int n3) {
        this.po = n3;
        this.name = Util.rColor(Messages.MenuList_Name().replaceAll("<top>", String.valueOf(n3)).replaceAll("<player>", string));
        for (String string2 : Messages.MenuList_Lore()) {
            this.lore.add(Util.rColor(string2.replaceAll("<referral>", String.valueOf(n2)).replaceAll("<points>", String.valueOf(n))));
        }
    }

    public ItemStack create() {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (this.name != null) {
            itemMeta.setDisplayName(this.name);
        }
        if (this.lore != null) {
            itemMeta.setLore(this.lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public String getString() {
        return this.name;
    }

    public int getPo() {
        return this.po - 1;
    }
}

