/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.black_ixx.playerpoints.PlayerPoints
 *  org.black_ixx.playerpoints.PlayerPointsAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.GiveItem;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Util;
import com.pedrojm96.referralsystem.Variable;
import java.util.UUID;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommand {
    private Type commandtype;
    private Player player;
    private String command;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$pedrojm96$referralsystem$ItemCommand$Type;

    public ItemCommand(Player player, String string) {
        this.player = player;
        string = string.trim();
        this.commandtype = Type.CONSOLE;
        if (string.startsWith("player:")) {
            this.command = string.substring(7);
            this.commandtype = Type.PLAYER;
        } else if (string.startsWith("op:")) {
            this.command = string.substring(3);
            this.commandtype = Type.OP;
        } else if (string.startsWith("broadcast:")) {
            this.command = string.substring(10);
            this.commandtype = Type.BROADCAST;
        } else if (string.startsWith("give:")) {
            this.command = string.substring(5);
            this.commandtype = Type.GIVE;
        } else if (string.startsWith("money:")) {
            this.command = string.substring(6);
            this.commandtype = Type.MONEY;
        } else if (string.startsWith("points:")) {
            this.command = string.substring(7);
            this.commandtype = Type.POINTS;
        } else if (string.startsWith("tell:")) {
            this.command = string.substring(5);
            this.commandtype = Type.TELL;
        } else if (string.startsWith("console:")) {
            this.command = string.substring(8);
            this.commandtype = Type.CONSOLE;
        } else if (string.contains(":")) {
            String[] arrstring = string.split(":");
            String string2 = arrstring[0];
            this.command = string.substring(string2.length());
            player.sendMessage("The type of command " + string2 + " is not valid please contact the administrator.");
        } else {
            this.command = string;
        }
        this.command = this.command.trim();
    }

    public void cmOP() {
        this.command = this.command.replaceAll("<player>", this.player.getName());
        if (this.player.isOp()) {
            this.player.chat("/" + this.command);
        } else {
            this.player.setOp(true);
            this.player.chat("/" + this.command);
            this.player.setOp(false);
        }
    }

    public void cmBROADCAST() {
        this.command = Variable.replaceVariables(this.command, this.player);
        this.command = Util.rColor(this.command);
        Bukkit.broadcastMessage((String)this.command);
    }

    public void cmGIVE() {
        if (this.command == null || this.command.length() == 0) {
            this.player.sendMessage("Item no valid contact an administrator");
            return;
        }
        if (this.command.contains(":")) {
            String[] arrstring = this.command.split(":");
            String string = arrstring[0];
            if (arrstring.length != 1 && arrstring.length != 0) {
                if (arrstring[1].contains(" ")) {
                    String[] arrstring2 = arrstring[1].split(" ");
                    String string2 = arrstring2[0];
                    String string3 = arrstring2[1];
                    GiveItem giveItem = new GiveItem(string, string2, string3);
                    giveItem.give(this.player);
                } else {
                    String string4 = arrstring[1];
                    GiveItem giveItem = new GiveItem(string, string4, "1");
                    giveItem.give(this.player);
                }
            } else {
                GiveItem giveItem = new GiveItem(string, "0", "1");
                giveItem.give(this.player);
            }
        } else if (this.command.contains(" ")) {
            String[] arrstring = this.command.split(" ");
            String string = arrstring[0];
            String string5 = arrstring[1];
            GiveItem giveItem = new GiveItem(string, "0", string5);
            giveItem.give(this.player);
        } else {
            GiveItem giveItem = new GiveItem(this.command, "0", "1");
            giveItem.give(this.player);
        }
    }

    public void cmMONEY() {
        if (!Util.isdouble(this.command)) {
            this.player.sendMessage("Invalid money amount: " + this.command);
            return;
        }
        double d = Double.parseDouble(this.command);
        if (d <= 0.0) {
            this.player.sendMessage("Invalid money amount: " + this.command);
            return;
        }
        if (ReferralSystem.econ) {
            EconomyResponse economyResponse = ReferralSystem.economy.depositPlayer(this.player.getName(), this.player.getWorld().getName(), d);
            if (!economyResponse.transactionSuccess()) {
                this.player.sendMessage("An unexpected error completing payment");
            }
        } else {
            this.player.sendMessage("Vault with a compatible economy plugin not found. Please inform the staff.");
        }
    }

    public void cmPOINTS() {
        if (!Util.isint(this.command)) {
            this.player.sendMessage("Invalid points amount: " + this.command);
            return;
        }
        int n = Integer.parseInt(this.command);
        if (n <= 0) {
            this.player.sendMessage("Invalid points amount: " + this.command);
            return;
        }
        if (ReferralSystem.poin) {
            if (!ReferralSystem.playerPoints.getAPI().give(this.player.getUniqueId(), n)) {
                this.player.sendMessage("An unexpected error completing payment");
            }
        } else {
            this.player.sendMessage("The plugin PlayerPoints was not found. Please inform the staff.");
        }
    }

    public void execute() {
        switch (ItemCommand.$SWITCH_TABLE$com$pedrojm96$referralsystem$ItemCommand$Type()[this.commandtype.ordinal()]) {
            case 1: {
                this.command = this.command.replaceAll("<player>", this.player.getName());
                this.player.chat("/" + this.command);
                break;
            }
            case 3: {
                this.cmOP();
                break;
            }
            case 4: {
                this.cmBROADCAST();
                break;
            }
            case 5: {
                this.cmGIVE();
                break;
            }
            case 8: {
                this.command = Variable.replaceVariables(this.command, this.player);
                this.command = Util.rColor(this.command);
                this.player.sendMessage(this.command);
                break;
            }
            case 6: {
                this.cmMONEY();
                break;
            }
            case 7: {
                this.cmPOINTS();
                break;
            }
            default: {
                this.command = this.command.replaceAll("<player>", this.player.getName());
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)this.command);
            }
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$pedrojm96$referralsystem$ItemCommand$Type() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$com$pedrojm96$referralsystem$ItemCommand$Type;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Type.values().length];
        try {
            arrn[Type.BROADCAST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Type.CONSOLE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Type.GIVE.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Type.MONEY.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Type.OP.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Type.PLAYER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Type.POINTS.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Type.TELL.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$com$pedrojm96$referralsystem$ItemCommand$Type = arrn;
        return $SWITCH_TABLE$com$pedrojm96$referralsystem$ItemCommand$Type;
    }

    private static enum Type {
        PLAYER,
        CONSOLE,
        OP,
        BROADCAST,
        GIVE,
        MONEY,
        POINTS,
        TELL;
        
        private Type(){

        }
        private Type(String string2, int n2) {
        }
    }

}

