package noppes.npcs.containers;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class ContainerNpcItemGiver extends Container
{
    private JobItemGiver role;
    
    public ContainerNpcItemGiver(final EntityNPCInterface npc, final EntityPlayer player) {
        this.role = (JobItemGiver)npc.jobInterface;
        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlotToContainer(new Slot((IInventory)this.role.inventory, j1, 6 + j1 * 18, 90));
        }
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.addSlotToContainer(new Slot((IInventory)player.inventory, l1 + i1 * 9 + 9, 6 + l1 * 18, 116 + i1 * 18));
            }
        }
        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlotToContainer(new Slot((IInventory)player.inventory, j1, 6 + j1 * 18, 174));
        }
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int i) {
        return null;
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return true;
    }
}
