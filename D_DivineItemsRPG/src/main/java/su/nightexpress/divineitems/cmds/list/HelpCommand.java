/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package su.nightexpress.divineitems.cmds.list;

import java.util.List;
import org.bukkit.command.CommandSender;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Lang;

public class HelpCommand
extends CommandBase {
    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        if (arrstring.length <= 1) {
            for (String string : Lang.Help_Main.getList()) {
                commandSender.sendMessage(string);
            }
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

