/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package cn.Jerez.Library.Utils.Commands;

import cn.Jerez.Library.Annotation.Command.CommandAnntation;
import cn.Jerez.Library.Utils.SimpleLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.Map;

public class CommandExecute
        extends Command {
    protected CommandExecute(String name) {
        super(name);
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (CommandCore.HOOK_SUB_COMMANDS.containsKey(label)) {
            boolean errorCmd;
            ISubCommand subCommand = CommandCore.HOOK_SUB_COMMANDS.get(label);
            subCommand.setSender(sender);
            subCommand.setLabel(label);
            subCommand.setArgs(args);
            Class<?> clazz = subCommand.getClass();
            boolean bl = errorCmd = args == null || args.length == 0;
            if (!errorCmd) {
                Map<String, String> labelMap = CommandCore.COMMAND_ARGS.get(label);
                if (labelMap != null && labelMap.size() != 0) {
                    String invokeMethodName = labelMap.get(args[0]);
                    if (invokeMethodName != null) {
                        try {
                            Method invokeMethod = clazz.getMethod(invokeMethodName, new Class[0]);
                            CommandAnntation ca = invokeMethod.getAnnotation(CommandAnntation.class);
                            if (CommandAnntationUtil.success(sender, ca)) {
                                invokeMethod.setAccessible(true);
                                invokeMethod.invoke(subCommand, new Object[0]);
                            }
                            SimpleLogger.sendDef(sender, "对不起!您的权限不足! 无法完成此操作!");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        errorCmd = true;
                    }
                } else {
                    errorCmd = true;
                }
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
                subCommand.showHelp(sender);
                return true;
            }
            if (label.equalsIgnoreCase("fj") && args.length == 0) {
                execute(sender, label, new String[]{"open"});
                return true;
            }
            if (errorCmd) {
                subCommand.showHelp(sender);
                return true;
            }
        }
        return false;
    }
}

