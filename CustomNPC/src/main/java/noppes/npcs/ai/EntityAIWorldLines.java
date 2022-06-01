package noppes.npcs.ai;

import net.minecraft.entity.ai.*;
import noppes.npcs.entity.*;
import noppes.npcs.constants.*;

public class EntityAIWorldLines extends EntityAIBase
{
    private EntityNPCInterface npc;
    
    public EntityAIWorldLines(final EntityNPCInterface npc) {
        this.npc = npc;
        this.setMutexBits((int)AiMutex.PASSIVE);
    }
    
    public boolean shouldExecute() {
        return !this.npc.isAttacking() && !this.npc.isKilled() && this.npc.advanced.hasWorldLines() && this.npc.getRNG().nextInt(1900) == 1;
    }
    
    public void startExecuting() {
        this.npc.saySurrounding(this.npc.advanced.getWorldLine());
    }
}
