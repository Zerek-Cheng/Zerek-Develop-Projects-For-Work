package noppes.npcs.items;

import cpw.mods.fml.common.registry.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import noppes.npcs.controllers.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import net.minecraft.entity.*;
import noppes.npcs.roles.*;
import net.minecraft.entity.passive.*;
import noppes.npcs.*;

public class ItemSoulstoneEmpty extends Item
{
    public ItemSoulstoneEmpty() {
        this.setMaxStackSize(64);
    }
    
    public Item setUnlocalizedName(final String name) {
        super.setUnlocalizedName(name);
        GameRegistry.registerItem((Item)this, name);
        return this;
    }
    
    public boolean store(final EntityLivingBase entity, final ItemStack stack, final EntityPlayer player) {
        if (!this.hasPermission(entity, player) || entity instanceof EntityPlayer) {
            return false;
        }
        final ItemStack stone = new ItemStack(CustomItems.soulstoneFull);
        final NBTTagCompound compound = new NBTTagCompound();
        if (!entity.writeToNBTOptional(compound)) {
            return false;
        }
        ServerCloneController.Instance.cleanTags(compound);
        if (stone.stackTagCompound == null) {
            stone.stackTagCompound = new NBTTagCompound();
        }
        stone.stackTagCompound.setTag("Entity", (NBTBase)compound);
        String name = EntityList.getEntityString((Entity)entity);
        if (name == null) {
            name = "generic";
        }
        stone.stackTagCompound.setString("Name", "entity." + name + ".name");
        if (entity instanceof EntityNPCInterface) {
            final EntityNPCInterface npc = (EntityNPCInterface)entity;
            stone.stackTagCompound.setString("DisplayName", entity.getCommandSenderName());
            if (npc.advanced.role == EnumRoleType.Companion) {
                final RoleCompanion role = (RoleCompanion)npc.roleInterface;
                stone.stackTagCompound.setString("ExtraText", "companion.stage,: ," + role.stage.name);
            }
        }
        else if (entity instanceof EntityLiving && ((EntityLiving)entity).hasCustomNameTag()) {
            stone.stackTagCompound.setString("DisplayName", ((EntityLiving)entity).getCustomNameTag());
        }
        NoppesUtilServer.GivePlayerItem((Entity)player, player, stone);
        if (!player.capabilities.isCreativeMode) {
            stack.splitStack(1);
            if (stack.stackSize <= 0) {
                player.destroyCurrentEquippedItem();
            }
        }
        return entity.isDead = true;
    }
    
    public boolean hasPermission(final EntityLivingBase entity, final EntityPlayer player) {
        if (NoppesUtilServer.isOp(player) && player.capabilities.isCreativeMode) {
            return true;
        }
        if (CustomNpcsPermissions.enabled() && CustomNpcsPermissions.hasPermission(player, CustomNpcsPermissions.SOULSTONE_ALL)) {
            return true;
        }
        if (entity instanceof EntityNPCInterface) {
            final EntityNPCInterface npc = (EntityNPCInterface)entity;
            if (npc.advanced.role == EnumRoleType.Companion) {
                final RoleCompanion role = (RoleCompanion)npc.roleInterface;
                if (role.getOwner() == player) {
                    return true;
                }
            }
            if (npc.advanced.role == EnumRoleType.Follower) {
                final RoleFollower role2 = (RoleFollower)npc.roleInterface;
                if (role2.getOwner() == player) {
                    return !role2.refuseSoulStone;
                }
            }
            return false;
        }
        return entity instanceof EntityAnimal && CustomNpcs.SoulStoneAnimals;
    }
}
