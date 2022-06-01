package com.WeiBoss.bossshoptr.Commands;

import com.WeiBoss.bossshoptr.File.Config;
import com.WeiBoss.bossshoptr.File.Message;
import com.WeiBoss.bossshoptr.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class WeiCommand {
    private boolean isPlayer;
    private static Main plugin = Main.instance;

    public final void execute(CommandSender weiCommandSender, String[] weiStrings) {
        this.isPlayer = (weiCommandSender instanceof Player);
        if (playerOnly() && !this.isPlayer)
            return;
        if (getPermission() != null && !weiCommandSender.hasPermission(getPermission())) {
            weiCommandSender.sendMessage(Config.Prefix + Message.NoPermission.replace("%s%",getPermission()));
            return;
        }
        perform(weiCommandSender, weiStrings);
    }

    boolean isPlayer() {
        return this.isPlayer();
    }

    public abstract void perform(CommandSender weiCommandSender, String[] weiStrings);

    public abstract boolean playerOnly();

    public abstract String getPermission();
}