/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 */
package cn.timewk.neptune.plugin.afkkicker.api.command;

import cn.timewk.neptune.plugin.afkkicker.api.util.PluginGetter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandBase
        implements CommandExecutor,
        PluginGetter {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return this.run(sender, args);
    }

    public abstract boolean run(CommandSender var1, String[] var2);

    public boolean checkPermission(CommandSender sender, String permission) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(String.valueOf(this.getErrorHead()) + "要使用该指令你需要" + permission + "权限");
            return false;
        }
        return true;
    }
}

