package com.WeiBoss.bossshoptr.Commands;

import com.WeiBoss.bossshoptr.Main;
import com.WeiBoss.bossshoptr.Util.WeiUtil;
import org.bukkit.command.CommandSender;


public class HelpCommand extends WeiCommand {
    private static Main plugin = Main.instance;

    @Override
    public void perform(CommandSender weiCommandSender, String[] weiStrings) {
        plugin.message.getStringList("Message.Help").forEach(s -> weiCommandSender.sendMessage(WeiUtil.onReplace(s)));
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "bossshoptr.admin";
    }
}