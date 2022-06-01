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
import su.nightexpress.divineitems.modules.enchant.EnchantManager;
import su.nightexpress.divineitems.utils.Utils;

public class EnchantsCommand
extends CommandBase {
    private DivineItems plugin;

    public EnchantsCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        EnchantManager enchantManager = this.plugin.getMM().getEnchantManager();
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
            Utils.interactiveList(commandSender, n, enchantManager.getEnchantNames(), enchantManager.name(), "-1 1");
        } else if (arrstring.length == 5 && arrstring[1].equalsIgnoreCase("get")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                return;
            }
            Player player = (Player)commandSender;
            String string = arrstring[2];
            EnchantManager.EnchantType enchantType = null;
            try {
                enchantType = EnchantManager.EnchantType.valueOf(string.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(EnchantManager.EnchantType.class, "\u00a7c", "\u00a77")));
                return;
            }
            int n = 1;
            int n2 = 1;
            try {
                n = Integer.parseInt(arrstring[3]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
            }
            try {
                n2 = Integer.parseInt(arrstring[4]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[4]));
            }
            EnchantManager.Enchant enchant = enchantManager.getEnchantByType(enchantType);
            if (enchant == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Invalid.toMsg().replace("%s", string));
                return;
            }
            int n3 = 0;
            while (n3 < n2) {
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), enchant.create(n)).setPickupDelay(40);
                } else {
                    player.getInventory().addItem(new ItemStack[]{enchant.create(n)});
                }
                ++n3;
            }
        } else if (arrstring.length == 6 && arrstring[1].equalsIgnoreCase("give")) {
            Player player = Bukkit.getPlayer((String)arrstring[2]);
            if (player == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
                return;
            }
            String string = arrstring[3];
            EnchantManager.EnchantType enchantType = null;
            if (string.equalsIgnoreCase("random")) {
                string = enchantManager.getRandomEnchant().getId();
            }
            try {
                enchantType = EnchantManager.EnchantType.valueOf(string.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(EnchantManager.EnchantType.class, "\u00a7c", "\u00a77")));
                return;
            }
            int n = 1;
            int n4 = 2;
            try {
                n = Integer.parseInt(arrstring[4]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[4]));
            }
            try {
                n4 = Integer.parseInt(arrstring[5]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[5]));
            }
            EnchantManager.Enchant enchant = enchantManager.getEnchantByType(enchantType);
            if (enchant == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Invalid.toMsg().replace("%s", string));
                return;
            }
            int n5 = 0;
            while (n5 < n4) {
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), enchant.create(n)).setPickupDelay(40);
                } else {
                    player.getInventory().addItem(new ItemStack[]{enchant.create(n)});
                }
                ++n5;
            }
        } else if (arrstring.length == 9 && arrstring[1].equalsIgnoreCase("drop")) {
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
            EnchantManager.EnchantType enchantType = null;
            try {
                enchantType = EnchantManager.EnchantType.valueOf(string.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(EnchantManager.EnchantType.class, "\u00a7c", "\u00a77")));
                return;
            }
            int n = 1;
            int n6 = 2;
            try {
                n = Integer.parseInt(arrstring[7]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[7]));
            }
            try {
                n6 = Integer.parseInt(arrstring[8]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[8]));
            }
            EnchantManager.Enchant enchant = enchantManager.getEnchantByType(enchantType);
            if (enchant == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Enchants_Invalid.toMsg().replace("%s", string));
                return;
            }
            Location location = new Location(world, d, d2, d3);
            int n7 = 0;
            while (n7 < n6) {
                world.dropItemNaturally(location, enchant.create(n)).setPickupDelay(40);
                ++n7;
            }
        } else {
            for (String string : Lang.Help_Enchants.getList()) {
                commandSender.sendMessage(string.replace("%m_state%", this.plugin.getMM().getColorStatus(enchantManager.isActive())).replace("%m_ver%", enchantManager.version()).replace("%m_name%", enchantManager.name()));
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

