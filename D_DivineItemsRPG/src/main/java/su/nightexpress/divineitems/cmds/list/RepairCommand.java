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
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.repair.RepairManager;

public class RepairCommand
extends CommandBase {
    private DivineItems plugin;

    public RepairCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        RepairManager repairManager = this.plugin.getMM().getRepairManager();
        if ((arrstring.length == 2 || arrstring.length == 3) && arrstring[1].equalsIgnoreCase("item")) {
            Player player;
            ItemStack itemStack;
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                return;
            }
            int n = 999999;
            if (arrstring.length == 3) {
                try {
                    n = Integer.parseInt(arrstring[2]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[2]));
                }
            }
            if ((itemStack = (player = (Player)commandSender).getInventory().getItemInMainHand()) == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_NoItem.toMsg());
                return;
            }
            if (ItemAPI.getDurability(itemStack, 0) < 0) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_NoDurability.toMsg());
                return;
            }
            itemStack = ItemAPI.setDurability(itemStack, n, ItemAPI.getDurability(itemStack, 1));
            player.getInventory().setItemInMainHand(itemStack);
        } else if (arrstring.length == 4 && arrstring[1].equalsIgnoreCase("get")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidSender.toMsg());
                return;
            }
            Player player = (Player)commandSender;
            int n = 1;
            int n2 = 1;
            try {
                n = Integer.parseInt(arrstring[2]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[2]));
            }
            try {
                n2 = Integer.parseInt(arrstring[3]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
            }
            RepairManager.RepairGem repairGem = repairManager.getGem();
            int n3 = 0;
            while (n3 < n2) {
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), repairGem.getItemGem(n)).setPickupDelay(40);
                } else {
                    player.getInventory().addItem(new ItemStack[]{repairGem.getItemGem(n)});
                }
                ++n3;
            }
        } else if (arrstring.length == 5 && arrstring[1].equalsIgnoreCase("give")) {
            Player player = Bukkit.getPlayer((String)arrstring[2]);
            if (player == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
                return;
            }
            int n = 1;
            int n4 = 1;
            try {
                n = Integer.parseInt(arrstring[3]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
            }
            try {
                n4 = Integer.parseInt(arrstring[4]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[4]));
            }
            RepairManager.RepairGem repairGem = repairManager.getGem();
            int n5 = 0;
            while (n5 < n4) {
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), repairGem.getItemGem(n)).setPickupDelay(40);
                } else {
                    player.getInventory().addItem(new ItemStack[]{repairGem.getItemGem(n)});
                }
                ++n5;
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
            int n = 1;
            int n6 = 2;
            try {
                n = Integer.parseInt(arrstring[6]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[6]));
            }
            try {
                n6 = Integer.parseInt(arrstring[7]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[7]));
            }
            RepairManager.RepairGem repairGem = repairManager.getGem();
            Location location = new Location(world, d, d2, d3);
            int n7 = 0;
            while (n7 < n6) {
                world.dropItemNaturally(location, repairGem.getItemGem(n)).setPickupDelay(40);
                ++n7;
            }
        } else {
            for (String string : Lang.Help_Repair.getList()) {
                commandSender.sendMessage(string.replace("%m_state%", this.plugin.getMM().getColorStatus(repairManager.isActive())).replace("%m_ver%", repairManager.version()).replace("%m_name%", repairManager.name()));
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

