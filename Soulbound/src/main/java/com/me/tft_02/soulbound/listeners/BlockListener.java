package com.me.tft_02.soulbound.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.util.DurabilityUtils;

public class BlockListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		final ItemStack itemStack = event.getPlayer().getItemInHand();
		DurabilityUtils.handleInfiniteDurability(itemStack);
	}
}
