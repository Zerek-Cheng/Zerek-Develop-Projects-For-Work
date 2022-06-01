package noppes.npcs.roles;

import noppes.npcs.entity.*;
import java.util.*;
import noppes.npcs.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.boss.*;

public class JobGuard extends JobInterface
{
    public boolean attacksAnimals;
    public boolean attackHostileMobs;
    public boolean attackCreepers;
    public List<String> targets;
    public boolean specific;
    
    public JobGuard(final EntityNPCInterface npc) {
        super(npc);
        this.attacksAnimals = false;
        this.attackHostileMobs = true;
        this.attackCreepers = false;
        this.targets = new ArrayList<String>();
        this.specific = false;
    }
    
    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setBoolean("GuardAttackAnimals", this.attacksAnimals);
        nbttagcompound.setBoolean("GuardAttackMobs", this.attackHostileMobs);
        nbttagcompound.setBoolean("GuardAttackCreepers", this.attackCreepers);
        nbttagcompound.setBoolean("GuardSpecific", this.specific);
        nbttagcompound.setTag("GuardTargets", (NBTBase)NBTTags.nbtStringList(this.targets));
        return nbttagcompound;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        this.attacksAnimals = nbttagcompound.getBoolean("GuardAttackAnimals");
        this.attackHostileMobs = nbttagcompound.getBoolean("GuardAttackMobs");
        this.attackCreepers = nbttagcompound.getBoolean("GuardAttackCreepers");
        this.specific = nbttagcompound.getBoolean("GuardSpecific");
        this.targets = NBTTags.getStringList(nbttagcompound.getTagList("GuardTargets", 10));
    }
    
    public boolean isEntityApplicable(final Entity entity) {
        if (entity instanceof EntityPlayer || entity instanceof EntityNPCInterface) {
            return false;
        }
        if (this.specific && this.targets.contains("entity." + EntityList.getEntityString(entity) + ".name")) {
            return true;
        }
        if (entity instanceof EntityAnimal) {
            return this.attacksAnimals && (!(entity instanceof EntityTameable) || ((EntityTameable)entity).getOwner() == null);
        }
        if (entity instanceof EntityCreeper) {
            return this.attackCreepers;
        }
        return (entity instanceof IMob || entity instanceof EntityDragon) && this.attackHostileMobs;
    }
}
