package com._0myun.minecraft.skillapi.vexskillkey;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDealer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            R.INSTANCE.reloadConfig();
            R.INSTANCE.load();
            sender.sendMessage(R.INSTANCE.lang("lang1"));
            return true;
        }
        if (!sender.isOp()) return true;
        if (args.length != 2) return true;
        //1 玩家名
        //2 index
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        R.INSTANCE.setSkillOpen(offlinePlayer.getUniqueId(), Integer.valueOf(args[1]), true);
        if (offlinePlayer.isOnline()) {
            Player p = offlinePlayer.getPlayer();
            p.sendMessage(R.INSTANCE.lang("lang2"));
            R.INSTANCE.playerUnInit(p);
            R.INSTANCE.playerInit(p);
        }
        return true;
    }
}
