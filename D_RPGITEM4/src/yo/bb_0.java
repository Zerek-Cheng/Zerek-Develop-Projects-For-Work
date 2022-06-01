/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 */
package yo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import think.rpgitems.item.RPGItem;
import yo.ao_0;
import yo.by_0;

public class bb_0
extends InventoryView {
    private Player a;
    private InventoryView b;
    private Inventory c;
    private Inventory d;
    private String e;

    public bb_0(Player player, InventoryView inventoryView) {
        this.e = ao_0.a(player);
        this.a = player;
        this.c = inventoryView.getTopInventory();
        this.b = inventoryView;
        this.d = Bukkit.createInventory((InventoryHolder)this.c.getHolder(), (int)this.c.getSize(), (String)this.c.getTitle());
        this.b();
    }

    public InventoryView a() {
        return this.b;
    }

    public void b() {
        this.d.setContents(this.c.getContents());
        for (ItemStack item : this.d) {
            RPGItem rItem = by_0.a(item);
            if (rItem == null) continue;
            item.setType(rItem.p());
            ItemMeta meta = rItem.b(this.e);
            if (!(meta instanceof LeatherArmorMeta) && rItem.p().isBlock()) {
                item.setDurability(rItem.o());
            }
            ArrayList<String> lores = new ArrayList<String>(RPGItem.a(meta.getLore()));
            lores.addAll(RPGItem.a(rItem, item.getItemMeta().getLore()));
            meta.setLore(lores);
            Map<Enchantment, Integer> enchs = RPGItem.a(rItem, item.getItemMeta());
            item.setItemMeta(meta);
            if (enchs.isEmpty()) continue;
            item.addUnsafeEnchantments(enchs);
        }
        this.d.setContents(this.d.getContents());
    }

    public Inventory c() {
        return this.c;
    }

    public Inventory getBottomInventory() {
        return this.a.getInventory();
    }

    public HumanEntity getPlayer() {
        return this.a;
    }

    public Inventory getTopInventory() {
        return this.d;
    }

    public InventoryType getType() {
        return InventoryType.CHEST;
    }

    public void d() {
        this.c.setContents(this.d.getContents());
    }
}

