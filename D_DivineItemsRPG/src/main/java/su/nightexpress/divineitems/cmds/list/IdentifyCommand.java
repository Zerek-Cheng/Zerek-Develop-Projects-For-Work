/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package su.nightexpress.divineitems.cmds.list;

import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.identify.IdentifyManager;
import su.nightexpress.divineitems.utils.Utils;

public class IdentifyCommand
extends CommandBase {
    private DivineItems plugin;

    public IdentifyCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        IdentifyManager identifyManager = this.plugin.getMM().getIdentifyManager();
        if (arrstring.length == 2 && arrstring[1].equalsIgnoreCase("force")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                return;
            }
            Player player = (Player)commandSender;
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidItem.toMsg());
                return;
            }
            itemStack = identifyManager.identify(itemStack);
            player.getInventory().setItemInMainHand(itemStack);
        } else if (arrstring.length >= 3 && arrstring[1].equalsIgnoreCase("tome")) {
            if ((arrstring.length == 3 || arrstring.length == 4) && arrstring[2].equalsIgnoreCase("list")) {
                int n = 1;
                if (arrstring.length == 4) {
                    try {
                        n = Integer.parseInt(arrstring[3]);
                    }
                    catch (NumberFormatException numberFormatException) {
                        commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                    }
                }
                Utils.interactiveList(commandSender, n, identifyManager.getTomeNames(), String.valueOf(identifyManager.name()) + "_tome", "1");
            } else if (arrstring.length == 5 && arrstring[2].equalsIgnoreCase("get")) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                    return;
                }
                Player player = (Player)commandSender;
                String string = arrstring[3];
                IdentifyManager.IdentifyTome identifyTome = identifyManager.getTomeById(string);
                if (identifyTome == null) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Identify_Invalid_Tome.toMsg().replace("%s", string));
                    return;
                }
                int n = 1;
                try {
                    n = Integer.parseInt(arrstring[4]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[4]));
                }
                int n2 = 0;
                while (n2 < n) {
                    if (string.equalsIgnoreCase("random")) {
                        identifyTome = identifyManager.getTomeById(string);
                    }
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItemNaturally(player.getLocation(), identifyTome.create()).setPickupDelay(40);
                    } else {
                        player.getInventory().addItem(new ItemStack[]{identifyTome.create()});
                    }
                    ++n2;
                }
            } else if (arrstring.length == 6 && arrstring[2].equalsIgnoreCase("give")) {
                Player player = Bukkit.getPlayer((String)arrstring[3]);
                if (player == null) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
                    return;
                }
                String string = arrstring[4];
                IdentifyManager.IdentifyTome identifyTome = identifyManager.getTomeById(string);
                if (identifyTome == null) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Identify_Invalid_Tome.toMsg().replace("%s", string));
                    return;
                }
                int n = 1;
                try {
                    n = Integer.parseInt(arrstring[5]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[5]));
                }
                int n3 = 0;
                while (n3 < n) {
                    if (string.equalsIgnoreCase("random")) {
                        identifyTome = identifyManager.getTomeById(string);
                    }
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItemNaturally(player.getLocation(), identifyTome.create()).setPickupDelay(40);
                    } else {
                        player.getInventory().addItem(new ItemStack[]{identifyTome.create()});
                    }
                    ++n3;
                }
            } else if (arrstring.length == 9 && arrstring[2].equalsIgnoreCase("drop")) {
                World world = Bukkit.getWorld((String)arrstring[3]);
                if (world == null) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidWorld.toMsg().replace("%s", arrstring[3]));
                    return;
                }
                double d = 0.0;
                double d2 = 0.0;
                double d3 = 0.0;
                try {
                    d = Double.parseDouble(arrstring[4]);
                    d2 = Double.parseDouble(arrstring[5]);
                    d3 = Double.parseDouble(arrstring[6]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidCoordinates.toMsg().replace("%s", new StringBuilder(String.valueOf(arrstring[4])).append(" ").append(arrstring[5]).append(" ").append(arrstring[6]).toString()));
                }
                String string = arrstring[7];
                IdentifyManager.IdentifyTome identifyTome = identifyManager.getTomeById(string);
                if (identifyTome == null) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Identify_Invalid_Tome.toMsg().replace("%s", string));
                    return;
                }
                int n = 1;
                try {
                    n = Integer.parseInt(arrstring[8]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[8]));
                }
                Location location = new Location(world, d, d2, d3);
                int n4 = 0;
                while (n4 < n) {
                    if (string.equalsIgnoreCase("random")) {
                        identifyTome = identifyManager.getTomeById(string);
                    }
                    world.dropItemNaturally(location, identifyTome.create()).setPickupDelay(40);
                    ++n4;
                }
            }
        } else if (arrstring.length >= 3 && arrstring[1].equalsIgnoreCase("item")) {
            if ((arrstring.length == 3 || arrstring.length == 4) && arrstring[2].equalsIgnoreCase("list")) {
                int n = 1;
                if (arrstring.length == 4) {
                    try {
                        n = Integer.parseInt(arrstring[3]);
                    }
                    catch (NumberFormatException numberFormatException) {
                        commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                    }
                }
                Utils.interactiveList(commandSender, n, identifyManager.getUINames(), String.valueOf(identifyManager.name()) + "_item", "-1 1");
            } else if (arrstring.length == 6 && arrstring[2].equalsIgnoreCase("get")) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                    return;
                }
                Player player = (Player)commandSender;
                String string = arrstring[3];
                IdentifyManager.UnidentifiedItem unidentifiedItem = identifyManager.getItemById(string);
                if (unidentifiedItem == null) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Identify_Invalid_Item.toMsg().replace("%s", string));
                    return;
                }
                int n = 1;
                try {
                    n = Integer.parseInt(arrstring[4]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[4]));
                }
                int n5 = 1;
                try {
                    n5 = Integer.parseInt(arrstring[5]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[5]));
                }
                int n6 = 0;
                while (n6 < n5) {
                    if (string.equalsIgnoreCase("random")) {
                        unidentifiedItem = identifyManager.getItemById(string);
                    }
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItemNaturally(player.getLocation(), unidentifiedItem.create(n)).setPickupDelay(40);
                    } else {
                        player.getInventory().addItem(new ItemStack[]{unidentifiedItem.create(n)});
                    }
                    ++n6;
                }
            } else if (arrstring.length == 7 && arrstring[2].equalsIgnoreCase("give")) {
                Player player = Bukkit.getPlayer((String)arrstring[3]);
                if (player == null) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
                    return;
                }
                String string = arrstring[4];
                IdentifyManager.UnidentifiedItem unidentifiedItem = identifyManager.getItemById(string);
                if (unidentifiedItem == null) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Identify_Invalid_Tome.toMsg().replace("%s", string));
                    return;
                }
                int n = 1;
                try {
                    n = Integer.parseInt(arrstring[5]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[5]));
                }
                int n7 = 1;
                try {
                    n7 = Integer.parseInt(arrstring[6]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[6]));
                }
                int n8 = 0;
                while (n8 < n7) {
                    if (string.equalsIgnoreCase("random")) {
                        unidentifiedItem = identifyManager.getItemById(string);
                    }
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItemNaturally(player.getLocation(), unidentifiedItem.create(n)).setPickupDelay(40);
                    } else {
                        player.getInventory().addItem(new ItemStack[]{unidentifiedItem.create(n)});
                    }
                    ++n8;
                }
            } else if (arrstring.length == 10 && arrstring[2].equalsIgnoreCase("drop")) {
                World world = Bukkit.getWorld((String)arrstring[3]);
                if (world == null) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidWorld.toMsg().replace("%s", arrstring[3]));
                    return;
                }
                double d = 0.0;
                double d4 = 0.0;
                double d5 = 0.0;
                try {
                    d = Double.parseDouble(arrstring[4]);
                    d4 = Double.parseDouble(arrstring[5]);
                    d5 = Double.parseDouble(arrstring[6]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidCoordinates.toMsg().replace("%s", new StringBuilder(String.valueOf(arrstring[4])).append(" ").append(arrstring[5]).append(" ").append(arrstring[6]).toString()));
                }
                String string = arrstring[7];
                IdentifyManager.UnidentifiedItem unidentifiedItem = identifyManager.getItemById(string);
                if (unidentifiedItem == null) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Identify_Invalid_Tome.toMsg().replace("%s", string));
                    return;
                }
                int n = 1;
                try {
                    n = Integer.parseInt(arrstring[8]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[8]));
                }
                int n9 = 1;
                try {
                    n9 = Integer.parseInt(arrstring[9]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[9]));
                }
                Location location = new Location(world, d, d4, d5);
                int n10 = 0;
                while (n10 < n9) {
                    if (string.equalsIgnoreCase("random")) {
                        unidentifiedItem = identifyManager.getItemById(string);
                    }
                    world.dropItemNaturally(location, unidentifiedItem.create(n)).setPickupDelay(40);
                    ++n10;
                }
            }
        } else {
            for (String string : Lang.Help_Identify.getList()) {
                commandSender.sendMessage(string.replace("%m_state%", this.plugin.getMM().getColorStatus(identifyManager.isActive())).replace("%m_ver%", identifyManager.version()).replace("%m_name%", identifyManager.name()));
            }
            return;
        }
    }

    @Override
    public String getPermission() {
        return "divineitems.admin";
    }

    @Override
    public boolean playersOnly() {
        return false;
    }
}

