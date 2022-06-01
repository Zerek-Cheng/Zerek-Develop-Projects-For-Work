/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package su.nightexpress.divineitems.cmds.list;

import org.bukkit.command.CommandSender;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Lang;

public class ReloadCommand
extends CommandBase {
    private DivineItems plugin;

    public ReloadCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        if (arrstring.length != 2) {
            commandSender.sendMessage("\u00a7cUsage: \u00a7f/di reload <full/cfg>");
            return;
        }
        if (arrstring[1].equalsIgnoreCase("full")) {
            this.plugin.reloadFull();
            commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Reload_Full.toMsg());
        } else if (arrstring[1].equalsIgnoreCase("cfg")) {
            this.plugin.reloadCfg();
            commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Reload_Cfg.toMsg());
        } else {
            commandSender.sendMessage("\u00a7cUsage: \u00a7f/di reload <full/cfg>");
            return;
        }
    }

    @Override
    public String getPermission() {
        return "divineitems.admin";
    }

    @Override
    public boolean playersOnly() {
        return false;
    }
}

