/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package su.nightexpress.divineitems.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import su.nightexpress.divineitems.config.Lang;

public abstract class CommandBase {
    private boolean isPlayer;

    public final void execute(CommandSender commandSender, String[] arrstring) {
        this.isPlayer = commandSender instanceof Player;
        if (this.playersOnly() && !this.isPlayer) {
            return;
        }
        if (this.getPermission() != null && !commandSender.hasPermission(this.getPermission())) {
            commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_NoPerm.toMsg());
            return;
        }
        this.perform(commandSender, arrstring);
    }

    boolean isPlayer() {
        return this.isPlayer;
    }

    public abstract void perform(CommandSender var1, String[] var2);

    public abstract String getPermission();

    public abstract boolean playersOnly();
}

