package noppes.npcs.containers;

import noppes.npcs.roles.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import java.util.*;

class SlotNpcMercenaryCurrency extends Slot
{
    RoleFollower role;
    
    public SlotNpcMercenaryCurrency(final RoleFollower role, final IInventory inv, final int i, final int j, final int k) {
        super(inv, i, j, k);
        this.role = role;
    }
    
    public int getSlotStackLimit() {
        return 64;
    }
    
    public boolean isItemValid(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        for (final ItemStack is : this.role.inventory.items.values()) {
            if (item == is.getItem()) {
                if (itemstack.getHasSubtypes() && itemstack.getMetadata() != is.getMetadata()) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
}
