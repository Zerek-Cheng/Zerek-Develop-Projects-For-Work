package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import java.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.item.*;

public abstract class BlockLightable extends BlockRotated
{
    protected BlockLightable(final Block block, final boolean lit) {
        super(block);
        if (lit) {
            this.setLightLevel(1.0f);
        }
    }
    
    public abstract Block unlitBlock();
    
    public abstract Block litBlock();
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (this.litBlock() == this) {
            world.setBlock(x, y, z, this.unlitBlock(), world.getBlockMetadata(x, y, z), 2);
        }
        else {
            world.setBlock(x, y, z, this.litBlock(), world.getBlockMetadata(x, y, z), 2);
        }
        tile.validate();
        world.setTileEntity(x, y, z, tile);
        return true;
    }
    
    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        return Item.getItemFromBlock(this.litBlock());
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World worldIn, final int x, final int y, final int z) {
        return Item.getItemFromBlock(this.litBlock());
    }
    
    protected ItemStack createStackedBlock(final int meta) {
        return new ItemStack(this.litBlock());
    }
}
