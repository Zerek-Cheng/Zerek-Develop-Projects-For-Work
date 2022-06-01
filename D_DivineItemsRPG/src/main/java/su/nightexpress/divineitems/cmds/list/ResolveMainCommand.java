/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package su.nightexpress.divineitems.cmds.list;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.resolve.ResolveManager;

public class ResolveMainCommand
extends CommandBase {
    private DivineItems plugin;

    public ResolveMainCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        Player player = null;
        if (arrstring.length == 2) {
            player = Bukkit.getPlayer((String)arrstring[1]);
        } else if (commandSender instanceof Player) {
            player = (Player)commandSender;
        }
        if (player == null) {
            commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
            return;
        }
        this.plugin.getMM().getResolveManager().openResolveGUI(player);
    }

    @Override
    public String getPermission() {
        return "divineitems.resolve";
    }

    @Override
    public boolean playersOnly() {
        return false;
    }
}

