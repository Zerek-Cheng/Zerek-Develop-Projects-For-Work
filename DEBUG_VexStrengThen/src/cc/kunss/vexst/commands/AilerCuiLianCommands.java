/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package cc.kunss.vexst.commands;

import cc.kunss.vexst.utils.GuiFile;
import cc.kunss.vexst.utils.Message;
import cc.kunss.vexst.utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AilerCuiLianCommands
implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("vst") && args.length == 0 && commandSender.hasPermission("vexstreng.cmd.cl")) {
            commandSender.sendMessage("\u00a7f[\u00a7aVexStrengThen\u00a7f]: \u00a7c/vst open \u00a7a\u6253\u5f00GUI\u754c\u9762");
            commandSender.sendMessage("\u00a7f[\u00a7aVexStrengThen\u00a7f]: \u00a7c/vst open \u00a7a\u91cd\u8f7d\u914d\u7f6e\u6587\u4ef6");
            return true;
        }
        if (args[0].equalsIgnoreCase("open") && commandSender.hasPermission("vexstreng.cmd.open")) {
            GuiFile.opengui((Player)commandSender);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload") && commandSender.hasPermission("vexstreng.cmd.reload")) {
            Methods.Default();
            commandSender.sendMessage(Message.getMessageString("reloadconfig"));
        }
        return false;
    }
}

