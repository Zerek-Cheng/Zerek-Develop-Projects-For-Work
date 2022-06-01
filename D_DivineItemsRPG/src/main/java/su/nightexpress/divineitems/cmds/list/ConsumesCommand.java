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
import su.nightexpress.divineitems.modules.consumes.ConsumeManager;
import su.nightexpress.divineitems.utils.Utils;

public class ConsumesCommand
extends CommandBase {
    private DivineItems plugin;

    public ConsumesCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        ConsumeManager consumeManager = this.plugin.getMM().getConsumeManager();
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
            Utils.interactiveList(commandSender, n, consumeManager.getConsumeNames(), consumeManager.name(), "1");
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
            ConsumeManager.Consume consume = consumeManager.getConsumeById(string);
            if (consume == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Consumables_Invalid.toMsg().replace("%s", string));
                return;
            }
            int n2 = 0;
            while (n2 < n) {
                if (string.equalsIgnoreCase("random")) {
                    consume = consumeManager.getConsumeById(string);
                }
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), consume.build()).setPickupDelay(40);
                } else {
                    player.getInventory().addItem(new ItemStack[]{consume.build()});
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
            ConsumeManager.Consume consume = consumeManager.getConsumeById(string);
            if (consume == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Consumables_Invalid.toMsg().replace("%s", string));
                return;
            }
            int n3 = 0;
            while (n3 < n) {
                if (string.equalsIgnoreCase("random")) {
                    consume = consumeManager.getConsumeById(string);
                }
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), consume.build()).setPickupDelay(40);
                } else {
                    player.getInventory().addItem(new ItemStack[]{consume.build()});
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
            ConsumeManager.Consume consume = consumeManager.getConsumeById(string);
            if (consume == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Consumables_Invalid.toMsg().replace("%s", string));
                return;
            }
            Location location = new Location(world, d, d2, d3);
            int n4 = 0;
            while (n4 < n) {
                if (string.equalsIgnoreCase("random")) {
                    consume = consumeManager.getConsumeById(string);
                }
                world.dropItemNaturally(location, consume.build()).setPickupDelay(40);
                ++n4;
            }
        } else {
            for (String string : Lang.Help_Consumables.getList()) {
                commandSender.sendMessage(string.replace("%m_state%", this.plugin.getMM().getColorStatus(consumeManager.isActive())).replace("%m_ver%", consumeManager.version()).replace("%m_name%", consumeManager.name()));
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

