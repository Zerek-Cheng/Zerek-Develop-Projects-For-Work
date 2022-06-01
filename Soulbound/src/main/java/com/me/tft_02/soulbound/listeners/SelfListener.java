package com.me.tft_02.soulbound.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.events.SoulbindItemEvent;
import com.me.tft_02.soulbound.util.ItemUtils;
import com.me.tft_02.soulbound.util.Permissions;

public class SelfListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onItemSoulbound(final SoulbindItemEvent event) {
		final Player player = event.getPlayer();
		final Inventory inventory = player.getInventory();
		final int maxAmount = Permissions.getSoulbindMaximum(player);
		if (maxAmount < 0) {
			return;
		}
		int count = 0;
		for (final ItemStack itemStack : inventory.getContents()) {
			if (itemStack != null && ItemUtils.isSoulbound(itemStack)) {
				count++;
			}
		}
		if (count >= maxAmount) {
			player.sendMessage(ChatColor.RED + "无法绑定更多的物品, 已到达最大上限! " + ChatColor.GOLD + "(" + maxAmount + ")");
			event.setCancelled(true);
		}
	}
}
