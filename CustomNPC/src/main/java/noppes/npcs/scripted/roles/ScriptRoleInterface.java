package noppes.npcs.scripted.roles;

import noppes.npcs.entity.*;

public class ScriptRoleInterface
{
    protected EntityNPCInterface npc;
    
    public ScriptRoleInterface(final EntityNPCInterface npc) {
        this.npc = npc;
    }
    
    public int getType() {
        return 0;
    }
}
