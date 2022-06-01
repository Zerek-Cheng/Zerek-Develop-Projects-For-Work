/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  lk.vexview.event.TextFieldGetEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package cc.kunss.listeners;

import cc.kunss.CDKMain;
import cc.kunss.utils.CDKMap;
import cc.kunss.utils.Methods;
import java.util.List;
import java.util.Set;
import lk.vexview.event.TextFieldGetEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClickButtonEvents
implements Listener {
    @EventHandler
    public void ClickButton(TextFieldGetEvent e) {
        String CDK = e.getText();
        int i = CDKMain.getCDK().keySet().size();
        for (String cdklist : CDKMain.getCDK().keySet()) {
            CDKMap CDKMap2 = CDKMain.getCDK().get(cdklist);
            if (CDKMap2.getCdk().contains(CDK)) {
                Methods.RemoveCDK(cdklist, CDK);
                Methods.OPCommand(e.getPlayer(), CDKMap2.getCmd().replace("<Player>", e.getPlayer().getName()));
                Bukkit.broadcastMessage((String)CDKMap2.getMessage().replace("<Player>", e.getPlayer().getName()).replace("&", "\u00a7"));
                return;
            }
            if (--i != 0) continue;
            Methods.SendMessage((CommandSender)e.getPlayer(), "ERROR.TextCDKExidtsNO");
            return;
        }
    }
}

