package noppes.npcs.entity;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import cpw.mods.fml.relauncher.*;

public class EntityChairMount extends Entity
{
    public EntityChairMount(final World world) {
        super(world);
        this.setSize(0.0f, 0.0f);
    }
    
    public double getMountedYOffset() {
        return 0.5;
    }
    
    protected void entityInit() {
    }
    
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (this.worldObj != null && !this.worldObj.isRemote && this.riddenByEntity == null) {
            this.isDead = true;
        }
    }
    
    public boolean isEntityInvulnerable() {
        return true;
    }
    
    public boolean isInvisible() {
        return true;
    }
    
    public void moveEntity(final double x, final double y, final double z) {
    }
    
    protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
    }
    
    protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    protected void fall(final float distance) {
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(final double x, final double y, final double z, final float yaw, final float pitch, final int rotationIncrements) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }
}
