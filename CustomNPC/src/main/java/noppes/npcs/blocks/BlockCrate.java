package noppes.npcs.blocks;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import noppes.npcs.*;
import noppes.npcs.constants.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import noppes.npcs.blocks.tiles.*;

public class BlockCrate extends BlockRotated
{
    public BlockCrate() {
        super(Blocks.planks);
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        par1World.playSoundEffect((double)i, j + 0.5, (double)k, "random.chestopen", 0.5f, par1World.rand.nextFloat() * 0.1f + 0.9f);
        player.openGui((Object)CustomNpcs.instance, EnumGuiType.Crate.ordinal(), par1World, i, j, k);
        return true;
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
    }
    
    public int damageDropped(final int par1) {
        return par1;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileCrate();
    }
    
    public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int meta) {
        final TileNpcContainer tile = (TileNpcContainer)world.getTileEntity(x, y, z);
        if (tile == null) {
            return;
        }
        tile.dropItems(world, x, y, z);
        world.updateNeighborsAboutBlockChange(x, y, z, block);
        super.breakBlock(world, x, y, z, block, meta);
    }
}
