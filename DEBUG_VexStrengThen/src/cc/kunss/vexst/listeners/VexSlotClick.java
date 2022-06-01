/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  lk.vexview.api.VexViewAPI
 *  lk.vexview.event.VexSlotClickEvent
 *  lk.vexview.gui.OpenedVexGui
 *  lk.vexview.gui.VexGui
 *  lk.vexview.gui.components.DynamicComponent
 *  lk.vexview.gui.components.VexComponents
 *  lk.vexview.gui.components.VexSlot
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 */
package cc.kunss.vexst.listeners;

import cc.kunss.vexst.data.LRVexText;
import cc.kunss.vexst.utils.GuiFile;
import cc.kunss.vexst.utils.LRVexGuiComponent;
import cc.kunss.vexst.utils.VexGuis;
import lk.vexview.api.VexViewAPI;
import lk.vexview.event.VexSlotClickEvent;
import lk.vexview.gui.OpenedVexGui;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.DynamicComponent;
import lk.vexview.gui.components.VexComponents;
import lk.vexview.gui.components.VexSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VexSlotClick
implements Listener {
    @EventHandler
    public static void onslot(VexSlotClickEvent e) {
        if (e.getID() == GuiFile.getVexSlot("Item").getID()) {
            OpenedVexGui vexGuis = VexViewAPI.getPlayerCurrentGui((Player)e.getPlayer());
            VexSlot vexSlot = vexGuis.getVexGui().getSlotById(e.getID());
            LRVexText vexText = VexSlotClick.getVexText(vexGuis.getVexGui(), "InfoBar.text");
            vexGuis.removeDynamicComponent((DynamicComponent)vexText);
            LRVexText vt = VexGuis.sendInfoMessage(vexSlot.getItem(), vexText, e.getPlayer());
            vexGuis.addDynamicComponent((DynamicComponent)vt);
        }
    }

    public static LRVexText getVexText(VexGui gui, String Path2) {
        for (VexComponents vexComponents : gui.getComponents()) {
            LRVexText lrVexText;
            if (!(vexComponents instanceof LRVexText) || (lrVexText = (LRVexText)vexComponents).getId() != LRVexGuiComponent.getLRVexText(Path2, null).getId()) continue;
            return lrVexText;
        }
        return null;
    }
}

