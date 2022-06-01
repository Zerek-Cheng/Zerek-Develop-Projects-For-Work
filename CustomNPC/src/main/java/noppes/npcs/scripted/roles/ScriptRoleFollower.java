package noppes.npcs.scripted.roles;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import noppes.npcs.scripted.*;
import net.minecraft.entity.player.*;
import noppes.npcs.controllers.*;
import net.minecraft.entity.*;

public class ScriptRoleFollower extends ScriptRoleInterface
{
    private RoleFollower role;
    
    public ScriptRoleFollower(final EntityNPCInterface npc) {
        super(npc);
        this.role = (RoleFollower)npc.roleInterface;
    }
    
    public void setOwner(final ScriptPlayer player) {
        if (player == null || player.getMinecraftEntity() == null) {
            this.role.setOwner(null);
            return;
        }
        final EntityPlayer mcplayer = (EntityPlayer)player.getMinecraftEntity();
        this.role.setOwner(mcplayer);
    }
    
    public ScriptPlayer getOwner() {
        if (this.role.owner == null) {
            return null;
        }
        return (ScriptPlayer)ScriptController.Instance.getScriptForEntity((Entity)this.role.owner);
    }
    
    public boolean hasOwner() {
        return this.role.owner != null;
    }
    
    public int getDaysLeft() {
        return this.role.getDaysLeft();
    }
    
    public void addDaysLeft(final int days) {
        this.role.addDays(days);
    }
    
    public boolean getInfiniteDays() {
        return this.role.infiniteDays;
    }
    
    public void setInfiniteDays(final boolean infinite) {
        this.role.infiniteDays = infinite;
    }
    
    public boolean getGuiDisabled() {
        return this.role.disableGui;
    }
    
    public void setGuiDisabled(final boolean disabled) {
        this.role.disableGui = disabled;
    }
    
    @Override
    public int getType() {
        return 3;
    }
}
