/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package cc.kunss.vexst.utils;

import cc.kunss.vexst.api.VexStrengThenAPI;
import cc.kunss.vexst.data.LRVexText;
import cc.kunss.vexst.listeners.Click;
import cc.kunss.vexst.managers.AilerCuiLianManager;
import cc.kunss.vexst.managers.LevelAddition;
import cc.kunss.vexst.managers.StoneManager;
import cc.kunss.vexst.managers.StrengLevel;
import cc.kunss.vexst.utils.Message;
import cc.kunss.vexst.utils.Methods;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class VexGuis
extends Click {
    public static LRVexText sendInfoMessage(ItemStack itemStack, LRVexText vexText, Player p) {
        List<String> lore;
        if (!VexGuis.isItem(itemStack)) {
            lore = Message.getMessageList("GUIMessage.Default");
        } else if (VexGuis.isItemStreng(itemStack)) {
            AilerCuiLianManager ailerCuiLianManager = VexGuis.getItemStreng(itemStack);
            lore = Message.getMessageList("GUIMessage.sendItem.ItemisYesStreng");
            lore = VexGuis.ReplaceLists(lore, ailerCuiLianManager, itemStack, p);
        } else {
            lore = Message.getMessageList("GUIMessage.sendItem.ItemisNoStreng");
            lore = VexGuis.ReplaceList(lore, "{i}", itemStack.getItemMeta().getDisplayName());
        }
        vexText.getText().clear();
        vexText.getText().addAll(lore);
        return vexText;
    }

    public static List<String> ReplaceList(List<String> lore, String re1, String re2) {
        ArrayList<String> lo = new ArrayList<String>();
        for (String key : lore) {
            try {
                lo.add(key.replace(re1, re2));
            }
            catch (NullPointerException e) {
                lo.add(key.replace(re1, VexGuis.getReplaceString("isnull")));
            }
        }
        return lo;
    }

    public static List<String> ReplaceLists(List<String> lore, AilerCuiLianManager ailerCuiLianManager, ItemStack itemStack, Player p) {
        ArrayList<String> lo = new ArrayList<String>();
        for (String key : lore) {
            if (key.contains("{$}")) {
                for (StoneManager stone : ailerCuiLianManager.getStoneManagerIntegerMap().keySet()) {
                    lo.add(key.replace("{stone}", stone.getShowName()).replace("{amout}", String.valueOf(ailerCuiLianManager.getStoneManagerIntegerMap().get(stone))).replace("{isamout}", VexGuis.isStone(stone, p)).replace("{$}", "").replace("{p}", ailerCuiLianManager.getPermission()));
                }
                continue;
            }
            boolean isper = false;
            if (p.hasPermission(ailerCuiLianManager.getPermission()) && !ailerCuiLianManager.getPermission().equalsIgnoreCase("noon")) {
                isper = true;
            }
            LevelAddition addition = VexGuis.getPlayerLevelAddition(p, ailerCuiLianManager);
            double lmoney = addition.getMoney();
            double lpoints = addition.getPoints();
            double lpt = addition.getPt();
            double lluck = addition.getLucky();
            lo.add(key.replace("{i}", itemStack.getItemMeta().getDisplayName()).replace("{t}", ailerCuiLianManager.getType()).replace("{vm}", String.valueOf(ailerCuiLianManager.getMoney() - lmoney)).replace("{pm}", String.valueOf(ailerCuiLianManager.getPoints() - lpoints)).replace("{defaultrandom}", VexGuis.getNumber(ailerCuiLianManager.getDefaulerandom() + lluck)).replace("{b}", VexGuis.isBoolean(ailerCuiLianManager.isLoselevel())).replace("{p}", ailerCuiLianManager.getPermissionprefix()).replace("{isp}", VexGuis.isBoolean(isper)).replace("{vp}", VexGuis.getReplaceString("Vault")).replace("{pp}", VexGuis.getReplaceString("Points")).replace("{lvm}", String.valueOf(lmoney)).replace("{lpm}", String.valueOf(lpoints)).replace("{dvm}", String.valueOf(ailerCuiLianManager.getMoney())).replace("{dpm}", String.valueOf(ailerCuiLianManager.getPoints())).replace("{lrandom}", VexGuis.getNumber(lluck)).replace("{drandom}", VexGuis.getNumber(ailerCuiLianManager.getDefaulerandom())).replace("{plevel}", String.valueOf(VexStrengThenAPI.getPlayerStrengLevel(p))).replace("{slevel}", String.valueOf(ailerCuiLianManager.getLevel()).replace("{slevelprefix}", Methods.getStrengLevelMap().get(ailerCuiLianManager.getLevel()).getPrefix()).replace("{plevelprefix}", VexStrengThenAPI.getPlayerStrengPrefix(p))));
        }
        return lo;
    }

    public static LevelAddition getPlayerLevelAddition(Player p, AilerCuiLianManager a) {
        int level = VexStrengThenAPI.getPlayerStrengLevel(p);
        LevelAddition addition = a.getLevelAdditionMap().get(level);
        return addition;
    }

    public static String isBoolean(boolean b) {
        if (b) {
            return Message.getMessageString("ReplaceString.true");
        }
        return Message.getMessageString("ReplaceString.false");
    }

    public static String getReplaceString(String Path2) {
        return Message.getMessageString("ReplaceString." + Path2);
    }

    public static String isStone(StoneManager stoneManager, Player p) {
        int amout = 0;
        for (int i = 0; i < p.getInventory().getSize(); ++i) {
            ItemStack itemStack = p.getInventory().getItem(i);
            try {
                if (!itemStack.getItemMeta().getLore().contains(stoneManager.getLore())) continue;
                amout += itemStack.getAmount();
                continue;
            }
            catch (NullPointerException e) {
                // empty catch block
            }
        }
        return String.valueOf(amout);
    }
}

