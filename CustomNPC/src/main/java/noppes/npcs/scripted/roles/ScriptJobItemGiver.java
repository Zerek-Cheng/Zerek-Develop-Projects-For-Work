package noppes.npcs.scripted.roles;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;

public class ScriptJobItemGiver extends ScriptJobInterface
{
    private JobItemGiver job;
    
    public ScriptJobItemGiver(final EntityNPCInterface npc) {
        super(npc);
        this.job = (JobItemGiver)npc.jobInterface;
    }
    
    @Override
    public int getType() {
        return 5;
    }
}
