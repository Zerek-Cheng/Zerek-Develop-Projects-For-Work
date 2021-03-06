package noppes.npcs.ai.selector;

import net.minecraft.command.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;

public class NPCInteractSelector implements IEntitySelector
{
    private EntityNPCInterface npc;
    
    public NPCInteractSelector(final EntityNPCInterface npc) {
        this.npc = npc;
    }
    
    public boolean isEntityApplicable(final Entity entity) {
        if (entity == this.npc || !(entity instanceof EntityNPCInterface) || !this.npc.isEntityAlive()) {
            return false;
        }
        final EntityNPCInterface selected = (EntityNPCInterface)entity;
        return !selected.isAttacking() && !this.npc.getFaction().isAggressiveToNpc(selected) && this.npc.ai.stopAndInteract;
    }
}
