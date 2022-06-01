/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  lk.vexview.api.VexViewAPI
 *  lk.vexview.gui.VexGui
 *  lk.vexview.gui.components.VexComponents
 *  lk.vexview.gui.components.VexHoverText
 *  lk.vexview.gui.components.VexImage
 *  lk.vexview.gui.components.VexSlot
 *  lk.vexview.gui.components.VexText
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package cc.kunss.vexst.utils;

import cc.kunss.vexst.managers.AilerCuiLianManager;
import cc.kunss.vexst.utils.GuiFile;
import cc.kunss.vexst.utils.Message;
import cc.kunss.vexst.utils.VexGuis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexComponents;
import lk.vexview.gui.components.VexHoverText;
import lk.vexview.gui.components.VexImage;
import lk.vexview.gui.components.VexSlot;
import lk.vexview.gui.components.VexText;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ResultGui
extends GuiFile {
    public static void ResultGuiOpen(Player p, ItemStack item, ItemStack StrengStone, ItemStack LuckyStone, ItemStack PStone, boolean b, boolean bb, AilerCuiLianManager ailerCuiLianManager, String exp) {
        VexViewAPI.openGui((Player)p, (VexGui)ResultGui.ResultGui(p, item, StrengStone, LuckyStone, PStone, b, bb, ailerCuiLianManager, exp));
    }

    public static VexGui ResultGui(Player p, ItemStack item, ItemStack StrengStone, ItemStack LuckyStone, ItemStack PStone, boolean b, boolean bb, AilerCuiLianManager ailerCuiLianManager, String exp) {
        VexGui vexGui = new VexGui(ResultGui.getData().getString("ResultGui.Gui.url"), ResultGui.getData().getInt("ResultGui.Gui.x"), ResultGui.getData().getInt("ResultGui.Gui.y"), ResultGui.getData().getInt("ResultGui.Gui.width"), ResultGui.getData().getInt("ResultGui.Gui.high"), ResultGui.getData().getInt("ResultGui.Gui.xshow"), ResultGui.getData().getInt("ResultGui.Gui.yshow"));
        ResultGui.getSlot(vexGui, "ResultGui.ItemSlot", item);
        ResultGui.getSlot(vexGui, "ResultGui.StoneSlot", StrengStone);
        ResultGui.getSlot(vexGui, "ResultGui.LockyStone", LuckyStone);
        ResultGui.getSlot(vexGui, "ResultGui.ProtectionStoneSlot", PStone);
        ResultGui.vexButton(vexGui, "ResultGui.button");
        ResultGui.text(vexGui, b, bb, "ResultGui.text", item, ailerCuiLianManager, exp);
        p.getInventory().addItem(new ItemStack[]{item, StrengStone, LuckyStone, PStone});
        return vexGui;
    }

    public static void getSlot(VexGui vexGui, String Path2, ItemStack item) {
        VexSlot vexSlot = new VexSlot(ResultGui.getData().getInt(Path2 + ".slot.id"), ResultGui.getData().getInt(Path2 + ".slot.x"), ResultGui.getData().getInt(Path2 + ".slot.y"), item);
        VexHoverText vexHoverText = new VexHoverText(ResultGui.getData().getStringList(Path2 + ".image.text"), ResultGui.getData().getInt(Path2 + ".image.x"), ResultGui.getData().getInt(Path2 + ".image.y"), ResultGui.getData().getInt(Path2 + ".image.width"), ResultGui.getData().getInt(Path2 + ".image.high"));
        VexImage vexImage = new VexImage(ResultGui.getData().getString(Path2 + ".image.url"), ResultGui.getData().getInt(Path2 + ".image.x"), ResultGui.getData().getInt(Path2 + ".image.y"), ResultGui.getData().getInt(Path2 + ".image.width"), ResultGui.getData().getInt(Path2 + ".image.high"), ResultGui.getData().getInt(Path2 + ".image.xshow"), ResultGui.getData().getInt(Path2 + ".image.yshow"), vexHoverText);
        vexGui.addComponent((VexComponents)vexImage);
        vexGui.addComponent((VexComponents)vexSlot);
    }

    public static void text(VexGui gui, boolean b, boolean bb, String Path2, ItemStack item, AilerCuiLianManager ailerCuiLianManager, String exp) {
        VexText text = new VexText(ResultGui.getData().getInt(Path2 + ".x"), ResultGui.getData().getInt(Path2 + ".y"), new ArrayList());
        List<String> messlist = new ArrayList();
        if (b) {
            messlist = Message.getMessageList("GUIMessage.StrengSuccess");
            messlist = VexGuis.ReplaceList(messlist, "{i}", item.getItemMeta().getDisplayName());
            messlist = VexGuis.ReplaceList(messlist, "{t}", ailerCuiLianManager.getType());
        } else if (bb) {
            messlist = Message.getMessageList("GUIMessage.StrengLoseisDefeat");
            messlist = VexGuis.ReplaceList(messlist, "{i}", item.getItemMeta().getDisplayName());
            messlist = VexGuis.ReplaceList(messlist, "{t}", ailerCuiLianManager.getType());
        } else {
            messlist = Message.getMessageList("GUIMessage.StrengDefeat");
            messlist = VexGuis.ReplaceList(messlist, "{i}", item.getItemMeta().getDisplayName());
            messlist = VexGuis.ReplaceList(messlist, "{t}", ailerCuiLianManager.getType());
        }
        messlist = VexGuis.ReplaceList(messlist, "{exp}", exp);
        text.getText().clear();
        text.getText().addAll(messlist);
        gui.addComponent((VexComponents)text);
    }
}

