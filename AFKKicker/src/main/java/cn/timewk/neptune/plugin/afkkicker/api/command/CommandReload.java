/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package cn.timewk.neptune.plugin.afkkicker.api.command;

import cn.timewk.neptune.plugin.afkkicker.AFKKicker;
import cn.timewk.neptune.plugin.afkkicker.api.API;
import cn.timewk.neptune.plugin.afkkicker.api.command.CommandBase;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class CommandReload
        extends CommandBase {
    @Override
    public boolean run(CommandSender sender, String[] args) {
        if (args.length > 0 && args[0].equals("reload")) {
            if (sender.hasPermission("afkkicker.reload")) {
                this.getServer().getPluginManager().disablePlugin((Plugin)this.getPlugin());
                this.getServer().getPluginManager().enablePlugin((Plugin)this.getPlugin());
                this.getAPI().info("插件重载完成!");
            } else {
                sender.sendMessage("§c缺少权限afkkicker.reload");
            }
        } else {
            sender.sendMessage("§c/afkkicker reload");
        }
        return true;
    }
}

