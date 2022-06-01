/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package su.nightexpress.divineitems.gui;

import org.bukkit.inventory.ItemStack;
import su.nightexpress.divineitems.gui.ContentType;

public class GUIItem {
    private ContentType type;
    private ItemStack item;
    private int[] slot;

    public GUIItem(ContentType contentType, ItemStack itemStack, int[] arrn) {
        this.setType(contentType);
        this.setItem(itemStack);
        this.setSlots(arrn);
    }

    public GUIItem(GUIItem gUIItem) {
        this.setType(gUIItem.getType());
        this.setItem(new ItemStack(gUIItem.getItem()));
        this.setSlots(gUIItem.getSlots());
    }

    public ContentType getType() {
        return this.type;
    }

    public void setType(ContentType contentType) {
        this.type = contentType;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack itemStack) {
        this.item = itemStack;
    }

    public int[] getSlots() {
        return this.slot;
    }

    public void setSlots(int[] arrn) {
        this.slot = arrn;
    }
}

