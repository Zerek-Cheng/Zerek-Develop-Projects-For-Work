/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.util.io.BukkitObjectInputStream
 *  org.bukkit.util.io.BukkitObjectOutputStream
 *  org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
 */
package me.kevinnovak.inventorypages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class InventoryStringDeSerializer {
    public static String toBase64(Inventory inventory) {
        return InventoryStringDeSerializer.toBase64(inventory.getContents());
    }

    public static String toBase64(ItemStack itemStack) {
        ItemStack[] arritemStack = new ItemStack[]{itemStack};
        return InventoryStringDeSerializer.toBase64(arritemStack);
    }

    public static String toBase64(ItemStack[] arritemStack) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream((OutputStream)byteArrayOutputStream);
            bukkitObjectOutputStream.writeInt(arritemStack.length);
            ItemStack[] arritemStack2 = arritemStack;
            int n = arritemStack2.length;
            int n2 = 0;
            while (n2 < n) {
                ItemStack itemStack = arritemStack2[n2];
                bukkitObjectOutputStream.writeObject((Object)itemStack);
                ++n2;
            }
            bukkitObjectOutputStream.close();
            return Base64Coder.encodeLines((byte[])byteArrayOutputStream.toByteArray());
        }
        catch (Exception exception) {
            throw new IllegalStateException("Unable to save item stacks.", exception);
        }
    }

    public static Inventory inventoryFromBase64(String string) {
        ItemStack[] arritemStack = InventoryStringDeSerializer.stacksFromBase64(string);
        Inventory inventory = Bukkit.createInventory(null, (int)((int)Math.ceil((double)arritemStack.length / 9.0) * 9));
        int n = 0;
        while (n < arritemStack.length) {
            inventory.setItem(n, arritemStack[n]);
            ++n;
        }
        return inventory;
    }

    public static ItemStack[] stacksFromBase64(String string) {
        try {
            if (string == null || Base64Coder.decodeLines((String)string) == null) {
                return new ItemStack[0];
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines((String)string));
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream((InputStream)byteArrayInputStream);
            ItemStack[] arritemStack = new ItemStack[bukkitObjectInputStream.readInt()];
            int n = 0;
            while (n < arritemStack.length) {
                arritemStack[n] = (ItemStack)bukkitObjectInputStream.readObject();
                ++n;
            }
            bukkitObjectInputStream.close();
            return arritemStack;
        }
        catch (Exception classNotFoundException) {

        }
        return null;
    }
}

