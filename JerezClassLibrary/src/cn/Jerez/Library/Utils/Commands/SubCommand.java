/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 */
package cn.Jerez.Library.Utils.Commands;

import cn.Jerez.Library.Annotation.Command.CommandAnntation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class SubCommand
        implements ISubCommand {
    protected CommandSender sender;
    protected String label;
    protected String[] args;
    protected Plugin plugin;

    @Override
    public CommandSender getSender() {
        return this.sender;
    }

    @Override
    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String[] getArgs() {
        return this.args;
    }

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void showHelp(CommandSender sender) {
        if (CommandCore.COMMAND_HELPS.containsKey(this.label)) {
            List<String> helpMsgs = CommandCore.COMMAND_HELPS.get(this.label);
            if (helpMsgs != null && helpMsgs.size() > 0) {
                for (String msg : helpMsgs) {
                    sender.sendMessage(msg);
                }
            }
            return;
        }
        Class<?> clazz = this.getClass();
        Method[] methods = clazz.getMethods();
        boolean notSubCommand = true;
        if (methods != null && methods.length > 0) {
            ArrayList<String> sendMsg = new ArrayList<String>();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(CommandAnntation.class)) continue;
                notSubCommand = false;
                CommandAnntation ca = method.getAnnotation(CommandAnntation.class);
                if (!CommandAnntationUtil.success(sender, ca)) continue;
                String invokeName = ca.cmd().equals("") ? method.getName() : ca.cmd();
                StringBuilder msgSb = new StringBuilder();
                msgSb.append("    §c" + this.label);
                msgSb.append(" §b<" + invokeName + ">");
                if (ca.args() != null && ca.args().length > 0) {
                    for (String arg : ca.args()) {
                        msgSb.append(" §a<" + arg + ">");
                    }
                }
                int len = 48 - msgSb.toString().length();
                for (int i = 0; i < len; ++i) {
                    msgSb.append(" ");
                }
                msgSb.append("§b" + ca.describe());
                sendMsg.add(msgSb.toString());
            }
            if (sendMsg.size() != 0) {
                sender.sendMessage("§f========================[§e" + this.plugin.getName() + "§f]========================");
                sender.sendMessage("");
                for (String msg : sendMsg) {
                    sender.sendMessage(msg);
                }
                sender.sendMessage("");
                sender.sendMessage("§f========================[§e" + this.plugin.getName() + "§f]========================");
            } else {
                sender.sendMessage("§f========================[§e" + this.plugin.getName() + "§f]========================");
                sender.sendMessage("");
                sender.sendMessage("");
                sender.sendMessage("          §b呀呼~没有权限查看任何命令呢....");
                sender.sendMessage("");
                sender.sendMessage("");
                sender.sendMessage("§f========================[§e" + this.plugin.getName() + "§f]========================");
            }
        }
        if (notSubCommand) {
            sender.sendMessage("§7[§d系統§7]: §a命令[" + this.label + "§a]存在! 但是未注册任何子命令!");
        }
    }
}

