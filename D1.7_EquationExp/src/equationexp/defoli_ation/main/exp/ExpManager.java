/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.PluginManager
 */
package equationexp.defoli_ation.main.exp;

import equationexp.defoli_ation.main.event.EquationExpAddEvent;
import equationexp.defoli_ation.main.exp.Exp;
import equationexp.defoli_ation.main.file.expfile.PlayerExpFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;

public class ExpManager {
    private final Exp exp;
    private final PlayerExpFile expFile;

    public ExpManager(PlayerExpFile expFile, Exp exp) {
        this.expFile = expFile;
        this.exp = exp;
    }

    public void setPlayerExp(Player p, int exp) {
        int nowExp = this.expFile.getPlayerExp(p);
        int amount = exp - nowExp;
        EquationExpAddEvent event = new EquationExpAddEvent(p, amount);
        Bukkit.getServer().getPluginManager().callEvent((Event)event);
        this.expFile.savePlayerExp(p, nowExp + event.getAmount());
    }

    public void loadPlayerExpLevel(Player p) {
        int playerExp = this.expFile.getPlayerExp(p);
        p.setLevel(this.exp.getLevel(playerExp));
    }

    public void loadPlayerExpBar(Player p) {
        int playerExp = this.expFile.getPlayerExp(p);
        int playerLevel = this.exp.getLevel(playerExp);
        int levelExp = this.exp.getExp(playerLevel);
        int nextLevelExp = this.exp.getExp(playerLevel + 1);
        int upLevelNeedExp = nextLevelExp - levelExp;
        int exp = playerExp - levelExp;
        p.setExp((float)exp / (float)upLevelNeedExp);
    }

    public void loadPlayerExpLevelAndExpBar(Player p) {
        this.loadPlayerExpLevel(p);
        this.loadPlayerExpBar(p);
    }

    public void addPlayerExp(Player p, int exp) {
        EquationExpAddEvent event = new EquationExpAddEvent(p, exp);
        Bukkit.getServer().getPluginManager().callEvent((Event)event);
        this.expFile.savePlayerExp(p, event.getAmount() + this.expFile.getPlayerExp(p));
    }

    public Exp getExp() {
        return this.exp;
    }
}

