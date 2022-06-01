package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;

public abstract class BlockRotated extends BlockContainer
{
    private Block block;
    public int renderId;
    
    protected BlockRotated(final Block block) {
        super(block.getMaterial());
        this.renderId = -1;
        this.block = block;
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * this.maxRotation() / 360.0f + 0.5) & this.maxRotation() - 1;
        l %= this.maxRotation();
        final TileColorable tile = (TileColorable)par1World.getTileEntity(par2, par3, par4);
        tile.rotation = l;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    public int maxRotation() {
        return 4;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return this.renderId;
    }
    
    public boolean isSideSolid(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection side) {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        return this.block.getIcon(side, meta);
    }
}
