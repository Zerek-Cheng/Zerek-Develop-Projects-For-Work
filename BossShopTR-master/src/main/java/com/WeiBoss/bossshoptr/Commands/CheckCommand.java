package com.WeiBoss.bossshoptr.Commands;

import com.WeiBoss.bossshoptr.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class CheckCommand extends WeiCommand {
    private static Main plugin = Main.instance;

    @Override
    public void perform(CommandSender weiCommandSender, String[] weiStrings) {
        if (weiStrings.length == 2) {
            Player admin = (Player) weiCommandSender;
            Player p = Bukkit.getPlayer(weiStrings[1]);
            plugin.mainGUI.open(admin, p, null, null, 1);
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