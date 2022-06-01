package com.me.tft_02.soulbound.config;

import java.util.List;

import cn.citycraft.PluginHelper.config.FileConfig;

public class Config {
	private static FileConfig config;

	public static boolean getAllowItemStoring() {
		return config.getBoolean("Soulbound.Allow_Item_Storing", true);
	}

	public static List<String> getBindCommands() {
		return config.getStringList("Soulbound.Commands_Bind_When_Used");
	}

	public static List<String> getBlockedCommands() {
		return config.getStringList("Soulbound.Blocked_Commands");
	}

	public static boolean getDeleteOnDrop() {
		return config.getBoolean("Soulbound.Delete_On_Drop", false);
	}

	public static boolean getFeedbackEnabled() {
		return config.getBoolean("Soulbound.Feedback_Messages_Enabled", true);
	}

	public static boolean getInfiniteDurability() {
		return config.getBoolean("Soulbound.Infinite_Durability", false);
	}

	public static boolean getPreventItemDrop() {
		return config.getBoolean("Soulbound.Prevent_Item_Drop", false);
	}

	public static boolean getShowNameInLore() {
		return config.getBoolean("Soulbound.Show_Name_In_Lore", true);
	}

	public static void load(final FileConfig config) {
		Config.config = config;
	}
}
