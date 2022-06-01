package com.me.tft_02.soulbound.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.Soulbound;
import com.me.tft_02.soulbound.config.Config;
import com.me.tft_02.soulbound.util.CommandUtils;
import com.me.tft_02.soulbound.util.ItemUtils;

public class BindCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (CommandUtils.noConsoleUsage(sender)) {
			return true;
		}

		if (!sender.hasPermission("soulbound.commands.bind")) {
			//return false;
		}

		boolean bindFullInventory = false;

		Player player = (Player) sender;
		Player target=player;
		/*switch (args.length) {
		case 1:
			target = Soulbound.p.getServer().getPlayerExact(args[0]);

			if (CommandUtils.isOffline(sender, target)) {
				return true;
			}

			break;
		case 2:
			if (!args[1].equalsIgnoreCase("inventory")) {
				sender.sendMessage(Soulbound.p.getlang("BIND_INVENTORY"));
				return true;
			}

			bindFullInventory = true;
			target = Soulbound.p.getServer().getPlayerExact(args[0]);

			if (CommandUtils.isOffline(sender, target)) {
				return true;
			}

			break;
		default:
			target = player;
		}*/

		if (bindFullInventory) {
			return handleBindFullInventory(player, target);
		}

		ItemStack itemInHand = player.getItemInHand();

		if ((itemInHand.getType() == Material.AIR) || ItemUtils.isSoulbound(itemInHand)) {
			sender.sendMessage(Soulbound.p.getlang("CAN_NOT_BIND"));
			return false;
		}

		ItemUtils.soulbindItem(target, itemInHand);

		if (ItemUtils.isSoulbound(itemInHand) && Config.getFeedbackEnabled()) {
			sender.sendMessage(Soulbound.p.getlang("BINDED").replace("%target%", target.getName()));
		}
		return true;
	}

	private boolean handleBindFullInventory(Player player, Player target) {
		for (ItemStack itemStack : player.getInventory().getContents()) {
			if (itemStack != null && itemStack.getType() != Material.AIR) {
				ItemUtils.soulbindItem(target, itemStack);
			}
		}

		if (Config.getFeedbackEnabled()) {
			player.sendMessage(Soulbound.p.getlang("BIND_FULL_INEVNTORY")
					.replace("%player%", player.getName()).replace("%target%", target.getName()));
		}
		return true;
	}
}
