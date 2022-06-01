package noppes.npcs.items;

import noppes.npcs.constants.*;
import noppes.npcs.*;
import net.minecraft.creativetab.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class ItemShield extends ItemNpcInterface
{
    public EnumNpcToolMaterial material;
    
    public ItemShield(final int par1, final EnumNpcToolMaterial material) {
        super(par1);
        this.material = material;
        this.setMaxDurability(material.getMaxUses());
        this.setCreativeTab((CreativeTabs)CustomItems.tabWeapon);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(0.6f, 0.6f, 0.6f);
        GL11.glTranslatef(0.0f, 0.0f, -0.2f);
        GL11.glRotatef(-6.0f, 0.0f, 1.0f, 0.0f);
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.block;
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
}
