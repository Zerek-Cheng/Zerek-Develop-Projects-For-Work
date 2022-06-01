package noppes.npcs.containers;

import noppes.npcs.roles.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

class SlotCompanionArmor extends Slot
{
    final int armorType;
    final RoleCompanion role;
    
    public SlotCompanionArmor(final RoleCompanion role, final IInventory iinventory, final int id, final int x, final int y, final int type) {
        super(iinventory, id, x, y);
        this.armorType = type;
        this.role = role;
    }
    
    public int getSlotStackLimit() {
        return 1;
    }
    
    public IIcon getBackgroundIconIndex() {
        return ItemArmor.getBackgroundIcon(this.armorType);
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        if (itemstack.getItem() instanceof ItemArmor && this.role.canWearArmor(itemstack)) {
            return ((ItemArmor)itemstack.getItem()).armorType == this.armorType;
        }
        return itemstack.getItem() instanceof ItemBlock && this.armorType == 0;
    }
}
