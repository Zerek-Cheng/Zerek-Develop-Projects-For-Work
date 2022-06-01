/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem.subcommands;

import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.menuList;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmsList
extends SubCommand {
    private String perm;

    public cmsList(String string) {
        this.perm = string;
    }

    public cmsList() {
        this.perm = null;
    }

    @Override
    public String getPerm() {
        return this.perm;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arrstring) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.No_Console());
            return;
        }
        Player player = (Player)commandSender;
        menuList.open(player, 1);
    }
}

