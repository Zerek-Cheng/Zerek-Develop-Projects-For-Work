package com.me.tft_02.soulbound.util;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.Soulbound;

public class PlayerData {
	public static HashMap<String, List<ItemStack>> playerSoulboundItems = new HashMap<String, List<ItemStack>>();

	Soulbound plugin;

	public PlayerData(final Soulbound instance) {
		plugin = instance;
	}

	public static List<ItemStack> retrieveItemsDeath(final Player player) {
		return playerSoulboundItems.get(player.getName());
	}

	public static void storeItemsDeath(final Player player, final List<ItemStack> items) {
		playerSoulboundItems.put(player.getName(), items);
	}
}
