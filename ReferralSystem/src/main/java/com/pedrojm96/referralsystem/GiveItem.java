/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.Util;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GiveItem {
    private String ma;
    private Short da;
    private int ca;

    public GiveItem(String string, String string2, String string3) {
        this.ma = string.trim();
        this.da = string2 == null || string2.length() == 0 || !Util.isint(string2.trim()) ? Short.valueOf((short)0) : Short.valueOf(string2.trim());
        this.ca = string3 == null || string3.length() == 0 || !Util.isint(string3.trim()) ? 1 : Integer.valueOf(string3.trim());
    }

    public void give(Player player) {
        if (this.ma == null || this.ma.length() == 0 || Material.getMaterial((String)this.ma) == null) {
            player.sendMessage("Material in the file claim.yml is invalid");
            return;
        }
        Material material = Material.getMaterial((String)this.ma);
        ItemStack itemStack = new ItemStack(material, this.ca, this.da.shortValue());
        player.getInventory().addItem(new ItemStack[]{new ItemStack(itemStack)});
    }
}

