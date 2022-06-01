package noppes.npcs.items;

import net.minecraft.block.*;
import net.minecraft.item.*;

public class ItemNpcBlock extends ItemBlock
{
    public String[] names;
    
    public ItemNpcBlock(final Block block) {
        super(block);
    }
    
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        if (this.names != null && par1ItemStack.getMetadata() < this.names.length) {
            return this.names[par1ItemStack.getMetadata()];
        }
        return this.blockInstance.getUnlocalizedName();
    }
}
