package com._0myun.minecraft.peacewarrior.commands;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.data.Position;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BattleQuitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        BattleManager.quit(p);
        p.teleport(((Position) R.INSTANCE.getConfig().get("hall")).toBukkitLocation());
        sender.sendMessage(R.INSTANCE.lang("lang0"));
        return true;
    }
}
