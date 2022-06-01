/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.bukkit.Bukkit
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package su.nightexpress.divineitems.gui;

import java.util.Collection;
import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import su.nightexpress.divineitems.gui.ContentType;
import su.nightexpress.divineitems.gui.GUIItem;
import su.nightexpress.divineitems.gui.GUIType;

public class GUI {
    private GUIType type;
    private String title;
    private int size;
    private HashMap<ContentType, GUIItem> items;

    public GUI(GUIType gUIType, String string, int n, HashMap<ContentType, GUIItem> hashMap) {
        this.setType(gUIType);
        this.setTitle(string);
        this.setSize(n);
        this.setItems(hashMap);
    }

    public GUI(GUI gUI) {
        this.setType(gUI.getType());
        this.setTitle(gUI.getTitle());
        this.setSize(gUI.getSize());
        HashMap<ContentType, GUIItem> hashMap = new HashMap<ContentType, GUIItem>();
        for (GUIItem gUIItem : gUI.getItems().values()) {
            hashMap.put(gUIItem.getType(), new GUIItem(gUIItem));
        }
        this.setItems(hashMap);
    }

    public GUIType getType() {
        return this.type;
    }

    public void setType(GUIType gUIType) {
        this.type = gUIType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String string) {
        this.title = string;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int n) {
        this.size = n;
    }

    public HashMap<ContentType, GUIItem> getItems() {
        return this.items;
    }

    public void setItems(HashMap<ContentType, GUIItem> hashMap) {
        this.items = hashMap;
    }

    public Inventory build() {
        Inventory inventory = Bukkit.createInventory(null, (int)this.getSize(), (String)ChatColor.translateAlternateColorCodes((char)'&', (String)this.getTitle()));
        for (GUIItem gUIItem : this.getItems().values()) {
            ItemStack itemStack = new ItemStack(gUIItem.getItem());
            int[] arrn = gUIItem.getSlots();
            int n = arrn.length;
            int n2 = 0;
            while (n2 < n) {
                int n3 = arrn[n2];
                inventory.setItem(n3, itemStack);
                ++n2;
            }
        }
        return inventory;
    }
}

