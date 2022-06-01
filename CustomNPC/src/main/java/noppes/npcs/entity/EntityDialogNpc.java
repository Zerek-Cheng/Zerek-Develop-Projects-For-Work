package noppes.npcs.entity;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class EntityDialogNpc extends EntityNPCInterface
{
    public EntityDialogNpc(final World world) {
        super(world);
    }
    
    @Override
    public boolean isInvisibleToPlayer(final EntityPlayer player) {
        return true;
    }
    
    @Override
    public boolean isInvisible() {
        return true;
    }
    
    @Override
    public void onUpdate() {
    }
    
    @Override
    public boolean interact(final EntityPlayer player) {
        return false;
    }
}
