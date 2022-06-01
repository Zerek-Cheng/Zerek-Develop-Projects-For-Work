package noppes.npcs.containers;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import noppes.npcs.constants.*;
import net.minecraft.item.*;

public class ContainerNPCCompanion extends ContainerNpcInterface
{
    public InventoryNPC currencyMatrix;
    public RoleCompanion role;
    
    public ContainerNPCCompanion(final EntityNPCInterface npc, final EntityPlayer player) {
        super(player);
        this.role = (RoleCompanion)npc.roleInterface;
        for (int k = 0; k < 3; ++k) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlotToContainer(new Slot((IInventory)player.inventory, j1 + k * 9 + 9, 6 + j1 * 18, 87 + k * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot((IInventory)player.inventory, l, 6 + l * 18, 145));
        }
        if (this.role.talents.containsKey(EnumCompanionTalent.INVENTORY)) {
            for (int size = (this.role.getTalentLevel(EnumCompanionTalent.INVENTORY) + 1) * 2, i = 0; i < size; ++i) {
                this.addSlotToContainer(new Slot((IInventory)this.role.inventory, i, 114 + i % 3 * 18, 8 + i / 3 * 18));
            }
        }
        if (this.role.getTalentLevel(EnumCompanionTalent.ARMOR) > 0) {
            for (int l = 0; l < 4; ++l) {
                this.addSlotToContainer((Slot)new SlotCompanionArmor(this.role, (IInventory)npc.inventory, l, 6, 8 + l * 18, l));
            }
        }
        if (this.role.getTalentLevel(EnumCompanionTalent.SWORD) > 0) {
            this.addSlotToContainer((Slot)new SlotCompanionWeapon(this.role, (IInventory)npc.inventory, 4, 79, 17));
        }
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int i) {
        return null;
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        this.role.setStats();
    }
}
