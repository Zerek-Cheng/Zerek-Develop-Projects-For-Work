/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.util.io.BukkitObjectInputStream
 *  org.bukkit.util.io.BukkitObjectOutputStream
 */
package info.TrenTech.EasyKits.Kit;

import info.TrenTech.EasyKits.Events.KitCreateEvent;
import info.TrenTech.EasyKits.SQL.SQLKits;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.sql.SQLException;

public class Kit {
    private String kitName;
    private int limit;
    private String cooldown;
    private double price;

    public Kit(String kitName) {
        this.kitName = kitName;
        if (!this.exists()) {
            FileConfiguration config = Utils.getConfig();
            this.cooldown = config.getString("Config.Default-Cooldown");
            this.price = config.getDouble("Config.Default-Price");
            this.limit = config.getInt("Config.Default-Kit-Limit");
        } else {
            this.cooldown = SQLKits.getKitCooldown(kitName);
            this.price = SQLKits.getKitPrice(kitName);
            this.limit = SQLKits.getKitLimit(kitName);
        }
    }

    public String getName() {
        return this.kitName;
    }

    public ItemStack[] getInventory() {
        byte[] byteInv = SQLKits.getKitInventory(this.kitName.toLowerCase());
        ByteArrayInputStream ByteArIS = new ByteArrayInputStream(byteInv);
        Object inv = null;
        try {
            BukkitObjectInputStream invObjIS = new BukkitObjectInputStream((InputStream)ByteArIS);
            inv = invObjIS.readObject();
            invObjIS.close();
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return (ItemStack[])inv;
    }

    public ItemStack[] getArmor() {
        byte[] byteArm = SQLKits.getKitArmor(this.kitName.toLowerCase());
        ByteArrayInputStream ByteArIS = new ByteArrayInputStream(byteArm);
        Object arm = null;
        try {
            BukkitObjectInputStream armObjIS = new BukkitObjectInputStream((InputStream)ByteArIS);
            arm = armObjIS.readObject();
            armObjIS.close();
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return (ItemStack[])arm;
    }

    public int getLimit() {
        return this.limit;
    }

    public double getPrice() {
        return this.price;
    }

    public String getCooldown() {
        return this.cooldown;
    }

    public void create(Player creator, ItemStack[] inventory, ItemStack[] armor) throws SQLException {
        KitCreateEvent event = new KitCreateEvent(creator, this.kitName);
        Bukkit.getServer().getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            ByteArrayOutputStream invByteArray = new ByteArrayOutputStream();
            try {
                BukkitObjectOutputStream invObjOS = new BukkitObjectOutputStream((OutputStream)invByteArray);
                invObjOS.writeObject((Object)inventory);
                invObjOS.close();
            }
            catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
            ByteArrayOutputStream armByteArray = new ByteArrayOutputStream();
            try {
                BukkitObjectOutputStream armObjOS = new BukkitObjectOutputStream((OutputStream)armByteArray);
                armObjOS.writeObject((Object)armor);
                armObjOS.close();
            }
            catch (IOException ioexception2) {
                ioexception2.printStackTrace();
            }
            SQLKits.createKit(this.kitName.toLowerCase(), invByteArray.toByteArray(), armByteArray.toByteArray(), this.price, this.cooldown, this.limit, creator.getUniqueId().toString());
        }
    }

    public void setInventory(ItemStack[] inventory) {
        ByteArrayOutputStream invByteArray = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream invObjOS = new BukkitObjectOutputStream((OutputStream)invByteArray);
            invObjOS.writeObject((Object)inventory);
            invObjOS.close();
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        SQLKits.setInventory(this.kitName, invByteArray.toByteArray());
    }

    public void setArmor(ItemStack[] armor) {
        ByteArrayOutputStream invByteArray = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream invObjOS = new BukkitObjectOutputStream((OutputStream)invByteArray);
            invObjOS.writeObject((Object)armor);
            invObjOS.close();
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        SQLKits.setArmor(this.kitName, invByteArray.toByteArray());
    }

    public void setLimit(int limit) {
        SQLKits.setKitLimit(this.kitName, limit);
        this.limit = limit;
    }

    public void setPrice(double price) {
        SQLKits.setKitPrice(this.kitName, price);
        this.price = price;
    }

    public void setCooldown(String cooldown) {
        SQLKits.setKitCooldown(this.kitName, cooldown);
        this.cooldown = cooldown;
    }

    public void remove() throws SQLException {
        SQLKits.deleteKit(this.kitName);
        try {
            this.finalize();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public boolean exists() {
        return SQLKits.kitExist(this.kitName);
    }
}

