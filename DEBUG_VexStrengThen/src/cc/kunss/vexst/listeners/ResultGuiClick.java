/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  lk.vexview.api.VexViewAPI
 *  lk.vexview.event.ButtonClickEvent
 *  lk.vexview.gui.VexGui
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package cc.kunss.vexst.listeners;

import cc.kunss.vexst.utils.GuiFile;
import lk.vexview.api.VexViewAPI;
import lk.vexview.event.ButtonClickEvent;
import lk.vexview.gui.VexGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ResultGuiClick
implements Listener {
    @EventHandler
    public void onClick(ButtonClickEvent e) {
        if (e.getButtonID() == GuiFile.getVexButton("ResultGui.button").getId()) {
            VexGui vexGui = VexViewAPI.getPlayerCurrentGui((Player)e.getPlayer()).getVexGui();
            VexViewAPI.openGui((Player)e.getPlayer(), (VexGui)GuiFile.getGUI());
        }
        if (e.getButtonID() == GuiFile.getVexButton("ButtonESC").getId()) {
            e.getPlayer().closeInventory();
        }
        if (e.getButtonID() == GuiFile.getVexButton("ButtonUpdata").getId()) {
            VexViewAPI.openGui((Player)e.getPlayer(), (VexGui)GuiFile.getGUI());
        }
    }
}

