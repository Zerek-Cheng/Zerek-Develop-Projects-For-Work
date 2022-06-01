package noppes.npcs.scripted.roles;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;

public class ScriptJobGuard extends ScriptJobInterface
{
    private JobGuard job;
    
    public ScriptJobGuard(final EntityNPCInterface npc) {
        super(npc);
        this.job = (JobGuard)npc.jobInterface;
    }
    
    @Override
    public int getType() {
        return 3;
    }
}
