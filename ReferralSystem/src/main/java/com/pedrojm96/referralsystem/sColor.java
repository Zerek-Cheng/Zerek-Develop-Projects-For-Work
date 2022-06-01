/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.Util;
import com.pedrojm96.referralsystem.Variable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sColor {
    private CommandSender s;
    private Player p;

    public sColor(CommandSender commandSender) {
        this.s = commandSender;
    }

    public sColor(Player player) {
        this.p = player;
    }

    public void sendMessage(String string) {
        if (this.p != null) {
            if (string == null || string.length() == 0) {
                this.p.sendMessage(string);
            } else {
                this.p.sendMessage(Util.rColor(Variable.replaceVariables(string, this.p)));
            }
            return;
        }
        if (this.s != null) {
            if (string == null || string.length() == 0) {
                this.s.sendMessage(string);
            } else {
                this.s.sendMessage(Util.rColor(Variable.replaceVariables(string, this.s)));
            }
        }
    }
}

