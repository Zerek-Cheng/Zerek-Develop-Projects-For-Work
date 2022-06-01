package com.me.tft_02.soulbound.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.me.tft_02.soulbound.datatypes.ActionType;
import com.me.tft_02.soulbound.datatypes.SoulbindItem;

import cn.citycraft.PluginHelper.config.FileConfig;

public class ItemsConfig {
	private static FileConfig item;
	private final static List<SoulbindItem> soulbindOnCraft = new ArrayList<SoulbindItem>();
	private final static List<SoulbindItem> soulbindOnDrop = new ArrayList<SoulbindItem>();
	private final static List<SoulbindItem> soulbindOnKit = new ArrayList<SoulbindItem>();
	private final static List<SoulbindItem> soulbindOnOpenChest = new ArrayList<SoulbindItem>();
	private final static List<SoulbindItem> soulbindOnPickupItem = new ArrayList<SoulbindItem>();
	private final static List<SoulbindItem> soulbindOnRespawn = new ArrayList<SoulbindItem>();

	public ItemsConfig() {
		loadKeys();
	}

	public static List<SoulbindItem> getSoulbindItems(final ActionType actionType) {
		switch (actionType) {
		case CRAFT:
			return soulbindOnCraft;
		case OPEN_CHEST:
			return soulbindOnOpenChest;
		case PICKUP_ITEM:
			return soulbindOnPickupItem;
		case DROP_ITEM:
			return soulbindOnDrop;
		case RESPAWN:
			return soulbindOnRespawn;
		case KIT:
			return soulbindOnKit;
		default:
			return null;
		}
	}

	public static boolean isActionItem(final ItemStack itemStack, final ActionType actionType) {
		for (final SoulbindItem soulbindItem : getSoulbindItems(actionType)) {

			if (itemStack.getData().equals(soulbindItem.getMaterialData())) {
				if (itemStack.hasItemMeta()) {
					final ItemMeta itemMeta = itemStack.getItemMeta();
					if (soulbindItem.getName() != null) {
						if (itemMeta.getDisplayName().contains(soulbindItem.getName())) {
							if (soulbindItem.getLore() != null && !soulbindItem.getLore().isEmpty()) {
								if (itemMeta.hasLore() && itemMeta.getLore().containsAll(soulbindItem.getLore())) {
									return true;
								}
							}
						}
					}

				}
			}
		}
		return false;
	}

	public static void load(final FileConfig config) {
		item = config;
	}

	private void addSoulbindItem(final ActionType actionType, final SoulbindItem soulbindItem) {
		switch (actionType) {
		case CRAFT:
			soulbindOnCraft.add(soulbindItem);
			return;
		case OPEN_CHEST:
			soulbindOnOpenChest.add(soulbindItem);
			return;
		case PICKUP_ITEM:
			soulbindOnPickupItem.add(soulbindItem);
			return;
		case DROP_ITEM:
			soulbindOnDrop.add(soulbindItem);
			return;
		case RESPAWN:
			soulbindOnRespawn.add(soulbindItem);
			return;
		case KIT:
			soulbindOnKit.add(soulbindItem);
			return;
		}
	}

	@SuppressWarnings("deprecation")
	protected void loadKeys() {
		final ConfigurationSection configurationSection = item.getConfigurationSection("Items");

		if (configurationSection == null) {
			return;
		}

		final Set<String> itemConfigSet = configurationSection.getKeys(false);

		for (final String itemName : itemConfigSet) {
			final String[] itemInfo = itemName.split("[|]");

			final Material itemMaterial = Material.matchMaterial(itemInfo[0]);

			if (itemMaterial == null) {
				Bukkit.getLogger().warning("[SoulBound] Invalid material name. This item will be skipped. - " + itemInfo[0]);
				continue;
			}

			final byte itemData = (itemInfo.length == 2) ? Byte.valueOf(itemInfo[1]) : 0;
			final MaterialData itemMaterialData = new MaterialData(itemMaterial, itemData);

			final List<String> lore = new ArrayList<String>();
			if (item.contains("Items." + itemName + ".Lore")) {

				for (final String loreEntry : item.getStringList("Items." + itemName + ".Lore")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', loreEntry));
				}
			}

			String name = null;
			if (item.contains("Items." + itemName + ".Name")) {
				name = ChatColor.translateAlternateColorCodes('&', item.getString("Items." + itemName + ".Name"));
			}

			final SoulbindItem soulbindItem = new SoulbindItem(itemMaterialData, name, lore);

			final List<String> actions = item.getStringList("Items." + itemName + ".Actions");

			for (final ActionType actionType : ActionType.values()) {
				if (actions.contains(actionType.toString())) {
					addSoulbindItem(actionType, soulbindItem);
				}
			}
		}
	}
}
