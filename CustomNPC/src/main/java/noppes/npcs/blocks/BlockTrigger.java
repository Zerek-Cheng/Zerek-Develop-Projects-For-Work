package noppes.npcs.blocks;

import net.minecraft.block.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.world.*;

public abstract class BlockTrigger extends BlockRotated
{
    protected BlockTrigger(final Block block) {
        super(block);
    }
    
    public boolean canProvidePower() {
        return true;
    }
    
    public int isProvidingStrongPower(final IBlockAccess world, final int x, final int y, final int z, final int side) {
        return this.isProvidingWeakPower(world, x, y, z, side);
    }
    
    public int isProvidingWeakPower(final IBlockAccess world, final int x, final int y, final int z, final int side) {
        final TileColorable tile = (TileColorable)world.getTileEntity(x, y, z);
        if (tile != null) {
            return tile.powerProvided();
        }
        return 0;
    }
    
    public void updateSurrounding(final World par1World, final int par2, final int par3, final int par4) {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, (Block)this);
    }
}
