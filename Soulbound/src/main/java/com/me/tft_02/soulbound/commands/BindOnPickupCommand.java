package com.me.tft_02.soulbound.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.Soulbound;
import com.me.tft_02.soulbound.util.CommandUtils;
import com.me.tft_02.soulbound.util.ItemUtils;

public class BindOnPickupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CommandUtils.noConsoleUsage(sender)) {
            return true;
        }

        switch (args.length) {
            case 0:
                Player player = (Player) sender;

                if (!player.hasPermission("soulbound.commands.bindonpickup")) {
                    return false;
                }

                ItemStack itemInHand = player.getItemInHand();

                if ((itemInHand.getType() == Material.AIR) || ItemUtils.isSoulbound(itemInHand)) {
                	sender.sendMessage(Soulbound.p.getlang("CAN_NOT_BIND_ON_PICKUP"));
                    return false;
                }

                ItemUtils.unbindItem(itemInHand);
                ItemUtils.bopItem(itemInHand);
                sender.sendMessage(Soulbound.p.getlang("BIND_ON_PICKUP"));

                return true;
            default:
                return false;
        }
    }
}
