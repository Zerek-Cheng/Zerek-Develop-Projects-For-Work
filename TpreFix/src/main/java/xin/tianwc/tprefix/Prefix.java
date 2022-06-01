/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package xin.tianwc.tprefix;

import lombok.Data;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Data
public class Prefix {
    private String permission;
    private String color;
    private String pre;
    private Double damage;
    private Double armor;
    private Double health;
    private Double speed;
    private int effect;
    private List<String> lores;

    public static boolean isPrefix(String pre) {
        if (Main.INSTANCE.getConfig().getKeys(false).contains(pre)) {
            return true;
        }
        return false;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(337, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.pre);
        ArrayList<String> itemLore = new ArrayList<String>();
        itemLore.add("§d点击佩戴这个称号");/*
        itemLore.add("§f攻击加成：§a" + this.damage);
        itemLore.add("§f防御加成：§a" + this.armor);
        itemLore.add("§f生命加成：§a" + this.health);
        itemLore.add("§f速度加成：§a" + this.speed);*/
        itemLore.addAll(this.getLores());
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        return item;
    }

    public Prefix(String pre) {
        this.permission = Main.INSTANCE.getConfig().getString(pre + ".permission");
        this.color = Main.INSTANCE.getConfig().getString(pre + ".color");
        this.pre = pre;
        this.lores = Main.INSTANCE.getConfig().getStringList(pre + ".lore");
    }
}

