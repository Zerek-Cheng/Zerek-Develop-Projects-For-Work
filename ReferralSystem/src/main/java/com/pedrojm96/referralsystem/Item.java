/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.ItemCommand;
import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Util;
import com.pedrojm96.referralsystem.menuClaim;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    private Material material;
    private Short data = null;
    private String name = null;
    private String permission = null;
    private Integer slot = 0;
    private Integer price;
    private List<String> lore;
    private List<String> commands;
    private Boolean kopen;

    public Item(Material material) {
        this.material = material;
    }

    public void setName(String string) {
        if (string == null || string.length() == 0) {
            this.name = null;
            return;
        }
        string = string.replaceAll("<price>", String.valueOf(this.getPrice()));
        this.name = Util.rColor(string);
    }

    public void setLore(List<String> list) {
        if (list == null || list.size() == 0) {
            this.lore = null;
            return;
        }
        this.lore = new ArrayList<String>();
        for (String string : list) {
            string = string.replaceAll("<price>", String.valueOf(this.getPrice()));
            string = Util.rColor(string);
            this.lore.add(string);
        }
    }

    public void setPrice(Integer n) {
        if (n == null || n == 0) {
            this.price = null;
            return;
        }
        this.price = n;
    }

    public int getPrice() {
        if (this.price == null) {
            return 0;
        }
        return this.price;
    }

    public void setSlot(Integer n) {
        if (n == null || n == 0) {
            this.slot = null;
            return;
        }
        if (n < 1) {
            n = 1;
        }
        if (n > menuClaim.getSlot(ReferralSystem.claim.getInt("settings-rows"))) {
            n = menuClaim.getSlot(ReferralSystem.claim.getInt("settings-rows"));
        }
        this.slot = n = Integer.valueOf(n - 1);
    }

    public void setPerm(String string) {
        this.permission = string == null || string.length() == 0 ? null : string;
    }

    public void setkOpen(Boolean bl) {
        this.kopen = bl == null ? null : bl;
    }

    public void setData(Short s) {
        if (s == null || s == 0) {
            this.data = 0;
            return;
        }
        this.data = s;
    }

    public void setCommands(List<String> list) {
        if (list == null || list.size() == 0) {
            this.commands = null;
            return;
        }
        this.commands = new ArrayList<String>();
        for (String string : list) {
            this.commands.add(string);
        }
    }

    public boolean hasPerm(Player player) {
        if (this.permission == null) {
            return true;
        }
        return player.hasPermission(this.permission);
    }

    public boolean isCommand() {
        if (this.commands == null) {
            return false;
        }
        return true;
    }

    public boolean kOpen() {
        if (this.kopen == null) {
            return false;
        }
        return this.kopen;
    }

    public int getSlot() {
        if (this.slot == null) {
            return 0;
        }
        return this.slot;
    }

    public ItemStack create(Player player) {
        ItemStack itemStack = new ItemStack(this.material, 1, this.data.shortValue());
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (this.name != null) {
            itemMeta.setDisplayName(this.name);
        }
        if (this.lore != null) {
            if (!this.hasPerm(player)) {
                String string = Util.rColor("&c\u2716 " + Messages.Claim_No_Permission());
                this.lore.add(string);
            }
            itemMeta.setLore(this.lore);
        } else if (!this.hasPerm(player)) {
            String string = Util.rColor("&c\u2716 " + Messages.Claim_No_Permission());
            this.lore.add(string);
            itemMeta.setLore(this.lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public boolean like(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (itemStack.getType() != this.material) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (this.name == null) {
            if (itemMeta.hasDisplayName()) {
                return false;
            }
        } else {
            if (!itemMeta.hasDisplayName()) {
                return false;
            }
            if (!itemMeta.getDisplayName().equals(this.name)) {
                return false;
            }
        }
        if (this.data != null && this.data.shortValue() != itemStack.getDurability()) {
            return false;
        }
        return true;
    }

    public void executeCommands(Player player) {
        if (this.commands != null && this.commands.size() > 0) {
            for (String string : this.commands) {
                ItemCommand itemCommand = new ItemCommand(player, string);
                itemCommand.execute();
            }
        }
    }
}

