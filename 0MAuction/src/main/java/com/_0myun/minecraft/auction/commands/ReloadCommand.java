package com._0myun.minecraft.auction.commands;

import com._0myun.minecraft.auction.Auction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) return true;
        Auction.INSTANCE.reloadConfig();
        sender.sendMessage("ok");
        return true;
    }
}
