/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 */
package cc.kunss.vexst.listeners;

import cc.kunss.vexst.utils.PlayerFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join
implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e) {
        if (!PlayerFile.isPlayer(e.getPlayer())) {
            PlayerFile.CreatePlayerFile(e.getPlayer());
        }
    }
}

