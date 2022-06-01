package noppes.npcs.client.renderer.blocks;

import noppes.npcs.blocks.*;
import noppes.npcs.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import noppes.npcs.blocks.tiles.*;
import org.lwjgl.opengl.*;

public class BlockBorderRenderer implements ISimpleBlockRenderingHandler
{
    public BlockBorderRenderer() {
        ((BlockBorder)CustomItems.border).renderId = RenderingRegistry.getNextAvailableRenderId();
    }
    
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        final TileBorder tile = (TileBorder)world.getTileEntity(x, y, z);
        GL11.glPushMatrix();
        if (tile.rotation == 1) {
            renderer.uvRotateTop = 1;
        }
        else if (tile.rotation == 3) {
            renderer.uvRotateTop = 2;
        }
        else if (tile.rotation == 2) {
            renderer.uvRotateTop = 3;
        }
        renderer.renderStandardBlock(CustomItems.border, x, y, z);
        renderer.uvRotateTop = 0;
        GL11.glPopMatrix();
        return true;
    }
    
    public boolean shouldRender3DInInventory(final int modelId) {
        return false;
    }
    
    public int getRenderId() {
        return CustomItems.border.getRenderType();
    }
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
    }
}
