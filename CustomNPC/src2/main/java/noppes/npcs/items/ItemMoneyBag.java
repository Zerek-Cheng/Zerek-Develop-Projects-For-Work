package noppes.npcs.items;

import noppes.npcs.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.client.gui.*;
import noppes.npcs.client.*;

public class ItemMoneyBag extends Item
{
    public ItemMoneyBag(final int i) {
        this.maxStackSize = 1;
        this.setCreativeTab((CreativeTabs)CustomItems.tab);
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par2World.isRemote) {
            return par1ItemStack;
        }
        if (par1ItemStack.stackTagCompound == null) {
            par1ItemStack.stackTagCompound = new NBTTagCompound();
        }
        final MoneyBagContents contents = new MoneyBagContents(par3EntityPlayer);
        NoppesUtil.openGUI(par3EntityPlayer, new GuiScreen());
        return par1ItemStack;
    }
}
