package com.me.tft_02.soulbound.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.util.ItemUtils;

public class ArmorStandListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
		final Player player = event.getPlayer();
		final ItemStack itemStack = event.getPlayerItem();
		if (itemStack != null && ItemUtils.isSoulbound(itemStack)) {
			player.sendMessage(ChatColor.RED + "绑定物品不允许被放置在盔甲架上.");
			event.setCancelled(true);
		}
	}
}
