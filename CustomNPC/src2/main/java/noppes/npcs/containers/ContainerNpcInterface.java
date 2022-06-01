package noppes.npcs.containers;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class ContainerNpcInterface extends Container
{
    private int posX;
    private int posZ;
    
    public ContainerNpcInterface(final EntityPlayer player) {
        this.posX = MathHelper.floor_double(player.posX);
        this.posZ = MathHelper.floor_double(player.posZ);
        player.motionX = 0.0;
        player.motionZ = 0.0;
    }
    
    public boolean canInteractWith(final EntityPlayer player) {
        return !player.isDead && this.posX == MathHelper.floor_double(player.posX) && this.posZ == MathHelper.floor_double(player.posZ);
    }
}
