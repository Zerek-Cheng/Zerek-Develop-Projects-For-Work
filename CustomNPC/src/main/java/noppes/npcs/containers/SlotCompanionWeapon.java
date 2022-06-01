package noppes.npcs.containers;

import noppes.npcs.roles.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

class SlotCompanionWeapon extends Slot
{
    final RoleCompanion role;
    
    public SlotCompanionWeapon(final RoleCompanion role, final IInventory iinventory, final int id, final int x, final int y) {
        super(iinventory, id, x, y);
        this.role = role;
    }
    
    public int getSlotStackLimit() {
        return 1;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        return this.role.canWearSword(itemstack);
    }
}
