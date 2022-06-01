package com.me.tft_02.soulbound.util;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class Permissions {

	public static boolean deleteOnDeath(final Permissible permissible) {
		return permissible.hasPermission("soulbound.items.delete_on_death");
	}

	public static int getSoulbindMaximum(final Player player) {
		final String match = "soulbound.maximum_allowed.";
		int amount = -1;
		for (final PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
			if (permission.getPermission().startsWith(match) && permission.getValue()) {
				amount = Integer.parseInt(permission.getPermission().split("[.]")[2]);
			}
		}
		return amount;
	}

	public static boolean keepOnDeath(final Permissible permissible) {
		return permissible.hasPermission("soulbound.items.keep_on_death");
	}

	public static boolean pickupBypass(final Permissible permissible) {
		return permissible.hasPermission("soulbound.pickup.bypass");
	}
}
