/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package me.Ninstet.MoreHearts;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.Ninstet.MoreHearts.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Warning;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class MoreHearts
        extends JavaPlugin
        implements Listener {
    public final Logger logger = Logger.getLogger("Minecraft");
    public static MoreHearts plugin;
    SettingsManager settings = SettingsManager.getInstance();

    public void onEnable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(String.valueOf(pdfFile.getName()) + " v" + pdfFile.getVersion() + " has been enabled!");
        this.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        this.saveConfig();
        this.settings.setup((Plugin) this);
        this.getServer().getScheduler().scheduleAsyncRepeatingTask((Plugin) this, new Runnable() {

            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    MoreHearts.this.checkPerm(player, false, false);
                }
            }
        }, 0L, 8L);
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(String.valueOf(pdfFile.getName()) + " has been disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (commandLabel.equalsIgnoreCase("morehearts") || commandLabel.equalsIgnoreCase("mh")) {
            if (args.length == 0) {
                if (player.hasPermission("morehearts.admin")) {
                    player.sendMessage((Object) ChatColor.GRAY + "/mh setdefault [Hearts]");
                    player.sendMessage((Object) ChatColor.GRAY + "/mh set [Group] [Hearts]");
                    player.sendMessage((Object) ChatColor.GRAY + "/mh delete [Group]");
                    player.sendMessage((Object) ChatColor.GRAY + "/mh list");
                } else {
                    player.sendMessage((Object) ChatColor.RED + "You do not have access to that command.");
                }
            } else if (args.length == 1) {
                if (player.hasPermission("morehearts.admin")) {
                    if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("delete")) {
                        player.sendMessage((Object) ChatColor.RED + "Specify a group name! Type " + (Object) ChatColor.YELLOW + "/mh" + (Object) ChatColor.RED + " for help.");
                    } else if (args[0].equalsIgnoreCase("setdefault")) {
                        player.sendMessage((Object) ChatColor.RED + "Specify the number of hearts! Type " + (Object) ChatColor.YELLOW + "/mh" + (Object) ChatColor.RED + " for help.");
                    } else if (args[0].equalsIgnoreCase("list")) {
                        player.sendMessage((Object) ChatColor.DARK_GREEN + "Default: " + (Object) ChatColor.GREEN + this.getConfig().getInt("Default"));
                        if (this.getConfig().getConfigurationSection("Groups") == null) {
                            player.sendMessage((Object) ChatColor.RED + "No groups have been set.");
                        } else {
                            StringBuilder sb = new StringBuilder();
                            for (String groupName : this.getConfig().getConfigurationSection("Groups").getKeys(false)) {
                                sb.append((Object) ChatColor.GREEN + groupName).append((Object) ChatColor.DARK_GREEN + " (" + this.getConfig().getInt(new StringBuilder("Groups.").append(groupName).toString()) + "), ");
                            }
                            player.sendMessage((Object) ChatColor.DARK_GREEN + "Groups: " + (Object) ChatColor.GREEN + sb.substring(0, sb.length() - 2) + (Object) ChatColor.DARK_GREEN + ".");
                        }
                    } else {
                        player.sendMessage((Object) ChatColor.RED + "Unknown command! Type " + (Object) ChatColor.YELLOW + "/mh" + (Object) ChatColor.RED + " for help.");
                    }
                } else {
                    player.sendMessage((Object) ChatColor.RED + "You do not have access to that command.");
                }
            } else if (args.length == 2) {
                if (player.hasPermission("morehearts.admin")) {
                    if (args[0].equalsIgnoreCase("set")) {
                        player.sendMessage((Object) ChatColor.RED + "Specify the number of hearts for group " + (Object) ChatColor.YELLOW + args[1] + (Object) ChatColor.RED + "! Type " + (Object) ChatColor.YELLOW + "/mh" + (Object) ChatColor.RED + " for help.");
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        String groupName = args[1];
                        String group = null;
                        if (this.getConfig().getConfigurationSection("Groups") == null) {
                            player.sendMessage((Object) ChatColor.RED + "Group " + (Object) ChatColor.YELLOW + groupName + (Object) ChatColor.RED + " does not exist.");
                        } else {
                            for (String arenaNameCycle : this.getConfig().getConfigurationSection("Groups").getKeys(false)) {
                                if (!groupName.equals(arenaNameCycle)) continue;
                                group = groupName;
                            }
                            if (group != null) {
                                this.getConfig().set("Groups." + group, null);
                                this.saveConfig();
                                Iterator iterator = this.getConfig().getConfigurationSection("Groups").getKeys(false).iterator();
                                if (iterator.hasNext()) {
                                    String unused = (String) iterator.next();
                                    player.sendMessage((Object) ChatColor.DARK_GREEN + "Group " + (Object) ChatColor.GREEN + group + (Object) ChatColor.DARK_GREEN + " has been deleted.");
                                    return true;
                                }
                                this.getConfig().set("Groups", null);
                                this.saveConfig();
                                player.sendMessage((Object) ChatColor.DARK_GREEN + "Group " + (Object) ChatColor.GREEN + group + (Object) ChatColor.DARK_GREEN + " has been deleted.");
                            } else {
                                player.sendMessage((Object) ChatColor.RED + "Group " + (Object) ChatColor.YELLOW + groupName + (Object) ChatColor.RED + " does not exist.");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("setdefault")) {
                        if (MoreHearts.isInteger(args[1])) {
                            this.getConfig().set("Default", (Object) Integer.parseInt(args[1]));
                            this.saveConfig();
                            player.sendMessage((Object) ChatColor.DARK_GREEN + "Default has been set to " + (Object) ChatColor.GREEN + args[1] + (Object) ChatColor.DARK_GREEN + " hearts.");
                        } else {
                            player.sendMessage((Object) ChatColor.RED + "Hearts must be a number!");
                        }
                    } else {
                        player.sendMessage((Object) ChatColor.RED + "Unknown command! Type " + (Object) ChatColor.YELLOW + "/mh" + (Object) ChatColor.RED + " for help.");
                    }
                } else {
                    player.sendMessage((Object) ChatColor.RED + "You do not have access to that command.");
                }
            } else if (args.length == 3) {
                if (player.hasPermission("morehearts.admin")) {
                    if (args[0].equalsIgnoreCase("set")) {
                        if (MoreHearts.isInteger(args[2])) {
                            this.getConfig().set("Groups." + args[1], (Object) Integer.parseInt(args[2]));
                            this.saveConfig();
                            player.sendMessage((Object) ChatColor.DARK_GREEN + "Group " + (Object) ChatColor.GREEN + args[1] + (Object) ChatColor.DARK_GREEN + " has been set to " + (Object) ChatColor.GREEN + args[2] + (Object) ChatColor.DARK_GREEN + " hearts.");
                        } else {
                            player.sendMessage((Object) ChatColor.RED + "Hearts must be a number!");
                        }
                    } else {
                        player.sendMessage((Object) ChatColor.RED + "Unknown command! Type " + (Object) ChatColor.YELLOW + "/mh" + (Object) ChatColor.RED + " for help.");
                    }
                } else {
                    player.sendMessage((Object) ChatColor.RED + "You do not have access to that command.");
                }
            } else {
                player.sendMessage((Object) ChatColor.RED + "Unknown command! Type " + (Object) ChatColor.YELLOW + "/mh" + (Object) ChatColor.RED + " for help.");
            }
        }
        return false;
    }

    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);
            isValidInteger = true;
        } catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return isValidInteger;
    }

    public void checkPerm(Player player, boolean shouldSetHealth, boolean resetHealth) {
        boolean hasPerm = false;
        int maxOpHearts = this.getConfig().getInt("Default");
        if (this.getConfig().getConfigurationSection("Groups") != null) {
            for (String perm : this.getConfig().getConfigurationSection("Groups").getKeys(false)) {
                if (player.isOp() || player.hasPermission("morehearts.*")) {
                    if (this.getConfig().getInt("Groups." + perm) <= maxOpHearts) continue;
                    maxOpHearts = this.getConfig().getInt("Groups." + perm);
                    continue;
                }
                if (!player.hasPermission("morehearts.group." + perm.toLowerCase()) || hasPerm) continue;
                player.setMaxHealth((double) this.getConfig().getInt("Groups." + perm));
                if (shouldSetHealth) {
                    player.setHealth((double) this.getConfig().getInt("Groups." + perm));
                }
                if (resetHealth && this.settings.getData().getConfigurationSection(player.getName()) != null) {
                    player.setHealth(this.settings.getData().getDouble(player.getName()));
                }
                hasPerm = true;
            }
        }
        if (player.isOp() || player.hasPermission("morehearts.*")) {
            player.setMaxHealth((double) maxOpHearts);
            if (shouldSetHealth) {
                player.setHealth((double) maxOpHearts);
            }
            if (resetHealth && this.settings.getData().getConfigurationSection(player.getName()) != null) {
                player.setHealth(this.settings.getData().getDouble(player.getName()));
            }
        } else if (!hasPerm) {
            player.setMaxHealth((double) this.getConfig().getInt("Default"));
            if (shouldSetHealth) {
                player.setHealth((double) this.getConfig().getInt("Default"));
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            this.checkPerm(player, false, true);
        } else {
            this.checkPerm(player, true, false);
        }
    }

    @EventHandler
    public void onHealthRegain(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            this.checkPerm(player, false, false);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        this.checkPerm(player, true, false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        boolean hasPerm = false;
        try {
            for (String perm : this.getConfig().getConfigurationSection("Groups").getKeys(false)) {
                if (!player.hasPermission("morehearts.group." + perm.toLowerCase()) || hasPerm) continue;
                this.settings.getData().set(player.getName(), (Object) player.getHealth());
                this.settings.saveData();
                hasPerm = true;
            }
        } catch (Exception ex) {
        }
    }

}

