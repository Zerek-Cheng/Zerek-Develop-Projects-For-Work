package noppes.npcs.containers;

import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

class SlotNPCArmor extends Slot
{
    final int armorType;
    
    SlotNPCArmor(final IInventory iinventory, final int i, final int j, final int k, final int l) {
        super(iinventory, i, j, k);
        this.armorType = l;
    }
    
    public int getSlotStackLimit() {
        return 1;
    }
    
    public IIcon getBackgroundIconIndex() {
        return ItemArmor.getBackgroundIcon(this.armorType);
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        if (itemstack.getItem() instanceof ItemArmor) {
            return ((ItemArmor)itemstack.getItem()).armorType == this.armorType;
        }
        return itemstack.getItem() instanceof ItemBlock && this.armorType == 0;
    }
}
