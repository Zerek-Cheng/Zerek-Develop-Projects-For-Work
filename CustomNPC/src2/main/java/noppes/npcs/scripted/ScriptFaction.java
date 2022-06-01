package noppes.npcs.scripted;

import noppes.npcs.controllers.*;
import net.minecraft.entity.player.*;
import noppes.npcs.entity.*;

public class ScriptFaction
{
    private Faction faction;
    
    public ScriptFaction(final Faction faction) {
        this.faction = faction;
    }
    
    public int getId() {
        return this.faction.id;
    }
    
    public String getName() {
        return this.faction.name;
    }
    
    public int getDefaultPoints() {
        return this.faction.defaultPoints;
    }
    
    public int getColor() {
        return this.faction.color;
    }
    
    public boolean isFriendlyToPlayer(final ScriptPlayer player) {
        return this.faction.isFriendlyToPlayer((EntityPlayer)player.player);
    }
    
    public boolean isNeutralToPlayer(final ScriptPlayer player) {
        return this.faction.isNeutralToPlayer((EntityPlayer)player.player);
    }
    
    public boolean isAggressiveToPlayer(final ScriptPlayer player) {
        return this.faction.isAggressiveToPlayer((EntityPlayer)player.player);
    }
    
    public boolean isAggressiveToNpc(final ScriptNpc npc) {
        return this.faction.isAggressiveToNpc(npc.npc);
    }
}
