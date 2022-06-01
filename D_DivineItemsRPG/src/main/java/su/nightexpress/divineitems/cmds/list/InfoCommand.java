/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.PluginDescriptionFile
 */
package su.nightexpress.divineitems.cmds.list;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.cmds.CommandBase;

public class InfoCommand
extends CommandBase {
    private DivineItems plugin;

    public InfoCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        commandSender.sendMessage("\u00a78\u00a7m--------\u00a78\u00a7l[ \u00a7fDivine Items RPG - Info \u00a78\u00a7l]\u00a78\u00a7m--------");
        commandSender.sendMessage("\u00a7r");
        commandSender.sendMessage("\u00a78\u00a7l[\u00a7aAbout\u00a78\u00a7l]");
        commandSender.sendMessage("\u00a7a> \u00a77Price: \u00a7a10.00 USD\u00a77.");
        commandSender.sendMessage("\u00a7a> \u00a77Created by: \u00a7aNightExpress");
        commandSender.sendMessage("\u00a7a> \u00a77Version: \u00a7a" + this.plugin.getDescription().getVersion() + " (Export)");
        commandSender.sendMessage("\u00a7a> \u00a77Licensed to: \u00a7a235309");
        commandSender.sendMessage("\u00a7a> \u00a77Type \u00a7a/di help \u00a77for help.");
        commandSender.sendMessage("\u00a7r");
        commandSender.sendMessage("\u00a78\u00a7l[\u00a7eTerms of Service\u00a78\u00a7l]");
        commandSender.sendMessage("\u00a7e> \u00a77Redistributing: \u00a7c\u00a7lDisallowed\u00a77.");
        commandSender.sendMessage("\u00a7e> \u00a77Refunds: \u00a7c\u00a7lDisallowed\u00a77.");
        commandSender.sendMessage("\u00a7e> \u00a77Decompile/Modify code: \u00a7a\u00a7lAllowed\u00a77.");
        commandSender.sendMessage("\u00a7r");
        commandSender.sendMessage("\u00a78\u00a7l[\u00a7dSupport/Bug Report/Suggestions\u00a78\u00a7l]");
        commandSender.sendMessage("\u00a7d> \u00a77SpigotMC: \u00a7dPM \u00a77or \u00a7dForum thread\u00a77. \u00a7a[Fast]");
        commandSender.sendMessage("\u00a7d> \u00a77ICQ: \u00a7d9-211-317\u00a77. \u00a7a[Fast]");
        commandSender.sendMessage("\u00a7r");
        commandSender.sendMessage("\u00a78\u00a7m------------------------------------");
    }

    @Override
    public String getPermission() {
        return "divineitems.user";
    }

    @Override
    public boolean playersOnly() {
        return false;
    }
}

