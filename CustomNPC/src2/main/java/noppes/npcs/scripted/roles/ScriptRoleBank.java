package noppes.npcs.scripted.roles;

import noppes.npcs.entity.*;

public class ScriptRoleBank extends ScriptRoleInterface
{
    public ScriptRoleBank(final EntityNPCInterface npc) {
        super(npc);
    }
    
    @Override
    public int getType() {
        return 4;
    }
}
