/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.bukkit.Server
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.permissions.Permission
 *  org.bukkit.plugin.PluginManager
 */
package com.onarandombox.MultiverseCore.utils;

import com.fernferret.allpay.multiverse.commons.GenericBank;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseCoreConfig;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiverseCore.utils.MVPermissions;
import com.onarandombox.MultiverseCore.utils.VaultHandler;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

public class PermissionTools {
    private MultiverseCore plugin;

    public PermissionTools(MultiverseCore plugin) {
        this.plugin = plugin;
    }

    public void addToParentPerms(String permString) {
        Permission actualPermission;
        String permStringChopped = permString.replace(".*", "");
        String[] seperated = permStringChopped.split("\\.");
        String parentPermString = PermissionTools.getParentPerm(seperated);
        if (parentPermString == null) {
            this.addToRootPermission("*", permStringChopped);
            this.addToRootPermission("*.*", permStringChopped);
            return;
        }
        Permission parentPermission = this.plugin.getServer().getPluginManager().getPermission(parentPermString);
        if (parentPermission == null) {
            parentPermission = new Permission(parentPermString);
            this.plugin.getServer().getPluginManager().addPermission(parentPermission);
            this.addToParentPerms(parentPermString);
        }
        if ((actualPermission = this.plugin.getServer().getPluginManager().getPermission(permString)) == null) {
            actualPermission = new Permission(permString);
            this.plugin.getServer().getPluginManager().addPermission(actualPermission);
        }
        if (!parentPermission.getChildren().containsKey(permString)) {
            parentPermission.getChildren().put(actualPermission.getName(), true);
            this.plugin.getServer().getPluginManager().recalculatePermissionDefaults(parentPermission);
        }
    }

    private void addToRootPermission(String rootPerm, String permStringChopped) {
        Permission rootPermission = this.plugin.getServer().getPluginManager().getPermission(rootPerm);
        if (rootPermission == null) {
            rootPermission = new Permission(rootPerm);
            this.plugin.getServer().getPluginManager().addPermission(rootPermission);
        }
        rootPermission.getChildren().put(permStringChopped + ".*", true);
        this.plugin.getServer().getPluginManager().recalculatePermissionDefaults(rootPermission);
    }

    private static String getParentPerm(String[] separatedPermissionString) {
        if (separatedPermissionString.length == 1) {
            return null;
        }
        String returnString = "";
        for (int i = 0; i < separatedPermissionString.length - 1; ++i) {
            returnString = returnString + separatedPermissionString[i] + ".";
        }
        return returnString + "*";
    }

    public boolean playerHasMoneyToEnter(MultiverseWorld fromWorld, MultiverseWorld toWorld, CommandSender teleporter, Player teleportee, boolean pay) {
        Player teleporterPlayer;
        if (this.plugin.getMVConfig().getTeleportIntercept()) {
            if (teleporter instanceof ConsoleCommandSender) {
                return true;
            }
            if (teleporter == null) {
                teleporter = teleportee;
            }
            if (!(teleporter instanceof Player)) {
                return false;
            }
            teleporterPlayer = (Player)teleporter;
        } else {
            teleporterPlayer = teleporter instanceof Player ? (Player)teleporter : null;
            if (teleporterPlayer == null) {
                return true;
            }
        }
        if (toWorld == null) {
            return true;
        }
        if (!toWorld.equals(fromWorld)) {
            String formattedAmount;
            boolean usingVault;
            if (toWorld.getPrice() == 0.0) {
                return true;
            }
            if (this.plugin.getMVPerms().hasPermission(teleporter, toWorld.getExemptPermission().getName(), true)) {
                return true;
            }
            if (toWorld.getCurrency() <= 0 && this.plugin.getVaultHandler().getEconomy() != null) {
                usingVault = true;
                formattedAmount = this.plugin.getVaultHandler().getEconomy().format(toWorld.getPrice());
            } else {
                usingVault = false;
                formattedAmount = this.plugin.getBank().getFormattedAmount(teleporterPlayer, toWorld.getPrice(), toWorld.getCurrency());
            }
            String errString = "You need " + formattedAmount + " to send " + (Object)teleportee + " to " + toWorld.getColoredWorldString();
            if (teleportee.equals((Object)teleporter)) {
                errString = "You need " + formattedAmount + " to enter " + toWorld.getColoredWorldString();
            }
            if (usingVault) {
                if (!this.plugin.getVaultHandler().getEconomy().has(teleporterPlayer.getName(), toWorld.getPrice())) {
                    return false;
                }
                if (pay) {
                    if (toWorld.getPrice() < 0.0) {
                        this.plugin.getVaultHandler().getEconomy().depositPlayer(teleporterPlayer.getName(), toWorld.getPrice() * -1.0);
                    } else {
                        this.plugin.getVaultHandler().getEconomy().withdrawPlayer(teleporterPlayer.getName(), toWorld.getPrice());
                    }
                }
            } else {
                GenericBank bank = this.plugin.getBank();
                if (!bank.hasEnough(teleporterPlayer, toWorld.getPrice(), toWorld.getCurrency(), errString)) {
                    return false;
                }
                if (pay) {
                    if (toWorld.getPrice() < 0.0) {
                        bank.give(teleporterPlayer, toWorld.getPrice() * -1.0, toWorld.getCurrency());
                    } else {
                        bank.take(teleporterPlayer, toWorld.getPrice(), toWorld.getCurrency());
                    }
                }
            }
        }
        return true;
    }

