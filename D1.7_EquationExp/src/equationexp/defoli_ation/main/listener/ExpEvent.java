/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerExpChangeEvent
 */
package equationexp.defoli_ation.main.listener;

import equationexp.defoli_ation.main.exp.Exp;
import equationexp.defoli_ation.main.exp.ExpManager;
import equationexp.defoli_ation.main.file.expfile.PlayerExpFile;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class ExpEvent
implements Listener {
    private ExpManager expManager;
    private PlayerExpFile expFile;
    private Exp exp;

    public ExpEvent(ExpManager exp, PlayerExpFile expFile) {
        this.expFile = expFile;
        this.expManager = exp;
        this.exp = this.expManager.getExp();
    }

    @EventHandler
    public void ExpChangeEvent(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        this.addAndloadPlayerExp(player, event.getAmount());
        event.setAmount(0);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        int newLevel;
        Player p = (Player)event.getWhoClicked();
        int level = this.exp.getLevel(this.expFile.getPlayerExp(p));
        if (level == (newLevel = p.getLevel())) {
            return;
        }
        this.updatePlayerExp(p, level, p.getLevel());
    }

    private void addAndloadPlayerExp(Player p, int exp) {
        this.expManager.addPlayerExp(p, exp);
        this.expManager.loadPlayerExpLevelAndExpBar(p);
    }

    private void updatePlayerExp(Player p, int oldLevel, int newLevel) {
        int oldExp = this.exp.getExp(oldLevel);
        int newExp = this.exp.getExp(newLevel);
        int addExp = oldExp - newExp;
        this.addAndloadPlayerExp(p, - addExp);
    }
}

