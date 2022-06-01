package noppes.npcs.items;

import net.minecraft.entity.passive.*;
import java.awt.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class ItemOrb extends ItemNpcInterface
{
    public ItemOrb(final int par1) {
        super(par1);
        this.setHasSubtypes(true);
    }
    
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        final float[] color = EntitySheep.fleeceColorTable[par1ItemStack.getMetadata()];
        return new Color(color[0], color[1], color[2]).getRGB();
    }
    
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int var4 = 0; var4 < 16; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