    public boolean playerCanGoFromTo(MultiverseWorld fromWorld, MultiverseWorld toWorld, CommandSender teleporter, Player teleportee) {
        /*Player teleporterPlayer;
        this.plugin.log(Level.FINEST, "Checking '" + (Object)teleporter + "' can send '" + (Object)teleportee + "' somewhere");
        if (this.plugin.getMVConfig().getTeleportIntercept()) {
            if (teleporter instanceof ConsoleCommandSender) {
                return true;
            }
            if (teleporter == null) {
                teleporter = teleportee;
            }
            if (!(teleporter instanceof Player)) {
                return false;
            }
            teleporterPlayer = (Player)teleporter;
        } else {
            teleporterPlayer = teleporter instanceof Player ? (Player)teleporter : null;
            if (teleporterPlayer == null) {
                return true;
            }
        }
        if (toWorld != null) {
            if (!this.plugin.getMVPerms().canEnterWorld(teleporterPlayer, toWorld)) {
                if (teleportee.equals((Object)teleporter)) {
                    teleporter.sendMessage("You don't have access to go here...");
                } else {
                    teleporter.sendMessage("You can't send " + teleportee.getName() + " here...");
                }
                return false;
            }
        } else {
            return true;
        }
        if (fromWorld != null && fromWorld.getWorldBlacklist().contains(toWorld.getName())) {
            if (teleportee.equals((Object)teleporter)) {
                teleporter.sendMessage("You don't have access to go to " + toWorld.getColoredWorldString() + " from " + fromWorld.getColoredWorldString());
            } else {
                teleporter.sendMessage("You don't have access to send " + teleportee.getName() + " from " + fromWorld.getColoredWorldString() + " to " + toWorld.getColoredWorldString());
            }
            return false;
        }*/
        return true;
    }

    public boolean playerCanBypassPlayerLimit(MultiverseWorld toWorld, CommandSender teleporter, Player teleportee) {
        if (teleporter == null) {
            teleporter = teleportee;
        }
        if (!(teleporter instanceof Player)) {
            return true;
        }
        MVPermissions perms = this.plugin.getMVPerms();
        if (perms.hasPermission((CommandSender)teleportee, "mv.bypass.playerlimit." + toWorld.getName(), false)) {
            return true;
        }
        teleporter.sendMessage("The world " + toWorld.getColoredWorldString() + " is full");
        return false;
    }

    public boolean playerCanIgnoreGameModeRestriction(MultiverseWorld toWorld, Player teleportee) {
        if (toWorld != null) {
            return this.plugin.getMVPerms().canIgnoreGameModeRestriction(teleportee, toWorld);
        }
        return true;
    }
}

