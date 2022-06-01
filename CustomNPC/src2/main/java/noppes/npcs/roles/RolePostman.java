package noppes.npcs.roles;

import net.minecraft.entity.player.*;
import noppes.npcs.entity.*;
import noppes.npcs.controllers.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import noppes.npcs.*;
import noppes.npcs.constants.*;

public class RolePostman extends RoleInterface
{
    public NpcMiscInventory inventory;
    private List<EntityPlayer> recentlyChecked;
    private List<EntityPlayer> toCheck;
    
    public RolePostman(final EntityNPCInterface npc) {
        super(npc);
        this.inventory = new NpcMiscInventory(1);
        this.recentlyChecked = new ArrayList<EntityPlayer>();
    }
    
    @Override
    public boolean aiShouldExecute() {
        if (this.npc.ticksExisted % 20 != 0) {
            return false;
        }
        (this.toCheck = (List<EntityPlayer>)this.npc.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, this.npc.boundingBox.expand(10.0, 10.0, 10.0))).removeAll(this.recentlyChecked);
        final List<EntityPlayer> listMax = (List<EntityPlayer>)this.npc.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, this.npc.boundingBox.expand(20.0, 20.0, 20.0));
        this.recentlyChecked.retainAll(listMax);
        this.recentlyChecked.addAll(this.toCheck);
        for (final EntityPlayer player : this.toCheck) {
            if (PlayerDataController.instance.hasMail(player)) {
                player.addChatMessage((IChatComponent)new ChatComponentTranslation("You've got mail", new Object[0]));
            }
        }
        return false;
    }
    
    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setTag("PostInv", (NBTBase)this.inventory.getToNBT());
        return nbttagcompound;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        this.inventory.setFromNBT(nbttagcompound.getCompoundTag("PostInv"));
    }
    
    @Override
    public void interact(final EntityPlayer player) {
        player.openGui((Object)CustomNpcs.instance, EnumGuiType.PlayerMailman.ordinal(), player.worldObj, 1, 1, 0);
    }
}
