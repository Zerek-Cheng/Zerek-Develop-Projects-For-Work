package noppes.npcs.blocks;

import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraft.block.*;
import noppes.npcs.*;

public class BlockCandle extends BlockLightable
{
    public BlockCandle(final boolean lit) {
        super(Blocks.planks, lit);
        this.setBlockBounds(0.3f, 0.0f, 0.3f, 0.7f, 0.5f, 0.7f);
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
            this.setBlockBounds(0.2f + xOffset, 0.4f, 0.2f + yOffset, 0.8f + xOffset, 0.9f, 0.8f + yOffset);
        }
        else if (tile.color == 1) {
            this.setBlockBounds(0.1f, 0.1f, 0.1f, 0.9f, 0.8f, 0.9f);
        }
        else {
            this.setBlockBounds(0.3f, 0.0f, 0.3f, 0.7f, 0.5f, 0.7f);
        }
    }
    
    public int onBlockPlaced(final World world, final int x, final int y, final int z, final int side, final float subX, final float subY, final float subZ, final int meta) {
        return side;
    }
    
    public void onPostBlockPlaced(final World world, final int x, final int y, final int z, final int meta) {
        final TileCandle tile = (TileCandle)world.getTileEntity(x, y, z);
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
        return new TileCandle();
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random random) {
        if (this == this.unlitBlock()) {
            return;
        }
        final TileCandle tile = (TileCandle)world.getTileEntity(x, y, z);
        if (tile.color == 1) {
            if (tile.rotation % 2 == 0) {
                world.spawnParticle("smoke", (double)(x + 0.5f), (double)(y + 0.66f), (double)(z + 0.13f), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(x + 0.5f), (double)(y + 0.65f), (double)(z + 0.13f), 0.0, 0.0, 0.0);
                world.spawnParticle("smoke", (double)(x + 0.5f), (double)(y + 0.66f), (double)(z + 0.87f), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(x + 0.5f), (double)(y + 0.65f), (double)(z + 0.87f), 0.0, 0.0, 0.0);
                world.spawnParticle("smoke", (double)(x + 0.13f), (double)(y + 0.66f), (double)(z + 0.5f), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(x + 0.13f), (double)(y + 0.65f), (double)(z + 0.5f), 0.0, 0.0, 0.0);
                world.spawnParticle("smoke", (double)(x + 0.87f), (double)(y + 0.66f), (double)(z + 0.5f), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(x + 0.87f), (double)(y + 0.65f), (double)(z + 0.5f), 0.0, 0.0, 0.0);
            }
            else {
                world.spawnParticle("smoke", (double)(x + 0.24f), (double)(y + 0.66f), (double)(z + 0.24f), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(x + 0.24f), (double)(y + 0.65f), (double)(z + 0.24f), 0.0, 0.0, 0.0);
                world.spawnParticle("smoke", (double)(x + 0.76f), (double)(y + 0.66f), (double)(z + 0.76f), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(x + 0.76f), (double)(y + 0.65f), (double)(z + 0.76f), 0.0, 0.0, 0.0);
                world.spawnParticle("smoke", (double)(x + 0.24f), (double)(y + 0.66f), (double)(z + 0.76f), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(x + 0.24f), (double)(y + 0.65f), (double)(z + 0.76f), 0.0, 0.0, 0.0);
                world.spawnParticle("smoke", (double)(x + 0.76f), (double)(y + 0.66f), (double)(z + 0.24f), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(x + 0.76f), (double)(y + 0.65f), (double)(z + 0.24f), 0.0, 0.0, 0.0);
            }
        }
        else {
            float xOffset = 0.5f;
            float yOffset = 0.45f;
            float zOffset = 0.5f;
            if (tile.color == 2) {
                yOffset = 1.05f;
                if (tile.rotation == 0) {
                    zOffset += 0.12f;
                }
                if (tile.rotation == 4) {
                    zOffset -= 0.12f;
                }
                if (tile.rotation == 6) {
                    xOffset += 0.12f;
                }
                if (tile.rotation == 2) {
                    xOffset -= 0.12f;
                }
            }
            final double d0 = x + xOffset;
            final double d2 = y + yOffset;
            final double d3 = z + zOffset;
            world.spawnParticle("smoke", d0, d2, d3, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d0, d2, d3, 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public Block unlitBlock() {
        return CustomItems.candle_unlit;
    }
    
    @Override
    public Block litBlock() {
        return CustomItems.candle;
    }
}
