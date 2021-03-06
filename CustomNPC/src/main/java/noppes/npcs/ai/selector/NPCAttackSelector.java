package noppes.npcs.ai.selector;

import net.minecraft.command.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import noppes.npcs.roles.*;
import noppes.npcs.constants.*;
import noppes.npcs.roles.companion.*;
import net.minecraft.entity.player.*;
import noppes.npcs.controllers.*;

public class NPCAttackSelector implements IEntitySelector
{
    private EntityNPCInterface npc;
    
    public NPCAttackSelector(final EntityNPCInterface npc) {
        this.npc = npc;
    }
    
    public boolean isEntityApplicable(final Entity entity) {
        if (!entity.isEntityAlive() || entity == this.npc || this.npc.getDistanceToEntity(entity) > this.npc.stats.aggroRange || !(entity instanceof EntityLivingBase) || ((EntityLivingBase)entity).getHealth() < 1.0f) {
            return false;
        }
        if (this.npc.ai.directLOS && !this.npc.getEntitySenses().canSee(entity)) {
            return false;
        }
        if (!this.npc.stats.attackInvisible && ((EntityLivingBase)entity).isPotionActive(Potion.invisibility) && this.npc.getDistanceSqToEntity(entity) < 9.0) {
            return false;
        }
        if (!this.npc.isFollower() && this.npc.ai.returnToStart) {
            int allowedDistance = this.npc.stats.aggroRange * 2;
            if (this.npc.ai.movingType == EnumMovingType.Wandering) {
                allowedDistance += this.npc.ai.walkingRange;
            }
            double distance = entity.getDistanceSq((double)this.npc.getStartXPos(), this.npc.getStartYPos(), (double)this.npc.getStartZPos());
            if (this.npc.ai.movingType == EnumMovingType.MovingPath) {
                final int[] arr = this.npc.ai.getCurrentMovingPath();
                distance = entity.getDistanceSq((double)arr[0], (double)arr[1], (double)arr[2]);
            }
            if (distance > allowedDistance * allowedDistance) {
                return false;
            }
        }
        if (this.npc.advanced.job == EnumJobType.Guard && ((JobGuard)this.npc.jobInterface).isEntityApplicable(entity)) {
            return true;
        }
        if (this.npc.advanced.role == EnumRoleType.Companion) {
            final RoleCompanion role = (RoleCompanion)this.npc.roleInterface;
            if (role.job == EnumCompanionJobs.GUARD && ((CompanionGuard)role.jobInterface).isEntityApplicable(entity)) {
                return true;
            }
        }
        if (!(entity instanceof EntityPlayerMP)) {
            if (entity instanceof EntityNPCInterface) {
                if (((EntityNPCInterface)entity).isKilled()) {
                    return false;
                }
                if (this.npc.advanced.attackOtherFactions) {
                    return this.npc.faction.isAggressiveToNpc((EntityNPCInterface)entity);
                }
            }
            return false;
        }
        if (!this.npc.faction.isAggressiveToPlayer((EntityPlayer)entity)) {
            return false;
        }
        if (PixelmonHelper.Enabled && this.npc.advanced.job == EnumJobType.Spawner) {
            return PixelmonHelper.canBattle((EntityPlayerMP)entity, this.npc);
        }
        return !((EntityPlayerMP)entity).capabilities.disableDamage;
    }
}
