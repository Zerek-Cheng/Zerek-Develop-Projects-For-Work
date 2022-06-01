/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package su.nightexpress.divineitems.cmds.list;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.ability.AbilityManager;
import su.nightexpress.divineitems.types.AmmoType;
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.types.DamageType;
import su.nightexpress.divineitems.types.SlotType;
import su.nightexpress.divineitems.utils.Utils;

public class SetCommand
extends CommandBase {
    private DivineItems plugin;

    public SetCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        Player player = (Player)commandSender;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidItem.toMsg());
            return;
        }
        int n = -1;
        if (arrstring.length == 4 && StringUtils.isNumeric((String)arrstring[3])) {
            n = Integer.parseInt(arrstring[3]);
        } else if (arrstring.length == 5 && StringUtils.isNumeric((String)arrstring[4])) {
            n = Integer.parseInt(arrstring[4]);
        } else if (arrstring.length == 6 && StringUtils.isNumeric((String)arrstring[5])) {
            n = Integer.parseInt(arrstring[5]);
        } else if (arrstring.length == 8 && StringUtils.isNumeric((String)arrstring[7])) {
            n = Integer.parseInt(arrstring[7]);
        }
        if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("level")) {
            int n2 = -1;
            if (StringUtils.isNumeric((String)arrstring[2])) {
                n2 = Integer.parseInt(arrstring[2]);
            }
            player.getInventory().setItemInMainHand(ItemAPI.setLevelRequired(itemStack, n2, n));
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("slot")) {
            SlotType slotType = null;
            try {
                slotType = SlotType.valueOf(arrstring[2].toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(SlotType.class, "\u00a7c", "\u00a77")));
                return;
            }
            itemStack = ItemAPI.addDivineSlot(itemStack, slotType, -1);
            player.getInventory().setItemInMainHand(itemStack);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("class")) {
            player.getInventory().setItemInMainHand(ItemAPI.setClassRequired(itemStack, arrstring[2], n));
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else {
            if (arrstring.length == 5 && arrstring[1].equalsIgnoreCase("damagetype")) {
                double d = 0.0;
                double d2 = 0.0;
                try {
                    d = Double.parseDouble(arrstring[3]);
                    d2 = Double.parseDouble(arrstring[4]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                    return;
                }
                DamageType damageType = this.plugin.getCFG().getDamageTypes().get(arrstring[2].toLowerCase());
                if (damageType == null) {
                    commandSender.sendMessage("xyu");
                    return;
                }
                player.getInventory().setItemInMainHand(ItemAPI.addDamageType(itemStack, damageType, d, d2, n));
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
                return;
            }
            if (arrstring.length == 4 && (arrstring[1].equalsIgnoreCase("armortype") || arrstring[1].equalsIgnoreCase("defensetype"))) {
                double d = -1.0;
                try {
                    d = Double.parseDouble(arrstring[3]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                    return;
                }
                ArmorType armorType = this.plugin.getCFG().getArmorTypes().get(arrstring[2].toLowerCase());
                player.getInventory().setItemInMainHand(ItemAPI.addDefenseType(itemStack, armorType, d, n));
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
                return;
            }
            if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("ammotype")) {
                AmmoType ammoType;
                try {
                    ammoType = AmmoType.valueOf(arrstring[2].toUpperCase());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(AmmoType.class, "\u00a7a", "\u00a77")));
                    return;
                }
                player.getInventory().setItemInMainHand(ItemAPI.setAmmoType(itemStack, ammoType, n));
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
                return;
            }
            if ((arrstring.length == 5 || arrstring.length == 6) && arrstring[1].equalsIgnoreCase("attribute")) {
                ItemStat itemStat = null;
                try {
                    itemStat = ItemStat.valueOf(arrstring[2].toUpperCase());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(ItemStat.class, "\u00a7a", "\u00a77")));
                    return;
                }
                player.getInventory().setItemInMainHand(ItemAPI.addAttribute(itemStack, itemStat, false, arrstring[3], arrstring[4], n));
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_AttributeSet.toMsg().replace("%s", arrstring[2]));
            } else if (arrstring.length == 4 && arrstring[1].equalsIgnoreCase("bonus")) {
                ItemStat itemStat = null;
                try {
                    itemStat = ItemStat.valueOf(arrstring[2].toUpperCase());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(ItemStat.class, "\u00a7a", "\u00a77")));
                    return;
                }
                player.getInventory().setItemInMainHand(ItemAPI.addAttribute(itemStack, itemStat, true, arrstring[3], "", n));
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_AttributeSet.toMsg().replace("%s", arrstring[2]));
            } else if (arrstring.length == 7 && arrstring[1].equalsIgnoreCase("ability")) {
                String string = arrstring[2].toLowerCase();
                if (this.plugin.getMM().getAbilityManager().getAbilityById(string) == null) {
                    commandSender.sendMessage("\u00a7cAbility \u00a7f" + string + "\u00a7c does not exist!");
                    return;
                }
                int n3 = 1;
                try {
                    n3 = Integer.parseInt(arrstring[3]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                    return;
                }
                String string2 = arrstring[4];
                if (!string2.equalsIgnoreCase("RIGHT") && !string2.equalsIgnoreCase("LEFT")) {
                    commandSender.sendMessage("\u00a7cKey value must be \u00a7fLEFT \u00a7cor \u00a7fRIGHT");
                    string2 = "LEFT";
                }
                boolean bl = Boolean.valueOf(arrstring[5]);
                int n4 = 5;
                try {
                    n4 = Integer.parseInt(arrstring[6]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[6]));
                    return;
                }
                player.getInventory().setItemInMainHand(ItemAPI.addAbility(itemStack, string, n3, string2, bl, n4, n));
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_AttributeSet.toMsg().replace("%s", arrstring[2]));
            } else {
                this.printHelp(player);
            }
        }
    }

    private void printHelp(Player player) {
        for (String string : Lang.Help_Set.getList()) {
            player.sendMessage(string);
        }
    }

    @Override
    public String getPermission() {
        return "divineitems.admin";
    }

    @Override
    public boolean playersOnly() {
        return true;
    }
}

