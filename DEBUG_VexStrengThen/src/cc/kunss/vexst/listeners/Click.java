/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  lk.vexview.api.VexViewAPI
 *  lk.vexview.event.ButtonClickEvent
 *  lk.vexview.gui.OpenedVexGui
 *  lk.vexview.gui.VexGui
 *  lk.vexview.gui.components.DynamicComponent
 *  lk.vexview.gui.components.VexSlot
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package cc.kunss.vexst.listeners;

import cc.kunss.vexst.Main;
import cc.kunss.vexst.api.VexStrengThenAPI;
import cc.kunss.vexst.data.LRVexText;
import cc.kunss.vexst.managers.*;
import cc.kunss.vexst.utils.*;
import lk.vexview.api.VexViewAPI;
import lk.vexview.event.ButtonClickEvent;
import lk.vexview.gui.OpenedVexGui;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.DynamicComponent;
import lk.vexview.gui.components.VexSlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Click
implements Listener {
    @EventHandler
    public void onClick(ButtonClickEvent e) {
        if (e.getButtonID() == GuiFile.getVexButton("ButtonVault").getId()) {
            Click.PayType("Vault", e.getPlayer());
        } else if (e.getButtonID() == GuiFile.getVexButton("ButtonPoints").getId()) {
            Click.PayType("Points", e.getPlayer());
        }
    }

    public static void PayType(String Type, Player p) {
        OpenedVexGui vig = VexViewAPI.getPlayerCurrentGui((Player)p);
        VexGui gui = vig.getVexGui();
        VexSlot ItemSlot = gui.getSlotById(GuiFile.getVexSlot("Item").getID());
        VexSlot StrengStoneSlot = gui.getSlotById(GuiFile.getVexSlot("Stone").getID());
        VexSlot LuckStone = gui.getSlotById(GuiFile.getVexSlot("LockyStone").getID());
        VexSlot ProtectionStoneSlot = gui.getSlotById(GuiFile.getVexSlot("ProtectionStone").getID());
        ItemStack itemStack = ItemSlot.getItem();
        ItemStack StrengStone = StrengStoneSlot.getItem();
        ItemStack LuckyStone = LuckStone.getItem();
        ItemStack ProtectionStoneStone = ProtectionStoneSlot.getItem();
        LRVexText lrVexText = VexSlotClick.getVexText(gui, "OutPutText.text");
        if (Click.isItem(itemStack) && Click.isItem(StrengStone)) {
            if (Click.isItemStreng(itemStack)) {
                AilerCuiLianManager ailerCuiLianManager = Click.getItemStreng(itemStack);
                LevelAddition addition = Click.getPlayerLevelAddition(p, ailerCuiLianManager);
                if (Click.isItemStrengID(ailerCuiLianManager, itemStack)) {
                    if (!p.hasPermission(ailerCuiLianManager.getPermission()) && !ailerCuiLianManager.getPermission().equalsIgnoreCase("noon")) {
                        List<String> mess = Message.getMessageList("GUIMessage.PlayerisPermission");
                        Methods.ReplaceList(mess, "{p}", ailerCuiLianManager.getPermissionprefix());
                        Click.sendMessageText(lrVexText, mess, vig);
                        return;
                    }
                    if (VexStrengThenAPI.getPlayerStrengLevel(p) < ailerCuiLianManager.getLevel()) {
                        List<String> mess = Message.getMessageList("GUIMessage.isLevel");
                        Methods.ReplaceList(mess, "{l}", String.valueOf(ailerCuiLianManager.getLevel()));
                        Click.sendMessageText(lrVexText, mess, vig);
                        return;
                    }
                    if (Type.equalsIgnoreCase("Vault")) {
                        double money = ailerCuiLianManager.getMoney() - addition.getMoney();
                        if (VaultAPI.getMoney(p.getName()) < money) {
                            List<String> mess = Message.getMessageList("GUIMessage.Vault");
                            Methods.ReplaceList(mess, "{v}", String.valueOf(money));
                            Click.sendMessageText(lrVexText, mess, vig);
                            return;
                        }
                        VaultAPI.removeMoney(p.getName(), money);
                    } else if (Type.equalsIgnoreCase("Points")) {
                        double points = ailerCuiLianManager.getPoints() - addition.getPoints();
                        if ((double)PlayerPointAPIs.getPloits().look(p.getUniqueId()) < points) {
                            List<String> mess = Message.getMessageList("GUIMessage.Points");
                            Methods.ReplaceList(mess, "{v}", String.valueOf(points));
                            Click.sendMessageText(lrVexText, mess, vig);
                            return;
                        }
                        PlayerPointAPIs.getPloits().take(p.getUniqueId(), (int)points);
                    }
                    if (Click.isStone(StrengStone)) {
                        StoneManager stoneManager = Click.getStone(StrengStone);
                        if (StrengStone.getAmount() >= ailerCuiLianManager.getStoneManagerIntegerMap().get(stoneManager)) {
                            double DefauleRandom = ailerCuiLianManager.getDefaulerandom() + addition.getLucky();
                            if (Click.isItem(LuckyStone)) {
                                if (Click.isLockyStone(LuckyStone)) {
                                    DefauleRandom += Click.RandomDouble(Click.getLuckyStone(LuckyStone));
                                } else {
                                    Click.sendMessageText(lrVexText, Message.getMessageList("GUIMessage.LuckyStoneis"), vig);
                                    return;
                                }
                            }
                            boolean loseenable = false;
                            if (Click.isItem(ProtectionStoneStone)) {
                                if (Click.isProtectionStone(ProtectionStoneStone)) {
                                    loseenable = true;
                                } else {
                                    Click.sendMessageText(lrVexText, Message.getMessageList("GUIMessage.ProtectionStoneis"), vig);
                                    return;
                                }
                            }
                            boolean lose = false;
                            double random = new Random().nextDouble() * 1.0;
                            GiveExp giveExp = ailerCuiLianManager.getGiveExp();
                            Click.RemovePlayerItem(p, Arrays.asList(new ItemStack[]{ItemSlot.getItem(), LuckStone.getItem(), ProtectionStoneSlot.getItem(), StrengStoneSlot.getItem()}));
                            LuckyStone.setAmount(LuckyStone.getAmount() - 1);
                            StrengStone.setAmount(StrengStone.getAmount() - ailerCuiLianManager.getStoneManagerIntegerMap().get(stoneManager));
                            ProtectionStoneStone.setAmount(ProtectionStoneStone.getAmount() - 1);
                            if (random < DefauleRandom) {
                                itemStack = Click.ItemStrengSuccess(ailerCuiLianManager.getLore(), Click.getItemStrengReplaceLoreList(ailerCuiLianManager, itemStack), itemStack);
                                double exp = Click.RandomDouble(giveExp.getSuccessMin(), giveExp.getSuccessMax());
                                ResultGui.ResultGuiOpen(p, itemStack, StrengStone, LuckyStone, ProtectionStoneStone, true, false, ailerCuiLianManager, Click.getDecimalFormat(exp));
                                if (ailerCuiLianManager.isMessageEnable()) {
                                    VexStrengThenAPI.givePlayerExp(p, exp);
                                    String mess = Message.getMessageString("StrengMessage").replace("{p}", p.getName()).replace("{i}", itemStack.getItemMeta().getDisplayName()).replace("{t}", ailerCuiLianManager.getType().replace("{gexp}", Click.getDecimalFormat(exp)));
                                    Bukkit.broadcastMessage((String)mess);
                                }
                                if (ailerCuiLianManager.isCmdenable()) {
                                    Click.sendCommand(p, ailerCuiLianManager);
                                }
                                p.playSound(p.getLocation(), Sound.valueOf((String)Message.getMessageString("Sound.Success")), 1.0f, 1.0f);
                            } else {
                                double exp = Click.RandomDouble(giveExp.getDefeatMin(), giveExp.getDefeatMax());
                                if (ailerCuiLianManager.isLoselevel()) {
                                    double losedefaultrandom = ailerCuiLianManager.getLoserandom() + addition.getPt();
                                    double random2 = new Random().nextDouble() * 1.0;
                                    if (loseenable) {
                                        ProtectionStoneManager ptsm = Click.getprotectionStoneManager(ProtectionStoneStone);
                                        if (random2 > (losedefaultrandom -= Click.RandomDouble(ptsm.getMin(), ptsm.getMax()))) {
                                            AilerCuiLianManager ailerCuiLianManager1 = Methods.getAilerCuiLianManagerMap().get(ailerCuiLianManager.getLosetype());
                                            itemStack = Click.ItemStrengSuccess(ailerCuiLianManager.getLore(), Click.getItemStrengReplaceLoreList(ailerCuiLianManager1, itemStack), itemStack);
                                            lose = true;
                                        }
                                    } else if (random2 > losedefaultrandom) {
                                        AilerCuiLianManager ailerCuiLianManager1 = Methods.getAilerCuiLianManagerMap().get(ailerCuiLianManager.getLosetype());
                                        itemStack = Click.ItemStrengSuccess(ailerCuiLianManager.getLore(), Click.getItemStrengReplaceLoreList(ailerCuiLianManager1, itemStack), itemStack);
                                        lose = true;
                                    }
                                }
                                VexStrengThenAPI.givePlayerExp(p, exp);
                                ResultGui.ResultGuiOpen(p, itemStack, StrengStone, LuckyStone, ProtectionStoneStone, false, lose, ailerCuiLianManager, Click.getDecimalFormat(exp));
                                p.playSound(p.getLocation(), Sound.valueOf((String)Message.getMessageString("Sound.Defeat")), 1.0f, 1.0f);
                            }
                        } else {
                            Click.sendMessageText(lrVexText, Message.getMessageList("GUIMessage.ItemisStoneAmout"), vig);
                        }
                    } else {
                        Click.sendMessageText(lrVexText, Message.getMessageList("GUIMessage.StoneExistx"), vig);
                    }
                } else {
                    Click.sendMessageText(lrVexText, Message.getMessageList("GUIMessage.ItemisStrengNO"), vig);
                }
            } else {
                Click.sendMessageText(lrVexText, Message.getMessageList("GUIMessage.ItemisStrengNO"), vig);
            }
        } else {
            Click.sendMessageText(lrVexText, Message.getMessageList("GUIMessage.ItemNull"), vig);
        }
    }

    public static String getDecimalFormat(double s) {
        DecimalFormat formatter = new DecimalFormat(Main.getMain().getConfig().getString("DecimalFormat"));
        return formatter.format(s);
    }

    public static LevelAddition getPlayerLevelAddition(Player p, AilerCuiLianManager ailerCuiLianManager) {
        int level = VexStrengThenAPI.getPlayerStrengLevel(p);
        return ailerCuiLianManager.getLevelAdditionMap().get(level);
    }

    public static LRVexText getvt(LRVexText text, List<String> addmess) {
        List lore = text.getText();
        lore.addAll(addmess);
        if (lore.size() >= text.getSize()) {
            int ss = lore.size() - text.getSize();
            for (int i = 0; i <= ss; ++i) {
                lore.remove(i);
            }
        }
        text.getText().clear();
        text.getText().addAll(lore);
        //text.setL(lore);
        return text;
    }

    public static void sendMessageText(LRVexText text, List<String> sendmess, OpenedVexGui openedVexGui) {
        openedVexGui.removeDynamicComponent((DynamicComponent)text);
        LRVexText lrVexText = Click.getvt(text, sendmess);
        openedVexGui.addDynamicComponent((DynamicComponent)lrVexText);
    }

    public static boolean getListDiffrent(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        if (list1.size() != list2.size()) {
            return false;
        }
        return !list1.retainAll(list2);
    }

    public static void sendCommand(Player p, AilerCuiLianManager ailerCuiLianManager) {
        for (String cmd : ailerCuiLianManager.getCmdlist()) {
            cmd = cmd.replace("{p}", p.getName());
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), cmd);
        }
    }

    public static String getNumber(double a) {
        double result1 = a;
        DecimalFormat df = new DecimalFormat("0.00%");
        String r = df.format(result1);
        return r;
    }

    public static void RemovePlayerItem(Player p, List<ItemStack> items) {
        ArrayList<ItemStack> itemstats = new ArrayList<ItemStack>();
        for (ItemStack item : items) {
            if (!Click.isItem(item)) continue;
            itemstats.add(item);
        }
        for (int i = 0; i < p.getInventory().getSize(); ++i) {
            ItemStack item;
            item = p.getInventory().getItem(i);
            if (!Click.isItem(item) || !itemstats.contains((Object)item)) continue;
            p.getInventory().setItem(i, null);
            itemstats.remove((Object)item);
        }
    }

    public static ProtectionStoneManager getprotectionStoneManager(ItemStack item) {
        for (ProtectionStoneManager protectionStoneManager : Methods.getProtectionStoneManagerMap().values()) {
            if (!item.getItemMeta().getLore().contains(protectionStoneManager.getLore())) continue;
            return protectionStoneManager;
        }
        return null;
    }

    public static boolean isProtectionStone(ItemStack item) {
        for (ProtectionStoneManager protectionStoneManager : Methods.getProtectionStoneManagerMap().values()) {
            if (!item.getItemMeta().getLore().contains(protectionStoneManager.getLore())) continue;
            return true;
        }
        return false;
    }

    public static double RandomDouble(LuckyStoneManager l) {
        double random = new Random().nextDouble() * (l.getMax() - l.getMin()) + l.getMin();
        return random;
    }

    public static double RandomDouble(double min, double max) {
        double random = new Random().nextDouble() * (max - min) + min;
        return random;
    }

    public static boolean isItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }
        return true;
    }

    public static boolean isLockyStone(ItemStack item) {
        for (LuckyStoneManager luckyStoneManager : Methods.getLuckyStoneManagerMap().values()) {
            if (!item.getItemMeta().getLore().contains(luckyStoneManager.getLore())) continue;
            return true;
        }
        return false;
    }

    public static LuckyStoneManager getLuckyStone(ItemStack item) {
        for (LuckyStoneManager luckyStoneManager : Methods.getLuckyStoneManagerMap().values()) {
            if (!item.getItemMeta().getLore().contains(luckyStoneManager.getLore())) continue;
            return luckyStoneManager;
        }
        return null;
    }

    public static ItemStack ItemStrengSuccess(String lores, List<String> replacelore, ItemStack item) {
        ItemStack itemStack = item;
        ItemMeta m = itemStack.getItemMeta();
        ArrayList<String> loress = new ArrayList<String>();
        for (String ss : m.getLore()) {
            if (ss.equalsIgnoreCase(lores)) {
                loress.addAll(replacelore);
                break;
            }
            loress.add(ss);
        }
        m.setLore(loress);
        itemStack.setItemMeta(m);
        return itemStack;
    }

    public static boolean isStone(ItemStack item) {
        for (String key : Methods.getStringStoneManagerMap().keySet()) {
            StoneManager manager = Methods.getStringStoneManagerMap().get(key);
            if (!item.getItemMeta().getLore().contains(manager.getLore())) continue;
            return true;
        }
        return false;
    }

    public static StoneManager getStone(ItemStack item) {
        for (String key : Methods.getStringStoneManagerMap().keySet()) {
            StoneManager manager = Methods.getStringStoneManagerMap().get(key);
            if (!item.getItemMeta().getLore().contains(manager.getLore())) continue;
            return manager;
        }
        return null;
    }

    public static List<String> getItemStrengReplaceIDList(AilerCuiLianManager ailerCuiLianManager, ItemStack item) {
        for (List<String> idlist : ailerCuiLianManager.getReplaceLorelist().keySet()) {
            if (!idlist.contains(String.valueOf(item.getTypeId())) && !idlist.contains("\\*")) continue;
            return idlist;
        }
        return null;
    }

    public static List<String> getItemStrengReplaceLoreList(AilerCuiLianManager ailerCuiLianManager, ItemStack item) {
        for (List<String> idlist : ailerCuiLianManager.getReplaceLorelist().keySet()) {
            if (!idlist.contains(String.valueOf(item.getTypeId())) && !idlist.contains("\\*")) continue;
            return ailerCuiLianManager.getReplaceLorelist().get(idlist);
        }
        return null;
    }

    public static boolean isItemStrengID(AilerCuiLianManager ailerCuiLianManager, ItemStack item) {
        for (List<String> idlist : ailerCuiLianManager.getReplaceLorelist().keySet()) {
            if (!idlist.contains(String.valueOf(item.getTypeId())) && !idlist.contains("\\*")) continue;
            return true;
        }
        return false;
    }

    public static AilerCuiLianManager getItemStreng(ItemStack item) {
        for (String key : Methods.getAilerCuiLianManagerMap().keySet()) {
            AilerCuiLianManager ailerCuiLianManager = Methods.getAilerCuiLianManagerMap().get(key);
            if (!item.getItemMeta().getLore().contains(ailerCuiLianManager.getLore())) continue;
            return ailerCuiLianManager;
        }
        return null;
    }

    public static boolean isItemStreng(ItemStack item) {
        if (item.getItemMeta().getLore() == null) {
            return false;
        }
        for (String key : Methods.getAilerCuiLianManagerMap().keySet()) {
            AilerCuiLianManager ailerCuiLianManager = Methods.getAilerCuiLianManagerMap().get(key);
            if (!item.getItemMeta().getLore().contains(ailerCuiLianManager.getLore())) continue;
            return true;
        }
        return false;
    }
}

