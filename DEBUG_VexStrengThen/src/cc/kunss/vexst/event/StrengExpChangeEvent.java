/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package cc.kunss.vexst.event;

import cc.kunss.vexst.api.VexStrengThenAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StrengExpChangeEvent
extends Event
implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player p;
    private int playerLevel;
    private String playerPrefix;
    private double PlayerExp;
    private double PlayerMaxExp;

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public StrengExpChangeEvent(Player p) {
        this.p = p;
        this.setPlayerExp(VexStrengThenAPI.getPlayerStrengExp(p));
        this.setPlayerLevel(VexStrengThenAPI.getPlayerStrengLevel(p));
        this.setPlayerPrefix(VexStrengThenAPI.getPlayerStrengPrefix(p));
        this.setPlayerMaxExp(VexStrengThenAPI.getPlayerStringMaxExp(p));
    }

    public double getPlayerExp() {
        return this.PlayerExp;
    }

    public void setPlayerExp(double playerExp) {
        this.PlayerExp = playerExp;
    }

    public Player getPlayer() {
        return this.p;
    }

    public String getPlayerPrefix() {
        return this.playerPrefix;
    }

    public void setPlayerPrefix(String playerPrefix) {
        this.playerPrefix = playerPrefix;
    }

    public int getPlayerLevel() {
        return this.playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    public double getPlayerMaxExp() {
        return this.PlayerMaxExp;
    }

    public void setPlayerMaxExp(double playerMaxExp) {
        this.PlayerMaxExp = playerMaxExp;
    }

    public boolean isCancelled() {
        return false;
    }

    public void setCancelled(boolean b) {
    }
}

