package com.me.tft_02.soulbound.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.Soulbound;
import com.me.tft_02.soulbound.config.Config;
import com.me.tft_02.soulbound.config.ItemsConfig;
import com.me.tft_02.soulbound.datatypes.ActionType;
import com.me.tft_02.soulbound.runnables.SoulbindInventoryTask;
import com.me.tft_02.soulbound.runnables.UpdateArmorTask;
import com.me.tft_02.soulbound.runnables.UpdateInventoryTask;
import com.me.tft_02.soulbound.util.CommandUtils;
import com.me.tft_02.soulbound.util.DurabilityUtils;
import com.me.tft_02.soulbound.util.ItemUtils;
import com.me.tft_02.soulbound.util.Permissions;
import com.me.tft_02.soulbound.util.PlayerData;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onItemDrop(final PlayerDropItemEvent event) {
		final Player player = event.getPlayer();
		if (!player.isValid()) {
			return;
		}
		final Item item = event.getItemDrop();
		final ItemStack itemStack = item.getItemStack();
		if (Config.getPreventItemDrop()) {
			if (ItemUtils.isSoulbound(itemStack) && ItemUtils.isBindedPlayer(player, itemStack)) {
				item.setPickupDelay(2 * 20);
				event.setCancelled(true);
				new UpdateInventoryTask(player).runTask(Soulbound.p);
			}
			return;
		}
		if (Config.getDeleteOnDrop()) {
			player.playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 1.0F, 1.0F);
			event.getItemDrop().remove();
			return;
		}
		if (ItemsConfig.isActionItem(itemStack, ActionType.DROP_ITEM)) {
			ItemUtils.soulbindItem(player, itemStack);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onItemPickup(final PlayerPickupItemEvent event) {
		final Player player = event.getPlayer();
		final Item item = event.getItem();
		final ItemStack itemStack = item.getItemStack();
		if (ItemUtils.isSoulbound(itemStack) && !ItemUtils.isBindedPlayer(player, itemStack)) {
			if (!Permissions.pickupBypass(player)) {
				event.setCancelled(true);
			}
			return;
		}
		if (ItemUtils.isBindOnPickup(itemStack) || ItemsConfig.isActionItem(itemStack, ActionType.PICKUP_ITEM)) {
			ItemUtils.soulbindItem(player, itemStack);
		}
		if (player.getItemOnCursor() != null && ItemUtils.isSoulbound(player.getItemOnCursor())) {
			item.setPickupDelay(40);
			event.setCancelled(true);
		}
	}

	/**
	 * Watch PlayerCommandPreprocessEvent events.
	 *
	 * @param event
	 *            The event to watch
	 */
	@EventHandler(ignoreCancelled = true)
	public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		final ItemStack itemStack = player.getItemInHand();
		final String command = event.getMessage();

		if (ItemUtils.isSoulbound(itemStack) && Config.getBlockedCommands().contains(command)) {
			player.sendMessage(ChatColor.RED + "不允许使用命令 " + ChatColor.GOLD + command + ChatColor.RED + " 当拿着绑定物品的时候.");
			event.setCancelled(true);
		}
	}

	/**
	 * Monitor PlayerCommandPreprocessEvent events.
	 *
	 * @param event
	 *            The event to monitor
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerCommandMonitor(final PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		final ItemStack inHand = player.getItemInHand();
		final String command = event.getMessage();
		final String[] args = CommandUtils.extractArgs(command);
		if (!ItemUtils.isSoulbound(inHand) && Config.getBindCommands().contains(command)) {
			ItemUtils.soulbindItem(player, inHand);
			return;
		}
		if (command.contains("kit")) {
			Player target;
			if (args.length >= 2) {
				target = Soulbound.p.getServer().getPlayer(args[1]);
			} else {
				target = player;
			}
			if (target == null) {
				return;
			}
			new SoulbindInventoryTask(target, ActionType.KIT).runTask(Soulbound.p);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerDeath(final PlayerDeathEvent event) {
		final Player player = event.getEntity();
		final boolean deleteOnDeath = Permissions.deleteOnDeath(player);
		final boolean keepOnDeath = Permissions.keepOnDeath(player);
		final List<ItemStack> items = new ArrayList<>();
		if (!keepOnDeath && !deleteOnDeath) {
			return;
		}
		for (final ItemStack item : new ArrayList<>(event.getDrops())) {
			if (ItemUtils.isSoulbound(item) && ItemUtils.isBindedPlayer(player, item)) {
				if (keepOnDeath) {
					items.add(item);
				}
				event.getDrops().remove(item);
			}
		}
		PlayerData.storeItemsDeath(player, items);
	}

	/**
	 * Monitor PlayerFishEvent events.
	 *
	 * @param event
	 *            The event to monitor
	 */
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerFish(final PlayerFishEvent event) {
		final ItemStack itemInHand = event.getPlayer().getItemInHand();
		DurabilityUtils.handleInfiniteDurability(itemInHand);
	}

	/**
	 * Watch PlayerInteract events.
	 *
	 * @param event
	 *            The event to watch
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(final PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		final Action action = event.getAction();
		final ItemStack inHand = player.getItemInHand();
		switch (action) {
		case RIGHT_CLICK_BLOCK:
		case RIGHT_CLICK_AIR:
		case LEFT_CLICK_AIR:
		case LEFT_CLICK_BLOCK:
			if (ItemUtils.isEquipable(inHand)) {
				new UpdateArmorTask(player).runTaskLater(Soulbound.p, 2);
			} else if (ItemUtils.isBindOnUse(inHand)) {
				ItemUtils.soulbindItem(player, inHand);
			}
		default:
			break;
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerRespawn(final PlayerRespawnEvent event) {
		new SoulbindInventoryTask(event.getPlayer(), ActionType.RESPAWN).runTask(Soulbound.p);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerRespawnHighest(final PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		if (!Permissions.keepOnDeath(player)) {
			return;
		}
		final ItemStack[] items = PlayerData.retrieveItemsDeath(player).toArray(new ItemStack[0]);
		if (items != null) {
			player.getInventory().addItem(items);
		}
		new UpdateInventoryTask(player).runTask(Soulbound.p);
	}

	/**
	 * Monitor PlayerShearEntityEvent events.
	 *
	 * @param event
	 *            The event to monitor
	 */
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerShearEntity(final PlayerShearEntityEvent event) {
		final ItemStack itemInHand = event.getPlayer().getItemInHand();
		DurabilityUtils.handleInfiniteDurability(itemInHand);
	}

	/**
	 * Monitor ServerCommandEvent events.
	 *
	 * @param event
	 *            The event to monitor
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onServerCommand(final ServerCommandEvent event) {
		final String command = event.getCommand();
		final String[] args = CommandUtils.extractArgs(command);
		if (!command.contains("kit")) {
			return;
		}
		if (args.length < 2) {
			return;
		}
		final Player target = Soulbound.p.getServer().getPlayer(args[1]);
		new SoulbindInventoryTask(target, ActionType.KIT).runTask(Soulbound.p);
	}
}
