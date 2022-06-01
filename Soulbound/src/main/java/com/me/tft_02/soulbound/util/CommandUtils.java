package com.me.tft_02.soulbound.util;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandUtils {

	public static String[] extractArgs(final String command) {
		String[] args = { "" };
		final String[] split = command.split(" ", 2);
		if (split.length > 1) {
			args = split[1].split(" ");
		}
		return args;
	}

	public static boolean isOffline(final CommandSender sender, final OfflinePlayer player) {
		if (player != null && player.isOnline()) {
			return false;
		}

		sender.sendMessage(ChatColor.RED + "当前命令不能作用于离线的玩家.");
		return true;
	}

	public static boolean noConsoleUsage(final CommandSender sender) {
		if (sender instanceof Player) {
			return false;
		}

		sender.sendMessage("当前命令不能再控制台执行!");
		return true;
	}
}
