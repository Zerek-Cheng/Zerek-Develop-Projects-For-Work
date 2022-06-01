package com._0myun.minecraft.banitemshower;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubBanItem {
    public BanItem parent;
    public int damage;
    public String currentName;
    public boolean confiscate;
    public String flags;

    public SubBanItem(BanItem parent, int damage, ConfigurationSection config) {
        this.parent = parent;
        this.damage = damage;
        this.currentName = config.getString("CurrentName");
        this.confiscate = config.isSet("Confiscate") ? config.getBoolean("Confiscate") : this.parent.confiscate;
        this.flags = config.getString("Flags");
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(parent.getCurrentId(), 1, (short) damage);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta==null) return item;

        itemMeta.setDisplayName(this.currentName);

        List<String> lores = new ArrayList<>();
        lores.add(Main.INSTANCE.lang.getString("lang1") +
                (this.confiscate ? Main.INSTANCE.lang.getString("lang2") : Main.INSTANCE.lang.getString("lang3")));
        if (this.flags != null && !this.flags.isEmpty()) {
            lores.add(Main.INSTANCE.lang.getString("lang4")
                    + Main.INSTANCE.lang.getString(this.flags)
            );
        }
        lores.add(Main.INSTANCE.lang.getString("lang5") +
                parent.getCurrentId() + ":" + (this.damage == -1 ? "*" : this.damage)
        );
        lores.add(Main.INSTANCE.lang.getString("lang6") +
                this.parent.getReason()
        );

        itemMeta.setLore(lores);

        item.setItemMeta(itemMeta);
        return item;
    }
}
