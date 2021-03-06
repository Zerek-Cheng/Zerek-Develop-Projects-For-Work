package noppes.npcs.items;

import cpw.mods.fml.common.registry.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import noppes.npcs.roles.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import noppes.npcs.controllers.*;

public class ItemSoulstoneFilled extends Item
{
    public ItemSoulstoneFilled() {
        this.setMaxStackSize(1);
    }
    
    public Item setUnlocalizedName(final String name) {
        super.setUnlocalizedName(name);
        GameRegistry.registerItem((Item)this, name);
        return this;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean bo) {
        if (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey("Entity", 10)) {
            list.add(EnumChatFormatting.RED + "Error");
            return;
        }
        String name = StatCollector.translateToLocal(stack.stackTagCompound.getString("Name"));
        if (stack.stackTagCompound.hasKey("DisplayName")) {
            name = stack.stackTagCompound.getString("DisplayName") + " (" + name + ")";
        }
        list.add(EnumChatFormatting.BLUE + name);
        if (stack.stackTagCompound.hasKey("ExtraText")) {
            String text = "";
            final String[] split2;
            final String[] split = split2 = stack.stackTagCompound.getString("ExtraText").split(",");
            for (final String s : split2) {
                text += StatCollector.translateToLocal(s);
            }
            list.add(text);
        }
    }
    
    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (!this.spawn(player, stack, world, x, y, z)) {
            return false;
        }
        if (!player.capabilities.isCreativeMode) {
            stack.splitStack(1);
        }
        return true;
    }
    
    public boolean spawn(final EntityPlayer player, final ItemStack stack, final World world, final int x, final int y, final int z) {
        if (world.isRemote) {
            return true;
        }
        if (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey("Entity", 10)) {
            return false;
        }
        final NBTTagCompound compound = stack.stackTagCompound.getCompoundTag("Entity");
        final Entity entity = EntityList.createEntityFromNBT(compound, world);
        if (entity == null) {
            return false;
        }
        entity.setPosition(x + 0.5, (double)(y + 1 + 0.2f), z + 0.5);
        if (entity instanceof EntityNPCInterface) {
            final EntityNPCInterface npc = (EntityNPCInterface)entity;
            npc.ai.startPos = new int[] { x, y, z };
            npc.setHealth(npc.getMaxHealth());
            npc.setPosition((double)(x + 0.5f), npc.getStartYPos(), (double)(z + 0.5f));
            if (npc.advanced.role == EnumRoleType.Companion && player != null) {
                final PlayerData data = PlayerDataController.instance.getPlayerData(player);
                if (data.hasCompanion()) {
                    return false;
                }
                ((RoleCompanion)npc.roleInterface).setOwner(player);
                data.setCompanion(npc);
            }
            if (npc.advanced.role == EnumRoleType.Follower && player != null) {
                ((RoleFollower)npc.roleInterface).setOwner(player);
            }
        }
        world.spawnEntityInWorld(entity);
        return true;
    }
}
