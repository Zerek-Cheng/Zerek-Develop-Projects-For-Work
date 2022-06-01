package com.WeiBoss.bossshoptr.Commands;

import com.WeiBoss.bossshoptr.File.Config;
import com.WeiBoss.bossshoptr.Main;
import com.WeiBoss.bossshoptr.Util.WeiUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand extends WeiCommand {
    private static Main plugin = Main.instance;

    @Override
    public void perform(CommandSender weiCommandSender, String[] weiStrings) {
        if (weiStrings.length == 3) {
            if (!WeiUtil.isNumber(weiStrings[2])) {
                weiCommandSender.sendMessage(Config.Prefix + "§c无效的数值");
                return;
            }
            Player admin = (Player) weiCommandSender;
            Player p = Bukkit.getPlayer(weiStrings[1]);
            int day = Integer.valueOf(weiStrings[2]);
            plugin.mainGUI.open(admin, p, null, day, 1);
        }
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String getPermission() {
        return "bossshoptr.admin";
    }
}