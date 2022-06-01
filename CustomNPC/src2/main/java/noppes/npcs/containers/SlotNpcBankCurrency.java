package noppes.npcs.containers;

import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class SlotNpcBankCurrency extends Slot
{
    public ItemStack item;
    
    public SlotNpcBankCurrency(final ContainerNPCBankInterface containerplayer, final IInventory iinventory, final int i, final int j, final int k) {
        super(iinventory, i, j, k);
    }
    
    public int getSlotStackLimit() {
        return 64;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return this.item != null && (this.item.getItem() == itemstack.getItem() && (!this.item.getHasSubtypes() || this.item.getMetadata() == itemstack.getMetadata()));
    }
}
