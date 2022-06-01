/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 */
package equationexp.defoli_ation.main.listener;

import equationexp.defoli_ation.main.exp.Exp;
import equationexp.defoli_ation.main.exp.ExpManager;
import equationexp.defoli_ation.main.file.expfile.PlayerExpFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin
implements Listener {
    private PlayerExpFile expFile;
    private ExpManager expManager;

    public OnPlayerJoin(PlayerExpFile expFile, ExpManager expManager) {
        this.expFile = expFile;
        this.expManager = expManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (this.expFile.exist(p)) {
            this.expManager.loadPlayerExpLevelAndExpBar(p);
        } else {
            this.expManager.setPlayerExp(p, this.expManager.getExp().getExp(p.getLevel()));
        }
    }
}

