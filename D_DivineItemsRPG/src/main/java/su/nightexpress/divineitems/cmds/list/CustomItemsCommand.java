/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
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
import su.nightexpress.divineitems.modules.customitems.CustomItemsManager;
import su.nightexpress.divineitems.utils.Utils;

public class CustomItemsCommand
extends CommandBase {
    private DivineItems plugin;

    public CustomItemsCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        CustomItemsManager customItemsManager = this.plugin.getMM().getCustomItemsManager();
        if ((arrstring.length == 2 || arrstring.length == 3) && arrstring[1].equalsIgnoreCase("list")) {
            int n = 1;
            if (arrstring.length == 3) {
                try {
                    n = Integer.parseInt(arrstring[2]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                }
            }
            Utils.interactiveList(commandSender, n, customItemsManager.getCustomItemsNames(), customItemsManager.name(), "1");
        } else if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("save")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                return;
            }
            Player player = (Player)commandSender;
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (itemStack == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidItem.toMsg());
                return;
            }
            String string = arrstring[2];
            customItemsManager.saveCustomItem(itemStack, string);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("delete")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                return;
            }
            Player player = (Player)commandSender;
            String string = arrstring[2];
            if (!customItemsManager.removeCustomItem(string)) {
                player.sendMessage("\u00a7cItem \u00a77" + string + " \u00a7cdoesn't exists!");
                return;
            }
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else if (arrstring.length == 4 && arrstring[1].equalsIgnoreCase("get")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                return;
            }
            Player player = (Player)commandSender;
            String string = arrstring[2];
            int n = 1;
            try {
                n = Integer.parseInt(arrstring[3]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
            }
            ItemStack itemStack = customItemsManager.getCustomItem(string);
            if (itemStack == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.CustomItems_Invalid.toMsg().replace("%s", string));
                return;
            }
            int n2 = 0;
            while (n2 < n) {
                if (string.equalsIgnoreCase("random")) {
                    itemStack = customItemsManager.getCustomItem(string);
                }
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack).setPickupDelay(40);
                } else {
                    player.getInventory().addItem(new ItemStack[]{itemStack});
                }
                ++n2;
            }
        } else if (arrstring.length == 5 && arrstring[1].equalsIgnoreCase("give")) {
            Player player = Bukkit.getPlayer((String)arrstring[2]);
            if (player == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
                return;
            }
            String string = arrstring[3];
            int n = 1;
            try {
                n = Integer.parseInt(arrstring[4]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[4]));
            }
            ItemStack itemStack = customItemsManager.getCustomItem(string);
            if (itemStack == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.CustomItems_Invalid.toMsg().replace("%s", string));
                return;
            }
            int n3 = 0;
            while (n3 < n) {
                if (string.equalsIgnoreCase("random")) {
                    itemStack = customItemsManager.getCustomItem(string);
                }
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack).setPickupDelay(40);
                } else {
                    player.getInventory().addItem(new ItemStack[]{itemStack});
                }
                ++n3;
            }
        } else if (arrstring.length == 8 && arrstring[1].equalsIgnoreCase("drop")) {
            World world = Bukkit.getWorld((String)arrstring[2]);
            if (world == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidWorld.toMsg().replace("%s", arrstring[2]));
                return;
            }
            double d = 0.0;
            double d2 = 0.0;
            double d3 = 0.0;
            try {
                d = Double.parseDouble(arrstring[3]);
                d2 = Double.parseDouble(arrstring[4]);
                d3 = Double.parseDouble(arrstring[5]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidCoordinates.toMsg().replace("%s", new StringBuilder(String.valueOf(arrstring[3])).append(" ").append(arrstring[4]).append(" ").append(arrstring[5]).toString()));
            }
            String string = arrstring[6];
            int n = 1;
            try {
                n = Integer.parseInt(arrstring[7]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[7]));
            }
            ItemStack itemStack = customItemsManager.getCustomItem(string);
            if (itemStack == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.CustomItems_Invalid.toMsg().replace("%s", string));
                return;
            }
            Location location = new Location(world, d, d2, d3);
            int n4 = 0;
            while (n4 < n) {
                if (string.equalsIgnoreCase("random")) {
                    itemStack = customItemsManager.getCustomItem(string);
                }
                world.dropItemNaturally(location, itemStack).setPickupDelay(40);
                ++n4;
            }
        } else {
            for (String string : Lang.Help_CustomItems.getList()) {
                commandSender.sendMessage(string.replace("%m_state%", this.plugin.getMM().getColorStatus(customItemsManager.isActive())).replace("%m_ver%", customItemsManager.version()).replace("%m_name%", customItemsManager.name()));
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

