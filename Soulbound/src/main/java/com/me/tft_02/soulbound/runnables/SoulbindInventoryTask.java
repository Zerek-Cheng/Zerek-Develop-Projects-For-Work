package com.me.tft_02.soulbound.runnables;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.me.tft_02.soulbound.config.ItemsConfig;
import com.me.tft_02.soulbound.datatypes.ActionType;
import com.me.tft_02.soulbound.util.ItemUtils;

public class SoulbindInventoryTask extends BukkitRunnable {
	private final ActionType actionType;
	private final Player player;

	public SoulbindInventoryTask(final Player player, final ActionType actionType) {
		this.player = player;
		this.actionType = actionType;
	}

	@Override
	public void run() {
		for (final ItemStack itemStack : player.getInventory().getContents()) {
			if (itemStack != null && ItemsConfig.isActionItem(itemStack, actionType)) {
				ItemUtils.soulbindItem(player, itemStack);
			}
		}
		for (final ItemStack itemStack : player.getInventory().getArmorContents()) {
			if (itemStack != null && ItemsConfig.isActionItem(itemStack, actionType)) {
				ItemUtils.soulbindItem(player, itemStack);
			}
		}
	}
}
