/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  lk.vexview.api.VexViewAPI
 *  lk.vexview.gui.VexGui
 *  lk.vexview.gui.VexInventoryGui
 *  lk.vexview.gui.components.VexButton
 *  lk.vexview.gui.components.VexComponents
 *  lk.vexview.gui.components.VexHoverText
 *  lk.vexview.gui.components.VexImage
 *  lk.vexview.gui.components.VexSlot
 *  lk.vexview.gui.components.VexText
 *  lk.vexview.gui.components.expand.VexGifImage
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package cc.kunss.vexst.utils;

import cc.kunss.vexst.Main;
import cc.kunss.vexst.data.LRVexText;
import cc.kunss.vexst.utils.LRVexGuiComponent;
import cc.kunss.vexst.utils.VexGuis;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.VexInventoryGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexComponents;
import lk.vexview.gui.components.VexHoverText;
import lk.vexview.gui.components.VexImage;
import lk.vexview.gui.components.VexSlot;
import lk.vexview.gui.components.VexText;
import lk.vexview.gui.components.expand.VexGifImage;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GuiFile {
    private static File f = new File(Main.getMain().getDataFolder(), "StrengThenGui.yml");
    private static YamlConfiguration data = YamlConfiguration.loadConfiguration((File)f);

    public static String getMessageString(String Path2) {
        return data.getString(Path2).replace("&", "\u00a7");
    }

    public static int getMessageInt(String Path2) {
        return data.getInt(Path2);
    }

    public static void opengui(Player p) {
        VexViewAPI.openGui((Player)p, (VexGui)GuiFile.getGUI());
    }

    public static VexGui getGUI() {
        VexInventoryGui inv = new VexInventoryGui(data.getString("MenuGui.url"), data.getInt("MenuGui.x"), data.getInt("MenuGui.y"), data.getInt("MenuGui.width"), data.getInt("MenuGui.high"), data.getInt("MenuGui.xshow"), data.getInt("MenuGui.yshow"), data.getInt("MenuGui.slotleft"), data.getInt("MenuGui.slottop"));
        GuiFile.vexInfoImage((VexGui)inv);
        LRVexText vexText = VexGuis.sendInfoMessage(new ItemStack(Material.AIR), LRVexGuiComponent.getLRVexText("InfoBar.text", new ArrayList<String>()), null);
        inv.addComponent((VexComponents)vexText);
        GuiFile.StrengMessage((VexGui)inv);
        GuiFile.vexSlot((VexGui)inv, "Item");
        GuiFile.vexSlot((VexGui)inv, "Stone");
        GuiFile.vexSlot((VexGui)inv, "LockyStone");
        GuiFile.vexSlot((VexGui)inv, "ProtectionStone");
        GuiFile.vexVaultImage((VexGui)inv, "Vault");
        GuiFile.vexVaultImage((VexGui)inv, "Points");
        GuiFile.vexButton((VexGui)inv, "ButtonVault");
        GuiFile.vexButton((VexGui)inv, "ButtonPoints");
        GuiFile.vexButton((VexGui)inv, "ButtonESC");
        GuiFile.vexButton((VexGui)inv, "ButtonUpdata");
        return inv;
    }

    public static void StrengMessage(VexGui vexGui) {
        VexImage vexImage = new VexImage(data.getString("OutPutText.image.url"), data.getInt("OutPutText.image.x"), data.getInt("OutPutText.image.y"), data.getInt("OutPutText.image.width"), data.getInt("OutPutText.image.high"), data.getInt("OutPutText.image.xshow"), data.getInt("OutPutText.image.yshow"));
        LRVexText lrVexText = LRVexGuiComponent.getLRVexText("OutPutText.text", data.getStringList("OutPutText.text.text"));
        vexGui.addComponent((VexComponents)vexImage);
        vexGui.addComponent((VexComponents)lrVexText);
    }

    public static void vexInfoImage(VexGui gui) {
        VexImage vexImage = new VexImage(data.getString("InfoBar.url"), data.getInt("InfoBar.x"), data.getInt("InfoBar.y"), data.getInt("InfoBar.width"), data.getInt("InfoBar.high"), data.getInt("InfoBar.xshow"), data.getInt("InfoBar.yshow"));
        gui.addComponent((VexComponents)vexImage);
    }

    public static void vexSlot(VexGui vexGui, String Path2) {
        VexSlot vexSlot = new VexSlot(data.getInt(Path2 + ".slot.id"), data.getInt(Path2 + ".slot.x"), data.getInt(Path2 + ".slot.y"), new ItemStack(Material.AIR));
        VexHoverText vexHoverText = new VexHoverText(data.getStringList(Path2 + ".image.text"), data.getInt(Path2 + ".image.x"), data.getInt(Path2 + ".image.y"), data.getInt(Path2 + ".image.width"), data.getInt(Path2 + ".image.high"));
        VexImage vexImage = new VexImage(data.getString(Path2 + ".image.url"), data.getInt(Path2 + ".image.x"), data.getInt(Path2 + ".image.y"), data.getInt(Path2 + ".image.width"), data.getInt(Path2 + ".image.high"), data.getInt(Path2 + ".image.xshow"), data.getInt(Path2 + ".image.yshow"), vexHoverText);
        if (Path2.equalsIgnoreCase("Item")) {
            VexGifImage vexGifImage = new VexGifImage(data.getString(Path2 + ".backimage.url"), data.getInt(Path2 + ".backimage.x"), data.getInt(Path2 + ".backimage.y"), data.getInt(Path2 + ".backimage.width"), data.getInt(Path2 + ".backimage.high"), data.getInt(Path2 + ".backimage.xshow"), data.getInt(Path2 + ".backimage.yshow"), vexHoverText);
            vexGui.addComponent((VexComponents)vexGifImage);
        }
        vexGui.addComponent((VexComponents)vexSlot);
        vexGui.addComponent((VexComponents)vexImage);
    }

    public static VexSlot getVexSlot(String Path2) {
        VexSlot vexSlot = new VexSlot(data.getInt(Path2 + ".slot.id"), data.getInt(Path2 + ".slot.x"), data.getInt(Path2 + ".slot.y"), new ItemStack(Material.AIR));
        return vexSlot;
    }

    public static void vexVaultImage(VexGui vexGui, String Path2) {
        VexImage vexImage = new VexImage(data.getString(Path2 + ".url"), data.getInt(Path2 + ".x"), data.getInt(Path2 + ".y"), data.getInt(Path2 + ".width"), data.getInt(Path2 + ".high"), data.getInt(Path2 + ".xshow"), data.getInt(Path2 + ".yshow"));
        VexText vexText = new VexText(data.getInt(Path2 + ".x"), data.getInt(Path2 + ".y") + 1, data.getStringList(Path2 + ".text"));
        vexGui.addComponent((VexComponents)vexImage);
        vexGui.addComponent((VexComponents)vexText);
    }

    public static VexButton getVexButton(String Path2) {
        VexButton button = new VexButton(data.getInt(Path2 + ".id"), data.getString(Path2 + ".name"), data.getString(Path2 + ".url1"), data.getString(Path2 + ".url2"), data.getInt(Path2 + ".x"), data.getInt(Path2 + ".y"), data.getInt(Path2 + ".width"), data.getInt(Path2 + ".high"));
        return button;
    }

    public static void vexButton(VexGui gui, String Path2) {
        if (Path2.equalsIgnoreCase("ButtonESC")) {
            VexButton vexButton = new VexButton(data.getInt(Path2 + ".id"), data.getString(Path2 + ".name"), data.getString(Path2 + ".url1"), data.getString(Path2 + ".url2"), data.getInt(Path2 + ".x"), data.getInt(Path2 + ".y"), data.getInt(Path2 + ".width"), data.getInt(Path2 + ".high"), new VexHoverText(data.getStringList(Path2 + ".text"), data.getInt(Path2 + ".x"), data.getInt(Path2 + ".y"), data.getInt(Path2 + ".width"), data.getInt(Path2 + ".high")));
        }
        VexButton button = new VexButton(data.getInt(Path2 + ".id"), data.getString(Path2 + ".name"), data.getString(Path2 + ".url1"), data.getString(Path2 + ".url2"), data.getInt(Path2 + ".x"), data.getInt(Path2 + ".y"), data.getInt(Path2 + ".width"), data.getInt(Path2 + ".high"), new VexHoverText(data.getStringList(Path2 + ".text"), data.getInt(Path2 + ".x"), data.getInt(Path2 + ".y"), data.getInt(Path2 + ".width"), data.getInt(Path2 + ".high")));
        gui.addComponent((VexComponents)button);
    }

    public static void saveMessage() {
        try {
            data.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDefauleMessage() {
        if (!f.exists()) {
            Main.getMain().saveResource(f.getName(), false);
        }
        data = YamlConfiguration.loadConfiguration((File)f);
    }

    public static void reloadMessage() {
        data = YamlConfiguration.loadConfiguration((File)f);
    }

    public static YamlConfiguration getData() {
        return data;
    }
}

