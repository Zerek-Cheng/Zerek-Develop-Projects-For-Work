package noppes.npcs.scripted.roles;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;

public class ScriptJobConversation extends ScriptJobInterface
{
    private JobConversation job;
    
    public ScriptJobConversation(final EntityNPCInterface npc) {
        super(npc);
        this.job = (JobConversation)npc.jobInterface;
    }
    
    @Override
    public int getType() {
        return 6;
    }
}
