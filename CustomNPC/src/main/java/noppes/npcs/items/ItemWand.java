package noppes.npcs.items;

import noppes.npcs.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;

public class ItemWand extends ItemNpcInterface
{
    public ItemWand(final int par1) {
        super(par1);
        this.setCreativeTab((CreativeTabs)CustomItems.tabMisc);
    }
    
    public boolean hasEffect(final ItemStack par1ItemStack, final int pass) {
        return true;
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(0.54f, 0.54f, 0.54f);
        GL11.glTranslatef(0.1f, 0.5f, 0.1f);
    }
}
