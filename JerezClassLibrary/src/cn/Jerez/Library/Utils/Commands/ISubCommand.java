/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 */
package cn.Jerez.Library.Utils.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public interface ISubCommand {
    public void setLabel(String var1);

    public String getLabel();

    public void setArgs(String[] var1);

    public String[] getArgs();

    public void setSender(CommandSender var1);

    public CommandSender getSender();

    public void setPlugin(Plugin var1);

    public Plugin getPlugin();

    public void showHelp(CommandSender var1);
}

