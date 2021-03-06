package noppes.npcs.blocks;

import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.block.*;
import noppes.npcs.*;

public class BlockLamp extends BlockLightable
{
    public BlockLamp(final boolean lit) {
        super(Blocks.planks, lit);
        this.setBlockBounds(0.3f, 0.0f, 0.3f, 0.7f, 0.6f, 0.7f);
    }
    
    @Override
    public int maxRotation() {
        return 8;
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        final TileEntity tileentity = world.getTileEntity(x, y, z);
        if (!(tileentity instanceof TileColorable)) {
            super.setBlockBoundsBasedOnState(world, x, y, z);
            return;
        }
        final TileColorable tile = (TileColorable)tileentity;
        if (tile.color == 2) {
            float xOffset = 0.0f;
            float yOffset = 0.0f;
            if (tile.rotation == 0) {
                yOffset = 0.2f;
            }
            else if (tile.rotation == 4) {
                yOffset = -0.2f;
            }
            else if (tile.rotation == 6) {
                xOffset = 0.2f;
            }
            else if (tile.rotation == 2) {
                xOffset = -0.2f;
            }
            this.setBlockBounds(0.3f + xOffset, 0.2f, 0.3f + yOffset, 0.7f + xOffset, 0.7f, 0.7f + yOffset);
        }
        else {
            this.setBlockBounds(0.3f, 0.0f, 0.3f, 0.7f, 0.6f, 0.7f);
        }
    }
    
    public int onBlockPlaced(final World world, final int x, final int y, final int z, final int side, final float subX, final float subY, final float subZ, final int meta) {
        return side;
    }
    
    public void onPostBlockPlaced(final World world, final int x, final int y, final int z, final int meta) {
        final TileLamp tile = (TileLamp)world.getTileEntity(x, y, z);
        if (meta == 1) {
            tile.color = 0;
        }
        else if (meta == 0) {
            tile.color = 1;
        }
        else {
            tile.color = 2;
            if (meta == 2) {
                tile.rotation = 0;
            }
            else if (meta == 3) {
                tile.rotation = 4;
            }
            else if (meta == 4) {
                tile.rotation = 6;
            }
            else if (meta == 5) {
                tile.rotation = 2;
            }
        }
        world.setBlockMetadataWithNotify(x, y, z, 0, 4);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int side, final int meta) {
        return Blocks.soul_sand.getIcon(side, meta);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileLamp();
    }
    
    @Override
    public Block unlitBlock() {
        return CustomItems.lamp_unlit;
    }
    
    @Override
    public Block litBlock() {
        return CustomItems.lamp;
    }
}
