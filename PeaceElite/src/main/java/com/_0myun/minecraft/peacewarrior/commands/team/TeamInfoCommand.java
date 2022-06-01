package com._0myun.minecraft.peacewarrior.commands.team;

import com._0myun.minecraft.peacewarrior.TeamManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamInfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(TeamManager.getTeamMembers(p.getUniqueId()).toString());
        return true;
    }
}
