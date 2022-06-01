package com._0myun.minecraft.peacewarrior.commands.team;

import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamInviteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        String invite = args[2];
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(invite);
        if (!offlinePlayer.isOnline()) {
            sender.sendMessage(R.INSTANCE.lang("lang9"));
            return true;
        }

        boolean apply = TeamManager.apply(p.getUniqueId(), offlinePlayer.getUniqueId());
        if (!apply){
            sender.sendMessage(R.INSTANCE.lang("lang13"));
            return true;
        }
        sender.sendMessage(R.INSTANCE.lang("lang10"));
        return true;
    }
}
