package noppes.npcs.scripted.roles;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import noppes.npcs.scripted.*;

public class ScriptJobHealer extends ScriptJobInterface
{
    private JobHealer job;
    
    public ScriptJobHealer(final EntityNPCInterface npc) {
        super(npc);
        this.job = (JobHealer)npc.jobInterface;
    }
    
    public void heal(final ScriptLivingBase entity, final float amount) {
        this.job.heal(entity.getMinecraftEntity(), amount);
    }
    
    @Override
    public int getType() {
        return 2;
    }
}
