package com.me.tft_02.soulbound;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.me.tft_02.soulbound.commands.BindCommand;
import com.me.tft_02.soulbound.commands.BindOnPickupCommand;
import com.me.tft_02.soulbound.commands.BindOnUseCommand;
import com.me.tft_02.soulbound.commands.UnbindCommand;
import com.me.tft_02.soulbound.config.Config;
import com.me.tft_02.soulbound.config.ItemsConfig;
import com.me.tft_02.soulbound.listeners.ArmorStandListener;
import com.me.tft_02.soulbound.listeners.BlockListener;
import com.me.tft_02.soulbound.listeners.EntityListener;
import com.me.tft_02.soulbound.listeners.InventoryListener;
import com.me.tft_02.soulbound.listeners.PlayerListener;

import cn.citycraft.PluginHelper.config.FileConfig;
import cn.citycraft.PluginHelper.utils.VersionChecker;

public class Soulbound extends JavaPlugin {
	// Checks for hooking into other plugins
	public static boolean epicBossRecodedEnabled = false;

	public static boolean loreLocksEnabled = false;

	public static Soulbound p;

	// Jar Stuff
	public static File soulbound;

	/* File Paths */
	private static String mainDirectory;
	private FileConfig config;
	private FileConfig itemcfg;
	private FileConfig msgcfg;

	// Update Check
	private boolean updateAvailable;

	public static String getMainDirectory() {
		return mainDirectory;
	}

	public void debug(final String message) {
		getLogger().info("[Debug] " + message);
	}

	@Override
	public FileConfiguration getConfig() {
		return config;
	}

	public String getlang(final String type) {
		return this.getmessage("Message." + type);
	}

	public String getmessage(final String path) {
		final String message = msgcfg.getMessage(path);
		return message;
	}

	public boolean isUpdateAvailable() {
		return updateAvailable;
	}

	/**
	 * Run things on enable.
	 */
	@Override
	public void onEnable() {
		p = this;

		setupFilePaths();

		loadConfigFiles();

		registerEvents();

		registerCommands();

		new VersionChecker(this);
	}

	@Override
	public void onLoad() {
		itemcfg = new FileConfig(this, "item.yml");
		msgcfg = new FileConfig(this, "message.yml");
		config = new FileConfig(this);
	}

	@Override
	public void reloadConfig() {
		msgcfg.reload();
		config.reload();
	}

	private void loadConfigFiles() {
		Config.load(config);
		ItemsConfig.load(itemcfg);
	}

	private void registerCommands() {
		getCommand("bind").setExecutor(new BindCommand());
		getCommand("bindonpickup").setExecutor(new BindOnPickupCommand());
		getCommand("bindonuse").setExecutor(new BindOnUseCommand());
		getCommand("unbind").setExecutor(new UnbindCommand());
	}

	private void registerEvents() {
		final PluginManager pm = getServer().getPluginManager();
		final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new InventoryListener(), this);
		pm.registerEvents(new EntityListener(), this);
		pm.registerEvents(new BlockListener(), this);
		if (version.equals("v1_8_R1") || version.equals("v1_8_R2") || version.equals("v1_8_R3")) {
			pm.registerEvents(new ArmorStandListener(), this);
		}
	}

	private void setupFilePaths() {
		soulbound = getFile();
		mainDirectory = getDataFolder().getPath() + File.separator;
	}

}
