package noppes.npcs.containers;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.item.*;

public class ContainerMerchantAdd extends ContainerNpcInterface
{
    private IMerchant theMerchant;
    private InventoryBasic merchantInventory;
    private final World theWorld;
    
    public ContainerMerchantAdd(final EntityPlayer player, final IMerchant par2IMerchant, final World par3World) {
        super(player);
        this.theMerchant = par2IMerchant;
        this.theWorld = par3World;
        this.merchantInventory = new InventoryBasic("", false, 3);
        this.addSlotToContainer(new Slot((IInventory)this.merchantInventory, 0, 36, 53));
        this.addSlotToContainer(new Slot((IInventory)this.merchantInventory, 1, 62, 53));
        this.addSlotToContainer(new Slot((IInventory)this.merchantInventory, 2, 120, 53));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)player.inventory, i, 8 + i * 18, 142));
        }
    }
    
    public void onCraftGuiOpened(final ICrafting par1ICrafting) {
        super.onCraftGuiOpened(par1ICrafting);
    }
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
    
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        super.onCraftMatrixChanged(par1IInventory);
    }
    
    public void setCurrentRecipeIndex(final int par1) {
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int par1, final int par2) {
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack itemstack = null;
        final Slot slot = this.inventorySlots.get(par2);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (par2 != 0 && par2 != 1 && par2 != 2) {
                if (par2 >= 3 && par2 < 30) {
                    if (!this.mergeItemStack(itemstack2, 30, 39, false)) {
                        return null;
                    }
                }
                else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack2, 3, 30, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack2, 3, 39, false)) {
                return null;
            }
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }
            if (itemstack2.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(par1EntityPlayer, itemstack2);
        }
        return itemstack;
    }
    
    public void onContainerClosed(final EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.theMerchant.setCustomer((EntityPlayer)null);
        super.onContainerClosed(par1EntityPlayer);
        if (!this.theWorld.isRemote) {
            ItemStack itemstack = this.merchantInventory.getStackInSlotOnClosing(0);
            if (itemstack != null) {
                par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
            }
            itemstack = this.merchantInventory.getStackInSlotOnClosing(1);
            if (itemstack != null) {
                par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
    }
}
