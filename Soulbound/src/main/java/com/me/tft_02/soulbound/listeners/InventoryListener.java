package com.me.tft_02.soulbound.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.Soulbound;
import com.me.tft_02.soulbound.config.Config;
import com.me.tft_02.soulbound.config.ItemsConfig;
import com.me.tft_02.soulbound.datatypes.ActionType;
import com.me.tft_02.soulbound.runnables.UpdateArmorTask;
import com.me.tft_02.soulbound.util.ItemUtils;
import com.me.tft_02.soulbound.util.ItemUtils.ItemType;
import com.me.tft_02.soulbound.util.Permissions;

public class InventoryListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClickEvent(final InventoryClickEvent event) {
		final HumanEntity entity = event.getWhoClicked();
		if (!(entity instanceof Player)) {
			return;
		}
		final Player player = (Player) entity;
		final ItemStack itemStack = event.getCurrentItem();
		final InventoryType inventoryType = event.getInventory().getType();
		if (inventoryType == null) {
			return;
		}
		final ItemType itemType = ItemUtils.getItemType(itemStack);
		if (itemType != ItemType.SOULBOUND) {
			return;
		}
		if (!Config.getAllowItemStoring() && inventoryType != InventoryType.CRAFTING) {
			event.setCancelled(true);
		}
		if (ItemUtils.isBindedPlayer(player, itemStack) || Permissions.pickupBypass(player)) {
			return;
		}
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryMoveEvent(final InventoryMoveItemEvent event) {
		final ItemStack itemStack = event.getItem();
		final ItemType itemType = ItemUtils.getItemType(itemStack);
		if (itemType == ItemType.SOULBOUND) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryOpen(final InventoryOpenEvent event) {
		final HumanEntity humanEntity = event.getPlayer();
		final Inventory inventory = event.getInventory();
		if (!(humanEntity instanceof Player)) {
			return;
		}
		final Player player = (Player) humanEntity;
		for (final ItemStack itemStack : inventory.getContents()) {
			if (itemStack != null && ItemsConfig.isActionItem(itemStack, ActionType.OPEN_CHEST)) {
				ItemUtils.soulbindItem(player, itemStack);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryPickupItemEvent(final InventoryPickupItemEvent event) {
		final ItemStack itemStack = event.getItem().getItemStack();
		final ItemType itemType = ItemUtils.getItemType(itemStack);
		if (itemType == ItemType.SOULBOUND) {
			event.setCancelled(true);
		}
	}

	/**
	 * Check CraftItemEvent events.
	 *
	 * @param event
	 *            The event to check
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onCraftItem(final CraftItemEvent event) {
		final HumanEntity humanEntity = event.getWhoClicked();
		if (!(humanEntity instanceof Player)) {
			return;
		}
		final Player player = (Player) humanEntity;
		final ItemStack itemStack = event.getRecipe().getResult();
		if (ItemsConfig.isActionItem(itemStack, ActionType.CRAFT)) {
			event.getInventory().setResult(ItemUtils.soulbindItem(player, itemStack));
		}
	}

	/**
	 * Check EnchantItemEvent events.
	 *
	 * @param event
	 *            The event to check
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onEnchantItem(final EnchantItemEvent event) {
		final Player player = event.getEnchanter();
		final ItemStack itemStack = event.getItem();
		if (ItemUtils.isBindOnUse(itemStack)) {
			ItemUtils.soulbindItem(player, itemStack);
			return;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	void onInventoryClick(final InventoryClickEvent event) {
		final HumanEntity entity = event.getWhoClicked();
		final ItemStack itemStack = event.getCurrentItem();
		final SlotType slotType = event.getSlotType();
		final InventoryType inventoryType = event.getInventory().getType();
		if (inventoryType == null) {
			return;
		}
		if (itemStack == null) {
			return;
		}
		if (entity instanceof Player) {
			final Player player = (Player) entity;
			switch (slotType) {
			case ARMOR:
				new UpdateArmorTask(player).runTaskLater(Soulbound.p, 2);
				return;
			case CONTAINER:
				final ItemType itemType = ItemUtils.getItemType(itemStack);
				switch (itemType) {
				case BIND_ON_PICKUP:
					ItemUtils.soulbindItem(player, itemStack);
					return;
				default:
					return;
				}
			default:
				if (ItemUtils.isEquipable(itemStack) && event.isShiftClick()) {
					new UpdateArmorTask(player).runTaskLater(Soulbound.p, 2);
					return;
				}
				break;
			}
		}
	}
}
