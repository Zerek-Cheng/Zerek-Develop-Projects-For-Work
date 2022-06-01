/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.PotionMeta
 *  org.bukkit.potion.PotionEffectType
 */
package su.nightexpress.divineitems.cmds.list;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.nms.VersionUtils;
import su.nightexpress.divineitems.utils.Utils;

public class ModifyCommand
extends CommandBase {
    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        Player player = (Player)commandSender;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidItem.toMsg());
            return;
        }
        if (arrstring.length >= 3 && arrstring[1].equalsIgnoreCase("name")) {
            String string = "";
            int n = 2;
            while (n < arrstring.length) {
                string = String.valueOf(string) + arrstring[n] + " ";
                ++n;
            }
            player.getInventory().setItemInMainHand(ItemAPI.setName(itemStack, string.trim()));
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else if (arrstring.length >= 3 && arrstring[1].equalsIgnoreCase("lore")) {
            if (arrstring[2].equalsIgnoreCase("add") && arrstring.length >= 4) {
                int n;
                String string = "";
                int n2 = n = arrstring.length;
                int n3 = -1;
                if (StringUtils.isNumeric((String)arrstring[n - 1])) {
                    --n2;
                    n3 = Integer.parseInt(arrstring[n - 1]);
                }
                int n4 = 3;
                while (n4 < n2) {
                    string = String.valueOf(string) + arrstring[n4] + " ";
                    ++n4;
                }
                player.getInventory().setItemInMainHand(ItemAPI.addLoreLine(itemStack, string.trim(), n3));
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
            } else if (arrstring[2].equalsIgnoreCase("del") && arrstring.length >= 4) {
                if (StringUtils.isNumeric((String)arrstring[3])) {
                    int n = Integer.parseInt(arrstring[3]);
                    player.getInventory().setItemInMainHand(ItemAPI.delLoreLine(itemStack, n));
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
                }
            } else if (arrstring[2].equalsIgnoreCase("clear")) {
                player.getInventory().setItemInMainHand(ItemAPI.clearLore(itemStack));
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
            } else {
                this.printHelp(player);
            }
        } else if (arrstring.length == 4 && arrstring[1].equalsIgnoreCase("flag")) {
            String string = arrstring[3].toUpperCase();
            ItemFlag itemFlag = null;
            try {
                itemFlag = ItemFlag.valueOf((String)string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(ItemFlag.class, "\u00a7c", "\u00a77")));
                return;
            }
            if (arrstring[2].equalsIgnoreCase("add")) {
                itemStack = ItemAPI.addFlag(itemStack, itemFlag);
                player.getInventory().setItemInMainHand(itemStack);
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
            } else if (arrstring[2].equalsIgnoreCase("del")) {
                itemStack = ItemAPI.delFlag(itemStack, itemFlag);
                player.getInventory().setItemInMainHand(itemStack);
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
            } else {
                this.printHelp(player);
            }
        } else if (arrstring.length >= 4 && arrstring[1].equalsIgnoreCase("nbt")) {
            String string = arrstring[3];
            if (arrstring[2].equalsIgnoreCase("add") && arrstring.length == 5) {
                String string2 = arrstring[4];
                itemStack = ItemAPI.addNBTTag(itemStack, string, string2);
                player.getInventory().setItemInMainHand(itemStack);
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
            } else if (arrstring[2].equalsIgnoreCase("del")) {
                itemStack = ItemAPI.delNBTTag(itemStack, string);
                player.getInventory().setItemInMainHand(itemStack);
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
            } else {
                this.printHelp(player);
            }
        } else if (arrstring.length == 4 && arrstring[1].equalsIgnoreCase("enchant")) {
            String string = arrstring[2].toUpperCase();
            Enchantment enchantment = Enchantment.getByName((String)string);
            if (enchantment == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(Enchantment.class, "\u00a7c", "\u00a77")));
                return;
            }
            int n = 1;
            try {
                n = Integer.parseInt(arrstring[3]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                return;
            }
            itemStack = ItemAPI.enchant(itemStack, enchantment, n);
            player.getInventory().setItemInMainHand(itemStack);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else if (arrstring.length >= 5 && arrstring[1].equalsIgnoreCase("potion")) {
            PotionMeta potionMeta = (PotionMeta)itemStack.getItemMeta();
            if (potionMeta == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_NotAPotion.toMsg());
                return;
            }
            String string = arrstring[2].toUpperCase();
            PotionEffectType potionEffectType = PotionEffectType.getByName((String)string);
            if (potionEffectType == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(PotionEffectType.class, "\u00a7b", "\u00a77")));
                return;
            }
            int n = 1;
            try {
                n = Integer.parseInt(arrstring[3]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                return;
            }
            int n5 = 20;
            try {
                n5 = Integer.parseInt(arrstring[4]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[4]));
                return;
            }
            boolean bl = false;
            boolean bl2 = false;
            if (arrstring.length == 6) {
                bl = Boolean.valueOf(arrstring[5]);
            }
            if (arrstring.length == 7) {
                bl2 = Boolean.valueOf(arrstring[6]);
            }
            if (VersionUtils.Version.getCurrent().isLower(VersionUtils.Version.v1_13_R1)) {
                Color color = Color.WHITE;
                if (arrstring.length == 8) {
                    String[] arrstring2 = arrstring[7].split(",");
                    int n6 = 0;
                    int n7 = 0;
                    int n8 = 0;
                    try {
                        n6 = Integer.parseInt(arrstring2[0]);
                        n7 = Integer.parseInt(arrstring2[1]);
                        n8 = Integer.parseInt(arrstring2[2]);
                    }
                    catch (NumberFormatException numberFormatException) {
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidRGB.toMsg().replace("%s", arrstring[7]));
                        return;
                    }
                    color = Color.fromRGB((int)n6, (int)n7, (int)n8);
                    itemStack = ItemAPI.addPotionEffect(itemStack, potionEffectType, n, n5, bl, bl2, color);
                }
            } else {
                boolean bl3 = false;
                if (arrstring.length == 8) {
                    bl3 = Boolean.valueOf(arrstring[7]);
                }
                itemStack = ItemAPI.addPotionEffect(itemStack, potionEffectType, n, n5, bl, bl2, bl3);
            }
            player.getInventory().setItemInMainHand(itemStack);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("eggtype")) {
            if (!itemStack.getType().name().equalsIgnoreCase("MONSTER_EGG")) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_NotAnEgg.toMsg());
                return;
            }
            String string = arrstring[2].toUpperCase();
            EntityType entityType = null;
            try {
                entityType = EntityType.valueOf((String)string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%s", Utils.getEnums(EntityType.class, "\u00a7c", "\u00a77")));
                return;
            }
            itemStack = ItemAPI.setEggType(itemStack, entityType);
            player.getInventory().setItemInMainHand(itemStack);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("color")) {
            if (!itemStack.getType().name().startsWith("LEATHER_")) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_NotALeather.toMsg());
                return;
            }
            Color color = Color.WHITE;
            String[] arrstring3 = arrstring[2].split(",");
            int n = 0;
            int n9 = 0;
            int n10 = 0;
            try {
                n = Integer.parseInt(arrstring3[0]);
                n9 = Integer.parseInt(arrstring3[1]);
                n10 = Integer.parseInt(arrstring3[2]);
            }
            catch (NumberFormatException numberFormatException) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidRGB.toMsg().replace("%s", arrstring[7]));
                return;
            }
            color = Color.fromRGB((int)n, (int)n9, (int)n10);
            itemStack = ItemAPI.setLeatherColor(itemStack, color);
            player.getInventory().setItemInMainHand(itemStack);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Admin_Set.toMsg());
        } else {
            this.printHelp(player);
        }
    }

    private void printHelp(Player player) {
        for (String string : Lang.Help_Modify.getList()) {
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

