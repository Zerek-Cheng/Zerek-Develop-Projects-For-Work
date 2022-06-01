/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package info.TrenTech.EasyKits.Utils;

import info.TrenTech.EasyKits.EasyKits;
import org.bukkit.ChatColor;

public class Notifications {
    private String type;
    private String kitName;
    private String playerName;
    private double price;
    private String cooldown;
    private int limit;

    public Notifications() {
    }

    public Notifications(String type, String kitName, String playerName, double price, String cooldown, int limit) {
        this.type = type;
        this.kitName = kitName;
        this.playerName = playerName;
        this.price = price;
        this.cooldown = cooldown;
        this.limit = limit;
    }

    public void getMessages() {
        EasyKits.messages.put("Permission-Denied", Utils.getConfig().getString("Messages.Permission-Denied"));
        EasyKits.messages.put("Kit-Created", Utils.getConfig().getString("Messages.Kit-Created"));
        EasyKits.messages.put("Kit-Deleted", Utils.getConfig().getString("Messages.Kit-Deleted"));
        EasyKits.messages.put("Kit-Obtained", Utils.getConfig().getString("Messages.Kit-Obtained"));
        EasyKits.messages.put("Kit-Sent", Utils.getConfig().getString("Messages.Kit-Sent"));
        EasyKits.messages.put("Kit-Received", Utils.getConfig().getString("Messages.Kit-Received"));
        EasyKits.messages.put("Kit-Exist", Utils.getConfig().getString("Messages.Kit-Exist"));
        EasyKits.messages.put("Kit-Not-Exist", Utils.getConfig().getString("Messages.Kit-Not-Exist"));
        EasyKits.messages.put("Get-Kit-Limit", Utils.getConfig().getString("Messages.Get-Kit-Limit"));
        EasyKits.messages.put("Set-Kit-Limit", Utils.getConfig().getString("Messages.Set-Kit-Limit"));
        EasyKits.messages.put("Reset-Kit-Limit", Utils.getConfig().getString("Messages.Reset-Kit-Limit"));
        EasyKits.messages.put("Get-Cooldown", Utils.getConfig().getString("Messages.Get-Cooldown"));
        EasyKits.messages.put("Set-Cooldown", Utils.getConfig().getString("Messages.Set-Cooldown"));
        EasyKits.messages.put("Reset-Cooldown", Utils.getConfig().getString("Messages.Reset-Cooldown"));
        EasyKits.messages.put("Get-Price", Utils.getConfig().getString("Messages.Get-Price"));
        EasyKits.messages.put("Set-Price", Utils.getConfig().getString("Messages.Set-Price"));
        EasyKits.messages.put("Insufficient-Funds", Utils.getConfig().getString("Messages.Insufficient-Funds"));
        EasyKits.messages.put("Inventory-Space", Utils.getConfig().getString("Messages.Inventory-Space"));
        EasyKits.messages.put("New-Player-Kit", Utils.getConfig().getString("Messages.New-Player-Kit"));
        EasyKits.messages.put("Kit-Book-Full", Utils.getConfig().getString("Messages.Kit-Book-Full"));
        EasyKits.messages.put("Not-Player", Utils.getConfig().getString("Messages.Not-Player"));
        EasyKits.messages.put("No-Player", Utils.getConfig().getString("Messages.No-Player"));
        EasyKits.messages.put("Invalid-Number", Utils.getConfig().getString("Messages.Invalid-Number"));
        EasyKits.messages.put("Invalid-Argument", Utils.getConfig().getString("Messages.Invalid-Argument"));
        EasyKits.messages.put("DB-Fail", Utils.getConfig().getString("Messages.DB-Fail"));
    }

    public String getMessage() {
        String msg = null;
        if (EasyKits.messages.get(this.type) != null) {
            msg = EasyKits.messages.get(this.type);
            if (msg.contains("%K") && this.kitName != null) {
                msg = msg.replaceAll("%K", this.kitName);
            }
            if (msg.contains("%M") && this.price != 0.0) {
                msg = msg.replaceAll("%M", Double.toString(this.price));
            }
            if (msg.contains("%T") && this.cooldown != null) {
                msg = msg.replaceAll("%T", this.cooldown);
            }
            if (msg.contains("%P") && this.playerName != null) {
                msg = msg.replaceAll("%P", this.playerName);
            }
            if (msg.contains("%L") && this.limit != 0) {
                msg = msg.replaceAll("%L", Integer.toString(this.limit));
            }
            String message = ChatColor.translateAlternateColorCodes((char)'&', (String)msg);
            return message;
        }
        throw new NullPointerException("Message Missing from Config!");
    }
}

