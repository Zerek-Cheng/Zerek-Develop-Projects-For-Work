package noppes.npcs.scripted.roles;

import noppes.npcs.entity.*;

public class ScriptRoleMailman extends ScriptRoleInterface
{
    public ScriptRoleMailman(final EntityNPCInterface npc) {
        super(npc);
    }
    
    @Override
    public int getType() {
        return 1;
    }
}
