package com.me.tft_02.soulbound.runnables;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.me.tft_02.soulbound.util.ItemUtils;
import com.me.tft_02.soulbound.util.ItemUtils.ItemType;

public class UpdateArmorTask extends BukkitRunnable {
	private final Player player;

	public UpdateArmorTask(final Player player) {
		this.player = player;
	}

	public void handleBindOnEquip(final Player player) {
		for (final ItemStack armor : player.getInventory().getArmorContents()) {
			if (armor != null && ItemUtils.getItemType(armor) == ItemType.BIND_ON_EQUIP) {
				ItemUtils.soulbindItem(player, armor);
			}
		}
		player.getInventory().setArmorContents(player.getInventory().getArmorContents());
	}

	@Override
	public void run() {
		handleBindOnEquip(player);
	}
}
